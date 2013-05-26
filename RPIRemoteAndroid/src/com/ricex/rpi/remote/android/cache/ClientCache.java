package com.ricex.rpi.remote.android.cache;

import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.message.remote.RemoteClient;

/** Maintains a list of all the RPI clients currently connected to the server
 * 
 * @author Mitchell
 *
 */

public class ClientCache {
	
	/** The singleton instance of the class */
	private static ClientCache _instance;
	
	/** Returns the singleton instance of this class */	
	public static ClientCache getInstance() {
		if (_instance == null) {
			_instance = new ClientCache();
		}
		return _instance;
	}
	
	/** The list of clients in the cache */
	private List<RemoteClient> clients;
	
	/** Creates a new instance of the client cache */
	
	private ClientCache() {
		clients = new ArrayList<RemoteClient>();
	}
	
	/** Sets the list of clients to the given list
	 * 
	 * @param clients The list of clients to set
	 */
	
	public void setClients(List<RemoteClient> clients) {
		this.clients = clients;
	}
	
	/** Adds the given client to the list of clients
	 * 
	 * @param client The client to add
	 */
	
	public void addClient(RemoteClient client) {
		clients.add(client);
	}
	
	/** Returns a list of all the clients */
	
	public List<RemoteClient> getClients() {
		return clients;
	}	   
	
	/** Returns a list of all the enabled clients */
	
	public List<RemoteClient> getEnabledClients() {
		List<RemoteClient> activeClients = new ArrayList<RemoteClient>();
		for (RemoteClient client : clients) {
			if (client.isEnabled()) {
				activeClients.add(client);
			}
		}
		return activeClients;
	}

}
