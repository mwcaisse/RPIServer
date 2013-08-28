package com.ricex.rpi.remote;

import java.net.Socket;

import com.ricex.rpi.remote.client.RPIClient;


/** The server that listens and deals with connections from RPI clients, aka 
 * raspberry pis
 * @author Mitchell Caisse
 * 
 */

public class RPIServer extends Server<RPIClient> {

	/** The Singleton instance */
	private static RPIServer _instance;
	
	/** Returns the singleton instance */
	public static RPIServer getInstance() {
		if (_instance == null) {
			_instance = new RPIServer();
		}
		return _instance;
	}
	                                       

	/** Creates a new RPIServer with the data from the RPIServerProperties */	
	private RPIServer() {
		super( RPIServerProperties.getInstance().getRPIPort(), RPIServerProperties.getInstance().getMaxConnectins(), "RPIServer");
	}

	/** 
	 * {@inheritDoc} 
	 */
	
	@Override
	protected RPIClient createClient(Socket socket) {
		return new RPIClient(this, getNextId(), socket);
	}

}
