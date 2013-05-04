package com.ricex.rpi.common;

import java.io.IOException;
import java.util.Properties;


public class RPIProperties {

	/** key for the RPI Port */
	private static final String RPI_PORT = "rpi_port";
	
	/** Key for the remote port */
	private static final String REMOTE_PORT = "remote_port";
	
	/** Key for the server IP */
	private static final String SERVER_IP = "server_ip";
	
	/** Keys for the client and sever base dirs */
	private static final String CLIENT_BASE_DIR = "client_base_dir";
	private static final String SERVER_BASE_DIR = "server_base_dir";
	
	private static RPIProperties _instance;
	
	public static RPIProperties getInstance() {
		if (_instance == null) {
			_instance = new RPIProperties();
		}
		return _instance;
	}
	
	private Properties properties;
	
	/** Private constructor for the singleton instance
	 * 
	 */
	private RPIProperties() {
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("rpi.conf"));
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
	
	/** Returns the port for the RaspberryPi clients to connect to */
	
	public String getRPIPort() {
		return properties.getProperty(RPI_PORT);
	}
	
	/** Returns the ports that remote clients will conenct to */
	
	public String getRemotePort() {
		return properties.getProperty(REMOTE_PORT);
	}
	
	/** Returns the IP address of the server for clients to conenct to */
	
	public String getServerIp() {
		return properties.getProperty(SERVER_IP);
	}
	
	/** Return the base directory used on the client */
	
	public String getClientBaseDir() {
		return properties.getProperty(CLIENT_BASE_DIR);
	}
	
	/** Return the base directory used on the server */
	
	public String getServerBaseDir() {
		return properties.getProperty(SERVER_BASE_DIR);
	}
}
