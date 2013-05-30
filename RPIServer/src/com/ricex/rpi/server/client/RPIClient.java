package com.ricex.rpi.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.server.Server;
import com.ricex.rpi.server.client.handler.RPIClientHandler;

/** A client that is connected to the server
 * 
 * @author Mitchell
 *
 */

public class RPIClient extends Client {
	
	/** The name of this client */
	private String name;
	
	/** The status of this client */
	private RPIStatus status;
	
	/** The list of change listeners registered for this client */
	private List<ClientChangeListener<RPIClient>> changeListeners;
	
	public RPIClient (Server<RPIClient> server, long id, Socket socket) {
		super(server, id, socket);	
		status = new RPIStatus(RPIStatus.IDLE);		
		changeListeners = new ArrayList<ClientChangeListener<RPIClient>>();		
	}
	
	/** Returns the status of this client */
	
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Sets the status of this client to the given status */
	
	public void setStatus(RPIStatus status) {
		this.status = status;
		notifyChangeListeners(); //notify listeners that the status has been changed
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
	public void addChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.add(listener);
	}
	
	/** Removes the given change listener */	
	public void removeChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.remove(listener);
	}	

	protected void notifyChangeListeners() {
		for (ClientChangeListener<RPIClient> listener : changeListeners) {
			listener.clientChanged(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected RPIClientHandler createClientHandler() {
		return new RPIClientHandler(this);
	}
}
