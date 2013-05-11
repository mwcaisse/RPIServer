package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.server.client.RemoteClient;

/** The server that listens for connections from RPIRemotes, and creates threads to respond to them
 * 
 * @author Mitchell
 *
 */

public class RemoteServer extends Server<RemoteClient> {

	public RemoteServer() {
		super(RPIServerProperties.getInstance().getRemotePort(), RPIServerProperties.getInstance().getMaxConnectins(), "RemoteServer");
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected RemoteClient createClient(Socket socket) {
		return new RemoteClient(this, getNextId(), socket);
	}
}
