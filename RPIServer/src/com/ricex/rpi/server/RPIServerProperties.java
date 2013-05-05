package com.ricex.rpi.server;

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
	
	/** Key for the base dis */
	private static final String BASE_DIR = "base_dir";		
	
	/** Private constructor to maintain singleton.
	 *  Calls the super constructor to load the properties in "./rpi_server.conf"
	 */
	private RPIServerProperties() {
		super("./rpi_server.conf");
	}
	
	/** Returns the port for the RaspberryPi clients to connect to */	
	public int getRPIPort() {
		return Integer.parseInt(getProperty(RPI_PORT));
	}
	
	/** Returns the ports that remote clients will conenct to */	
	public int getRemotePort() {
		return Integer.parseInt(getProperty(REMOTE_PORT));
	}

	/** Return the base directory used on the server */	
	public String getBaseDir() {
		return getProperty(BASE_DIR);
	}
	
	/** Returns the maximum number of server connections */	
	public int getMaxConnectins() {
		return Integer.parseInt(getProperty(MAX_CONNECTIONS));
	}
}
