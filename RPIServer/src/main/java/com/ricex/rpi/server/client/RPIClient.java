package com.ricex.rpi.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.ClientPlayerModule;
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

	/** The player module for this client */
	private ClientPlayerModule playerModule;

	public RPIClient (Server<RPIClient> server, long id, Socket socket) {
		super(server, id, socket);
		name = "Unnamed Client " + id;
		status = new RPIStatus(RPIStatus.IDLE);
		changeListeners = new ArrayList<ClientChangeListener<RPIClient>>();
		playerModule = new ClientPlayerModule(this);

	}

	/** Closes the clients connection, and cleans up resources */

	@Override
	public void close() {
		try {
			super.close();
			handler.close();
		}
		catch (IOException e) {
		}
	}

	/** Returns the status of this client */

	public RPIStatus getStatus() {
		return status;
	}

	/** Sets the status of this client to the given status */

	public void setStatus(RPIStatus status) {
		this.status = status;
		ClientChangeEvent<RPIClient> changeEvent = new ClientChangeEvent<RPIClient>(this, ClientChangeEvent.EVENT_STATUS_CHANGE);
		notifyChangeListeners(changeEvent); //notify listeners that the status has been changed
	}

	/** Returns the name of this client */

	public String getName() {
		return name;
	}

	/** Sets the name of the client to the given string */

	public void setName(String name) {
		this.name = name;
		ClientChangeEvent<RPIClient> changeEvent = new ClientChangeEvent<RPIClient>(this, ClientChangeEvent.EVENT_NAME_CHANGE);
		notifyChangeListeners(changeEvent); //notify listeners that the status has been changed
	}

	/** To string method of RPI CLient, returns the clients name
	 * 
	 */

	@Override
	public String toString() {
		return getName();
	}

	/** Sets the connected value of this client */

	@Override
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * @return the playerModule
	 */
	public ClientPlayerModule getPlayerModule() {
		return playerModule;
	}

	/** Adds the given change listener */
	public void addChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.add(listener);
	}

	/** Removes the given change listener */
	public void removeChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.remove(listener);
	}

	protected void notifyChangeListeners(ClientChangeEvent<RPIClient> changeEvent) {
		for (ClientChangeListener<RPIClient> listener : changeListeners) {
			listener.clientChanged(changeEvent);
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
