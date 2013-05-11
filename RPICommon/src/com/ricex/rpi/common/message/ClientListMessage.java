package com.ricex.rpi.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** Sends back a list of all the currently connected RPI Clients
 * 
 * @author Mitchell
 *
 */

public class ClientListMessage implements IMessage {

	/** List of the clients to send */
	private List<Client> clients;
	
	/** Creates a new ClientListMessage with an empty list of clients */
	public ClientListMessage() {
		this(new ArrayList<Client>());
	}
	
	/** Creates a new ClientListMessage to send the given clietns
	 * 
	 * @param clients The clients to send
	 */
	public ClientListMessage(List<Client> clients) {
		this.clients = clients;
	}
	
	/** Adds a client to the list to send with the given id and name 
	 * 
	 * @param id The clients id
	 * @param name The clients name
	 */
	
	public void addClient(long id, String name) {
		clients.add(new Client(id, name));
	}
	
	/** Returns the list of clients */	
	public List<Client> getClients() {
		return clients;
	}
	
	/** Holder class to send the clients over the network */
	
	public class Client implements Serializable {
	
		/** The id of the client */
		private long id;
		
		/** the Name of the client */
		private String name;
		
		/** Creates a new client with the given id and name */
		public Client(long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		/** Returns the id of this client */
		public long getId() {
			return id;
		}
		
		/** Returns the name of this client */
		public String getName() {
			return name;
		}
	}
	
}
