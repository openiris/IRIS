package etri.sdn.controller.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import etri.sdn.controller.OFController;

public class JarLoader {

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
				Logger.stderr("basename " + basename + " is not found");
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
			Logger.stderr("Cannot find constructor for " + path);
			return null;
		} catch (SecurityException e) {
			Logger.stderr("You are not authorized to open " + path);
			return null;
		} catch (InstantiationException e) {
			Logger.stderr("Cannot instantiate controller from the given jar " + path);
			return null;
		} catch (IllegalAccessException e) {
			Logger.stderr("You are not authorized to access constructor for " + path);
			return null;
		} catch (IllegalArgumentException e) {
			Logger.stderr("You have passed wrong argument to " + path);
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Logger.stderr("Wrong invocation target for " + path);
			return null;
		}
	}
}
