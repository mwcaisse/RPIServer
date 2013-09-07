package com.ricex.rpi.remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.ricex.rpi.remote.player.RPIPlayer;

/** Handles all communication to and from the server
 * 
 * @author Mitchell Caisse
 *
 */

public class ServerHandler implements Runnable {

	
	/** The socket that represents the connection to the server */
	protected Socket serverSocket;
	
	/** The input stream from the server */
	private ObjectInputStream inStream;
	
	/** The output stream to the server */
	private ObjectOutputStream outStream;
	
	/** Whether or not we are connected to the server */
	private boolean connected;
	
	/** The RPI Player that this server handler is representing */
	protected RPIPlayer player;
	
	/** Creates a new server handler for the given socket
	 * 
	 * @param socket The socket containing the connection to the server
	 */
	
	public ServerHandler(Socket serverSocket) throws IOException {
		this.serverSocket = serverSocket;
		
		inStream = new ObjectInputStream(serverSocket.getInputStream());
		outStream = new ObjectOutputStream(serverSocket.getOutputStream());
		
		createRPIPlayer();
	}
	
	/** Creates the RPI Player
	 * 
	 */
	
	protected void createRPIPlayer() {
		player = new RPIPlayer();
	}
	
	/** Return the player that this handler is representing
	 * 
	 * @return the player
	 */
	
	public RPIPlayer getPlayer() {
		return player;
	}
	
	/** Listens for messages from the server
	 * 
	 */
	
	public void run() {
		
	}
	                   
	                   
}
