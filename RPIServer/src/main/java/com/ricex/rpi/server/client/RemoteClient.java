package com.ricex.rpi.server.client;

import java.net.Socket;

import com.ricex.rpi.server.RPIPlayer;
import com.ricex.rpi.server.Server;
import com.ricex.rpi.server.client.handler.RemoteClientHandler;


public class RemoteClient extends Client {
	
	/** The player that the server is using */
	private RPIPlayer player;
	
	public RemoteClient(Server<RemoteClient> server, long id, Socket socket, RPIPlayer player) {
		super(server, id, socket);
		this.player = player;
	}

	/**
	 * {@inheritDoc}
	 */
	
	protected RemoteClientHandler createClientHandler() {
		return new RemoteClientHandler(this);
	}	

	
	/** Returns the RPI player
	 * 
	 * @return the rpi player
	 */
	
	public RPIPlayer getPlayer() {
		return player;
	}
}
