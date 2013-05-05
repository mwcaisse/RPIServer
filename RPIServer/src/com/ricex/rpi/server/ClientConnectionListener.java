package com.ricex.rpi.server;

/** Interface for listening in changes to a client 
 * 
 * @author Mitchell
 *
 */

public interface ClientConnectionListener {

	/** The given client has connected to the server */
	public void clientConnected(Client client);
	
	/** The given client has disconnected from the server */
	public void clientDisconnected(Client client);
}
