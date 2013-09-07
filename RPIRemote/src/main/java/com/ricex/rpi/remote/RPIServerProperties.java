package com.ricex.rpi.remote;

import com.ricex.rpi.common.RPIProperties;


public class RPIServerProperties extends RPIProperties {
	
	/*** The singleton instance for this class */
	private static RPIServerProperties _instance;
	
	/** Returns the singleton instance for this class */
	public static RPIServerProperties getInstance() {
		if (_instance == null) {
			_instance = new RPIServerProperties();
		}
		return _instance;
	}
	
	/** key for the RPI Port */
	private static final String RPI_PORT = "rpi_port";
	
	/** Key for the remote port */
	private static final String REMOTE_PORT = "remote_port";
	
	/** Key for maximum number of server connections */
	private static final String MAX_CONNECTIONS = "max_connections";	
	
	/** The key for the base directory */
	private static final String BASE_DIRECTORY = "base_dir";
	
	/** Private constructor to maintain singleton.
	 *  Calls the super constructor to load the properties in "./rpi_server.conf"
	 */
	private RPIServerProperties() {
		super("./rpi_remote.conf");
	}
	
	/** Returns the port for the RaspberryPi clients to connect to */	
	public int getRPIPort() {
		return Integer.parseInt(getProperty(RPI_PORT));
	}
	
	/** Returns the ports that remote clients will conenct to */	
	public int getRemotePort() {
		return Integer.parseInt(getProperty(REMOTE_PORT));
	}
	
	/** Returns the maximum number of server connections */	
	public int getMaxConnectins() {
		return Integer.parseInt(getProperty(MAX_CONNECTIONS));
	}
	
	/** Returns the base directory where the movies are located
	 * 
	 * @return
	 */
	
	public String getBaseDirectory() {
		return getProperty(BASE_DIRECTORY);
	}
}
