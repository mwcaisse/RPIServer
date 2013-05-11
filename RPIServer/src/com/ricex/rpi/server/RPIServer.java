package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.server.client.RPIClient;


/** The server that listens and deals with connections from RPI clients, aka 
 * raspberry pis
 * @author Mitchell
 * 
 */

public class RPIServer extends Server<RPIClient> {


	/** Creates a new RPIServer with the data from the RPIServerProperties */
	
	public RPIServer() {
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
