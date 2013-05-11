package com.ricex.rpi.common.message.remote;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.message.IMessage;


/** Sends back a list of all the currently connected RPI Clients
 * 
 * @author Mitchell
 *
 */

public class ClientListMessage implements IMessage {

	/** List of the clients to send */
	private List<RemoteClient> clients;
	
	/** Creates a new ClientListMessage with an empty list of clients */
	public ClientListMessage() {
		this(new ArrayList<RemoteClient>());
	}
	
	/** Creates a new ClientListMessage to send the given clietns
	 * 
	 * @param clients The clients to send
	 */
	public ClientListMessage(List<RemoteClient> clients) {
		this.clients = clients;
	}
	
	/** Adds a client to the list to send with the given id and name 
	 * 
	 * @param id The clients id
	 * @param name The clients name
	 */
	
	public void addClient(long id, String name) {
		clients.add(new RemoteClient(id, name));
	}
	
	/** Returns the list of clients */	
	public List<RemoteClient> getClients() {
		return clients;
	}
	
}
