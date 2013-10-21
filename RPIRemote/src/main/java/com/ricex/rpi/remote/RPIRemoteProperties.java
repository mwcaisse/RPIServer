package com.ricex.rpi.remote;

import com.ricex.rpi.common.RPIProperties;


public class RPIRemoteProperties extends RPIProperties {
	
	/*** The singleton instance for this class */
	private static RPIRemoteProperties _instance;
	
	/** Returns the singleton instance for this class */
	public static RPIRemoteProperties getInstance() {
		if (_instance == null) {
			_instance = new RPIRemoteProperties();
		}
		return _instance;
	}
	
	/** Key for the remote port */
	private static final String REMOTE_PORT = "remote_port";
	
	/** The address for the server */
	private static final String SERVER_ADDRESS = "server_address";

	
	/** Private constructor to maintain singleton.
	 *  Calls the super constructor to load the properties in "./rpi_server.conf"
	 */
	private RPIRemoteProperties() {
		super("./rpi_remote.conf");
	}
	
	/** Returns the ports that remote clients will conenct to */	
	public int getRemotePort() {
		return Integer.parseInt(getProperty(REMOTE_PORT));
	}
	
	/** Returns the address of the server to connect to */
	
	public String getServerAddress() {
		return getProperty(SERVER_ADDRESS);
	}
	

}
