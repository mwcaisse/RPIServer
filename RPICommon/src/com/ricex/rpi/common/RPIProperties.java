package com.ricex.rpi.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public abstract class RPIProperties {

	/** key for the RPI Port */
	private static final String RPI_PORT = "rpi_port";
	
	/** Key for the remote port */
	private static final String REMOTE_PORT = "remote_port";
	
	/** Key for the server IP */
	private static final String SERVER_IP = "server_ip";
	
	/** Keys for the client and sever base dirs */
	private static final String CLIENT_BASE_DIR = "client_base_dir";
	private static final String SERVER_BASE_DIR = "server_base_dir";
	
	/** Key for the base command */
	private static final String BASE_COMMAND = "base_command";
	
	/** Key for maximum number of server connections */
	private static final String SERVER_MAX_CONNECTIONS = "server_max_connections";	
	
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
			System.out.println("Unable to create properties. IOException");
			e.printStackTrace();
		}
	}
	
	/** Returns the property with the given key
	 * 
	 * @param key The key of the property to retreive
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}	
	
	/** Returns the property with the given key, or the default value if the key is not found
	 * 
	 * @param key The key
	 * @param defaultValue The default Value
	 * @return
	 */
	
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
