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
	
	public static ClientCache getInstance() {
		if (_instance == null) {
			_instance = new ClientCache();
		}
		return _instance;
	}
	
	/** The list of clients in the cache */
	private List<RemoteClient> clients;
	
	
	private ClientCache() {
		clients = new ArrayList<RemoteClient>();
	}
	
	public void setClients(List<RemoteClient> clients) {
		this.clients = clients;
	}
}
