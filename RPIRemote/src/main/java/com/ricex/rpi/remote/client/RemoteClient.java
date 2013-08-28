package com.ricex.rpi.remote.client;

import java.net.Socket;

import com.ricex.rpi.remote.Server;
import com.ricex.rpi.remote.client.handler.RemoteClientHandler;


public class RemoteClient extends Client {
	
	/** The client handler for this client */
	private RemoteClientHandler handler;
	
	public RemoteClient(Server<RemoteClient> server, long id, Socket socket) {
		super(server, id, socket);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected RemoteClientHandler createClientHandler() {
		return new RemoteClientHandler(this);
	}	

}
