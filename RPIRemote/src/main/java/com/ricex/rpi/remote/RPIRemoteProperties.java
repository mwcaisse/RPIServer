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
	private static final String SERVER_PORT = "server_port";
	
	/** The address for the server */
	private static final String SERVER_ADDRESS = "server_address";

	
	/** Private constructor to maintain singleton.
	 *  Calls the super constructor to load the properties in "./rpi_server.conf"
	 */
	private RPIRemoteProperties() {
		super("./rpi_remote.conf");
	}
	
	/** Returns the ports that remote clients will connect to */	
	public int getServerPort() {
		return Integer.parseInt(getProperty(SERVER_PORT));
	}
	
	/** Returns the address of the server to connect to */
	
	public String getServerAddress() {
		return getProperty(SERVER_ADDRESS);
	}
	
	/** Sets the value of the server port
	 * 
	 * @param serverPort The new server port
	 */
	
	public void setServerPort(int serverPort) {
		setProperty(SERVER_PORT, Integer.toString(serverPort));
	}
	
	/** Sets the value of the server address
	 * 
	 * @param serverAddress The new server address
	 */
	
	public void setServerAddress(String serverAddress) {
		setProperty(SERVER_PORT, serverAddress);
	}
	

}
