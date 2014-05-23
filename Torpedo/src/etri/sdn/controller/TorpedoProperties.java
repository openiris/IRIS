package etri.sdn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import etri.sdn.controller.util.Logger;

/**
 * This class implements a functionality that reads Torpedo configuration file
 * (torpedo.properties in the project root directory).
 * 
 * @author bjlee
 *
 */
@SuppressWarnings("serial")
public final class TorpedoProperties extends Properties {
	
	/**
	 * static member to implement singleton pattern.
	 */
	private static TorpedoProperties sysconf_ = null;
	
	/**
	 * lock object to guarantee the synchronized access 
	 * to the {@link #sysconf_} member. 
	 */
	private static Object lock_ = new Object();

	/**
	 * Check if there is a property value associated with the given name
	 * @param name	name of the property value
	 * @return		true if there is the requested property value, false otherwise. 
	 */
	public boolean propertyExists(String name) {
		return super.getProperty(name) != null;
	}
	
	/**
	 * Get integer representation of the property value associated with the given name
	 * 
	 * @param name	name of the property value
	 * @return		integer value for the property. If there's no such property,
	 * 				0 is returned (FIXME:)
	 */
	public int getInt(String name) {
		String prop = null;
		try { 
			prop = super.getProperty(name);
			return Integer.parseInt( prop );
		} catch ( NumberFormatException e ) {
			Logger.stderr( "wrong number format for : " + name + "," + prop );
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Get String representation of the property value assiciated with the given name
	 * 
	 * @param name	name of the property value
	 * @return		String value for the property.
	 */
	public String getString(String name) {
		return super.getProperty(name);
	}

	/**
	 * Load configuration in torpedo.properties file and convert it into 
	 * TorpedoProperties object and return it. 
	 * As this method follows singleton pattern, 
	 * it returns the same TorpedoProperties object always. 
	 * 
	 * @return		TorpedoProperties object
	 */
	public static TorpedoProperties loadConfiguration() {
		
		synchronized ( lock_ ) {
			
			if ( sysconf_ != null ) {
				return sysconf_;
			}
			
			sysconf_ = new TorpedoProperties();
	
			// load some default values
			sysconf_.setProperty("port-number", "6633");
			sysconf_.setProperty("watcher-num", "2");
			sysconf_.setProperty("web-server-port", "8080");
	
			File sysconf_file = new File("./torpedo.properties");
			if ( sysconf_file.exists() ) {
				FileInputStream sysconf_stream;
				try {
					sysconf_.load( (sysconf_stream = new FileInputStream(sysconf_file)) );
					sysconf_stream.close();
				} catch (FileNotFoundException e) {
					// file is removed. (does nothing)
				} catch (IOException e) {
					// file cannot be read. (does nothing)
				}
			}
			
			return sysconf_;
		}
	}
}