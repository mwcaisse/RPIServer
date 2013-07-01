package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.common.message.remote.ClientListMessage;
import com.ricex.rpi.common.message.remote.ClientStatusUpdateMessage;
import com.ricex.rpi.common.message.remote.ClientUpdateMessage;
import com.ricex.rpi.server.client.ClientChangeEvent;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.client.RemoteClient;

/** The server that listens for connections from RPIRemotes, and creates threads to respond to them
 * 
 * @author Mitchell
 *
 */

public class RemoteServer extends Server<RemoteClient> implements ClientConnectionListener<RPIClient>, ClientChangeListener<RPIClient> {

	/** The singleton instance of this server */
	private static RemoteServer _instance;
	
	/** Returns the singleton instance */
	
	public static RemoteServer getInstance() {
		if (_instance == null) {
			_instance = new RemoteServer();
		}
		return _instance;
	}
	
	/** Creates a new Remote Server */
	
	private RemoteServer() {
		super(RPIServerProperties.getInstance().getRemotePort(), RPIServerProperties.getInstance().getMaxConnectins(), "RemoteServer");
		RPIServer.getInstance().addConnectionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected RemoteClient createClient(Socket socket) {
		RemoteClient client = new RemoteClient(this, getNextId(), socket);		

		//send the list of rpi clients to the remote client
		client.sendMessage(constructClientListMessage());
			
		return client;
	}

	/** Notifies all remote clients when an RPIClietn connects */
	
	@Override
	public void clientConnected(RPIClient client) {
		sendToAllClients(new ClientUpdateMessage(client.getId(), client.getName(), true, null));
		client.addChangeListener(this);
	}

	/** Notifies all remote clients when an RPIClient disconencts */
	
	@Override
	public void clientDisconnected(RPIClient client) {
		sendToAllClients(new ClientUpdateMessage(client.getId(), client.getName(), false, null));
		client.removeChangeListener(this);
	}
	
	/** Creates a client list message */
	
	private ClientListMessage constructClientListMessage() {
		ClientListMessage clientMessage = new ClientListMessage();
		for (RPIClient c : RPIServer.getInstance().getConnectedClients()) {
			clientMessage.addClient(c.getId(), c.getName(), c.getStatus(), c.getRootDirectory());
		}
		return clientMessage;
	}

	/** Notifies all connected remote clients when a RPIClient changes its status
	 * 
	 */
	
	@Override
	public void clientChanged(ClientChangeEvent<RPIClient> changeEvent) {
		if (changeEvent.getEventType() == ClientChangeEvent.EVENT_STATUS_CHANGE) {
			RPIClient client = changeEvent.getSource();
			sendToAllClients(new ClientStatusUpdateMessage(client.getId(), client.getStatus()));
		}
	}
	                                                        
	                                                       

}
