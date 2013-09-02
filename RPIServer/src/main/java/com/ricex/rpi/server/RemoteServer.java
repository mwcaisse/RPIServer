package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.server.client.RemoteClient;

/** The server that listens for connections from RPIRemotes, and creates threads to respond to them
 * 
 * @author Mitchell Caisse
 *
 */

public class RemoteServer extends Server<RemoteClient> {

	/** The singleton instance of this server */
	private static RemoteServer _instance;	

	/** Returns the singleton instance */

	public static RemoteServer getInstance() {
		if (_instance == null) {
			_instance = new RemoteServer();
		}
		return _instance;
	}

	/** The RPI Player for this server */
	protected RPIPlayer player;
	
	/** Creates a new Remote Server */

	private RemoteServer() {
		super(RPIServerProperties.getInstance().getServerPort(), RPIServerProperties.getInstance().getMaxConnections(), "RemoteServer");
		player = new RPIPlayer(this);
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected RemoteClient createClient(Socket socket) {
		RemoteClient client = new RemoteClient(this, getNextId(), socket, player);
		return client;
	}
}