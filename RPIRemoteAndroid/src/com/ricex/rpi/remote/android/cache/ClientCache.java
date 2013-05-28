package com.ricex.rpi.remote.android.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Long, RemoteClient> clients;
	
	/** Creates a new instance of the client cache */
	
	private ClientCache() {
		clients = new HashMap<Long, RemoteClient>();
	}
	
	/** Sets the list of clients to the given list
	 * 
	 * @param clients The list of clients to set
	 */
	
	public void setClients(List<RemoteClient> clients) {
		this.clients.clear();
		for (RemoteClient client: clients) {
			this.clients.put(client.getId(), client);
		}
	}
	
	/** Adds the given client to the list of clients
	 * 
	 * @param client The client to add
	 */
	
	public void addClient(RemoteClient client) {
		clients.put(client.getId(), client);
	}
	
	/** Removes the client with the given id
	 * 
	 * @param id Id of the client to remove
	 */
	
	public void removeClient(long id) {
		clients.remove(id);
	}
	
	/** Removes the given client
	 * 
	 * @param client The client to remove
	 */
	
	public void removeClient(RemoteClient client) {
		removeClient(client.getId());
	}
	
	/** Returns the client with the given id
	 * 
	 * @param id Id of the client to fetch
	 * @return
	 */
	
	public RemoteClient getClient(long id) {
		return clients.get(id);
	}
	
	/** Returns a list of all the clients */
	
	public List<RemoteClient> getClients() {
		return new ArrayList<RemoteClient>(clients.values());
	}	   
	
	/** Returns a list of all the enabled clients */
	
	public List<RemoteClient> getEnabledClients() {
		List<RemoteClient> activeClients = new ArrayList<RemoteClient>();
		for (RemoteClient client : getClients()) {
			if (client.isEnabled()) {
				activeClients.add(client);
			}
		}
		return activeClients;
	}
}
