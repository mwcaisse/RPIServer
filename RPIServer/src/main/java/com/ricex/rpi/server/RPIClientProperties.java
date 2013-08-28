package com.ricex.rpi.server;

import com.ricex.rpi.common.RPIProperties;

public class RPIClientProperties extends RPIProperties {

	/** key for the RPI Port */
	private static final String RPI_PORT = "rpi_port";
	
	/** Key for the server IP */
	private static final String SERVER_IP = "server_ip";
	
	/** Key for the name of this client */
	private static final String NAME = "name";
	
	/** Key for the base dir */
	private static final String BASE_DIR = "base_dir";
	
	/** Key for the base command */
	private static final String BASE_COMMAND = "base_command";
	
	/** The singleton instance of this class */
	private static RPIClientProperties _instance;
	
	/** Returns the singleton instance of this class */	
	public static RPIClientProperties getInstance() {
		if (_instance == null) {
			_instance = new RPIClientProperties();
		}
		return _instance;
	}
	
	/** Keep the constructor private to keep singleton */
	private RPIClientProperties() {
		super("./rpi_client.conf");
	}
	
	/** Returns the port for the RaspberryPi clients to connect to */	
	public int getRPIPort() {
		return Integer.parseInt(getProperty(RPI_PORT));
	}
	
	/** Returns the IP address of the server for clients to conenct to */	
	public String getServerIp() {
		return getProperty(SERVER_IP);
	}
	
	/** Return the base directory used on the client */	
	public String getBaseDir() {
		return getProperty(BASE_DIR);
	}
	
	/** Returns the base command used on the client */	
	public String getBaseCommand() {
		return getProperty(BASE_COMMAND);
	}
	
	/** Returns the name of this client, or null if none */
	public String getName() {
		return getProperty(NAME, null);
	}

	
}
