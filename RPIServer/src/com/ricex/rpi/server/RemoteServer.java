package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.common.message.remote.ClientListMessage;
import com.ricex.rpi.common.message.remote.ClientUpdateMessage;
import com.ricex.rpi.common.message.remote.DirectoryListingMessage;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.client.RemoteClient;
import com.ricex.rpi.server.player.MovieParser;

/** The server that listens for connections from RPIRemotes, and creates threads to respond to them
 * 
 * @author Mitchell
 *
 */

public class RemoteServer extends Server<RemoteClient> implements ClientConnectionListener<RPIClient> {

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
		
		//send the directory listing to the clint
		MovieParser mp = new MovieParser(RPIServerProperties.getInstance().getBaseDir());
		client.sendMessage(new DirectoryListingMessage(mp.parseVideos()));
		
		return client;
	}

	@Override
	public void clientConnected(RPIClient client) {
		sendToAllClients(new ClientUpdateMessage(client.getId(), client.getName(), true));
	}

	@Override
	public void clientDisconnected(RPIClient client) {
		sendToAllClients(new ClientUpdateMessage(client.getId(), client.getName(), false));
	}
	
	/** Creates a client list message */
	
	private ClientListMessage constructClientListMessage() {
		ClientListMessage clientMessage = new ClientListMessage();
		for (RPIClient c : RPIServer.getInstance().getConnectedClients()) {
			clientMessage.addClient(c.getId(), c.getName(), c.getStatus());
		}
		return clientMessage;
	}
	                                                        
	                                                       

}
