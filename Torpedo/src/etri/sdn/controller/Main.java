package etri.sdn.controller;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.protocol.io.TcpServer;
import etri.sdn.controller.util.Basename;
import etri.sdn.controller.util.JarLoader;

/**
 * This is a entry-point class of the whole Torpedo system.
 * 
 * @author bjlee
 *
 */
public class Main {
	
	/**
	 * if this value is set to true, debug mode is enabled. 
	 */
	public static boolean debug = true;
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void parseCommandLine(String[] args) {
		// currently, does nothing.
	}

	/**
	 * This is an entry-point function for Torpedo system. 
	 * 
	 * @param args command-line parameters.
	 */
	public static void main(String[] args) {
		 
		// suppress all the other debug messages from other components such as restlet.
		// but if you modify the jdklog.properties, you can make the log messages 
		// re-appear on the screen again.
		PropertyConfigurator.configure("./conf/log4j.properties");
		
		System.setProperty("java.util.logging.config.file", "./conf/jdklog.properties");
		
		Main.parseCommandLine(args);
		
		//
		// load system configuration.
		//
		TorpedoProperties sysconf = TorpedoProperties.loadConfiguration();

		//
		// load & start TCP server.
		//
		TcpServer tcp_server;
		try {
			tcp_server = new TcpServer(
					sysconf.getInt("port-number"),
					sysconf.getInt("watcher-num")
					);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		tcp_server.start();

		logger.info("starting torpedo...");

		tcp_server.wakeup();
		
		RESTApiServer rest_api_server = new RESTApiServer();
		rest_api_server.start(sysconf.getInt("web-server-port"));

		// 
		// do controller registration loop, which search for a new 
		// controller instance in the 'controllers' directory
		//
		controller_registration_loop(sysconf, tcp_server, rest_api_server);
	}

	/**
	 * This loop is for detecting new controller implementations uploaded to 'controllers' directory.
	 * Detected implementations are automatically loaded & registered to tcp_server.
	 * 
	 * @param sysconf
	 * @param tcp_server
	 * @param rest_api_server 
	 */
	private static void controller_registration_loop(
			TorpedoProperties sysconf, 
			TcpServer tcp_server, 
			RESTApiServer rest_api_server) 
	{
		Path ctrl_dir_path = Paths.get("controllers");
		if ( !Files.exists(ctrl_dir_path) ) {
			try {
				try { 
					Files.createDirectory( 
							ctrl_dir_path, 
							PosixFilePermissions.asFileAttribute( 
									PosixFilePermissions.fromString("rwxr-xr-x") 
							)
					);
				} catch ( UnsupportedOperationException e ) {
					// in Windows system, Files.createDirectory with PosixFilePermission
					// raises UnsupportedOperationException. In that case, we resort to this
					// option.
					Files.createDirectory( ctrl_dir_path );
				}
			} catch (IOException e) {
				logger.error("cannot create directory '{}'", ctrl_dir_path.toString());
				e.printStackTrace();
			}
		}
		else if ( ! Files.isDirectory( ctrl_dir_path ) ) {
			logger.error("Given path '{}' is not a valid directory", ctrl_dir_path.toString());
			System.exit(-1);
		}

		// now the controllers directory is ready.
		Set<String> ctrl_names = new HashSet<String>();
		do {
			final Set<String> tmp_names = new HashSet<String>();

			// read all directory listing. 
			try {
				Files.walkFileTree( ctrl_dir_path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
						String filename = file.toString().replace('\\', '/');
						if ( !filename.endsWith(".jar") ) {
							return FileVisitResult.CONTINUE;
						}
						tmp_names.add( filename );
						return FileVisitResult.CONTINUE;
					}
				}
				);
			} catch (IOException e) {
				logger.error("cannot walk the controller directory.");
				e.printStackTrace();
				System.exit(-1);
			}

			Set<String> controller_listing = new HashSet<String>( tmp_names );
			
			// find newly uploaded implementations only.
			tmp_names.removeAll(ctrl_names);
			if ( !tmp_names.isEmpty() ) {
				for ( String ctrl_name : tmp_names ) {
					
					String basename = Basename.get(ctrl_name, "jar");
					
					if ( !sysconf.propertyExists(basename + ".instance-num") || 
						 !sysconf.propertyExists(basename + ".role") ||
						 !sysconf.propertyExists(basename + ".run") ) {
						continue;
					}
					
					if ( !sysconf.getString(basename + ".run").equals("true") ) {
						continue;
					}
					
					logger.info("loading controller instance '{}'", ctrl_name);
					
					OFController to_load = JarLoader.getController(
							ctrl_name,
							sysconf.getInt(basename + ".instance-num"),
							sysconf.getString(basename + ".role")
					);

					if ( to_load != null ) {
						to_load.init();
						to_load.startModules();
						to_load.start();
						
						tcp_server.registerController(to_load);
						
						// retrieve models and register them to rest_api_server.
						// we need to restart the rest_api_server because 
						// the Routing mapping might be changed. 
						OFModel[] models = to_load.getModels();
						if ( models != null ) {
							rest_api_server.registerOFModels(models);
						}
					}
				}
			}

			ctrl_names.clear();
			ctrl_names.addAll( controller_listing );

			synchronized( ctrl_names ) {
				try {
					ctrl_names.wait(3000);		// wait for 3 second
				} catch (InterruptedException e) {
					// does nothing.
				} 		
			}

		} while ( true );
	}
}
