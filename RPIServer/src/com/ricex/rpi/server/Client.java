package com.ricex.rpi.server;

import java.net.Socket;

/** A client that connects to the given server interface 
 * 
 * @author Mitchell
 *
 */

public abstract class Client {

	/** The id of this client */
	final protected long id;
	
	/** The socket that the client connected on */
	final protected Socket socket;
	
	/** Creates a new client with the given id and socket
	 * 
	 * @param id The ID of the client
	 * @param socket The socket through which the client connected
	 */
	
	public Client(long id, Socket socket) {
		this.id = id;
		this.socket = socket;
	}
}
