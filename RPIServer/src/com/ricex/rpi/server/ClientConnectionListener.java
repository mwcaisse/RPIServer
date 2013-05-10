package com.ricex.rpi.server;

/** Interface for listening in changes to a client 
 * 
 * @author Mitchell
 *
 */

public interface ClientConnectionListener {

	/** The given client has connected to the server */
	public void clientConnected(RPIClient rPIClient);
	
	/** The given client has disconnected from the server */
	public void clientDisconnected(RPIClient rPIClient);
}
