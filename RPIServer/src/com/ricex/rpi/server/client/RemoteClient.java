package com.ricex.rpi.server.client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.server.Server;
import com.ricex.rpi.server.client.handler.RemoteClientHandler;


public class RemoteClient extends Client {
	
	/** The client handler for this client */
	private RemoteClientHandler handler;
	
	/** The list of change listeners registered for this client */
	private List<ClientChangeListener<RemoteClient>> changeListeners;
	
	public RemoteClient(Server<RemoteClient> server, long id, Socket socket) {
		super(server, id, socket);
		
		changeListeners = new ArrayList<ClientChangeListener<RemoteClient>>();
	}
	
	/**
	 * {@inheritDoc}
	 */	
	
	@Override
	public void setName(String name) {
		super.setName(name);
		notifyChangeListeners();
	}
	
	/** Adds the given change listener */
	public void addChangeListener(ClientChangeListener<RemoteClient> listener) {
		changeListeners.add(listener);
	}
	
	/** Removes the given change listener */	
	public void removeChangeListener(ClientChangeListener<RemoteClient> listener) {
		changeListeners.remove(listener);
	}	

	protected void notifyChangeListeners() {
		for (ClientChangeListener<RemoteClient> listener : changeListeners) {
			listener.clientChanged(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected RemoteClientHandler createClientHandler() {
		return new RemoteClientHandler(this);
	}	

}
