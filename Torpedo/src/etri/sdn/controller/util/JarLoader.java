package etri.sdn.controller.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etri.sdn.controller.OFController;

public class JarLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(JarLoader.class);

	@SuppressWarnings("unchecked")
	private static Class<OFController> loadJar(String path) {
		if ( !path.endsWith(".jar") ) {
			// this is not a jar file.
			return null;
		}
		
		String basename = Basename.get(path, "jar");
		
		File file = new File(path);
		if ( file.exists() ) {
			ClassLoader loader;
			try {
				loader = URLClassLoader.newInstance(
						new URL[] { file.toURI().toURL() },
						JarLoader.class.getClassLoader()
				); 
				return (Class<OFController>) Class.forName( basename, true, loader );
			} catch (MalformedURLException e1) {
				return null;
			} catch (ClassNotFoundException e) {
				logger.error("basename '{}' is not found", basename);
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static OFController getController(String path, int num_of_instances, String role) {
		Class<OFController> ctrl = loadJar(path);
		if ( ctrl == null ) {
			return null;
		}
		
		try { 
			Constructor<OFController> constructor = 
				ctrl.getConstructor(new Class[]{ int.class, String.class });
			return constructor.newInstance( num_of_instances, role );
		} catch (NoSuchMethodException e) {
			logger.error("Cannot find constructor for " + path);
			return null;
		} catch (SecurityException e) {
			logger.error("You are not authorized to open " + path);
			return null;
		} catch (InstantiationException e) {
			logger.error("Cannot instantiate controller from the given jar " + path);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("You are not authorized to access constructor for " + path);
			return null;
		} catch (IllegalArgumentException e) {
			logger.error("You have passed wrong argument to " + path);
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.error("Wrong invocation target for " + path);
			return null;
		}
	}
}
