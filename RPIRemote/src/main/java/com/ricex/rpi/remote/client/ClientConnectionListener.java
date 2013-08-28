package com.ricex.rpi.remote.client;


/** Interface for listening in changes to a client 
 * 
 * @author Mitchell Caisse
 *
 */

public interface ClientConnectionListener<T extends Client> {

	/** The given client has connected to the server */
	public void clientConnected(T client);
	
	/** The given client has disconnected from the server */
	public void clientDisconnected(T client);
}
