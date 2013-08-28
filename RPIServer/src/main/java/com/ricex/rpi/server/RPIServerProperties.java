package com.ricex.rpi.server;

import com.ricex.rpi.common.RPIProperties;

public class RPIServerProperties extends RPIProperties {

	/** key for the RPI Port */
	private static final String SERVER_PORT = "server_port";
	
	/** Key for the max number of connections */
	private static final String MAX_CONNECTIONS = "max_connections";
	
	/** Key for the base dir */
	private static final String BASE_DIR = "base_dir";
	
	/** Key for the base command */
	private static final String BASE_COMMAND = "base_command";
	
	/** The singleton instance of this class */
	private static RPIServerProperties _instance;
	
	/** Returns the singleton instance of this class */	
	public static RPIServerProperties getInstance() {
		if (_instance == null) {
			_instance = new RPIServerProperties();
		}
		return _instance;
	}
	
	/** Keep the constructor private to keep singleton */
	private RPIServerProperties() {
		super("./rpi_server.conf");
	}
	
	/** Returns the port that the server will be created on */
	public int getServerPort() {
		return Integer.parseInt(getProperty(SERVER_PORT, "6510"));
	}
	
	public int getMaxConnections() {
		return Integer.parseInt(getProperty(MAX_CONNECTIONS, "5"));
	}
	
	/** Return the base directory used on the client */	
	public String getBaseDir() {
		return getProperty(BASE_DIR);
	}
	
	/** Returns the base command used on the client */	
	public String getBaseCommand() {
		return getProperty(BASE_COMMAND);
	}	
}
