package com.ricex.rpi.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public abstract class RPIProperties {

	/** The properties store */
	private Properties properties;
	
	/** Protected constructor for the singleton instance
	 * 
	 */
	protected RPIProperties(String filePath) {
		properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			properties.load(fis);
			fis.close();
		}
		catch (IOException e) {
			//System.out.println("Unable to create properties. IOException");
			e.printStackTrace();
		}
	}
	
	/** Returns the property with the given key
	 * 
	 * @param key The key of the property to retreive
	 * @return
	 */
	protected String getProperty(String key) {
		return properties.getProperty(key);
	}	
	
	/** Returns the property with the given key, or the default value if the key is not found
	 * 
	 * @param key The key
	 * @param defaultValue The default Value
	 * @return
	 */
	
	protected String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	/** Sets the property with the specified key to the specified value
	 * 
	 * @param key The key of the property
	 * @param value The new value of the property
	 */
	
	protected void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	/** Save the given properties to file
	 * TODO: implemented
	 */
	
	public void saveProperties() {
		
	}
}
