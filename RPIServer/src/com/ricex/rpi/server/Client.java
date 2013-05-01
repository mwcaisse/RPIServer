package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.common.MovieMessage;

/** A client that is connected to the server
 * 
 * @author Mitchell
 *
 */

//TODO: Add some type of ID and/or name to the client
//TODO: Add a way to distinguish between RPI Clients and RemoteClients
//			most likely just ask at start
//TODO: Possibly turn this into an interface when we have multiple versions of the client

public class Client {

	/** This Clients socket */
	private Socket socket;
	
	/** The client handler for this client */
	private ClientHandler handler;
	
	/** The thread that this client is executing in */
	private Thread clientThread;
	
	/** Indicates wether this client is still connected or not */
	private boolean connected = false;
	
	public Client (Socket socket) {
		this.socket = socket;
		this.handler = new ClientHandler(this);		
		
		connected = true;
		
		clientThread = new Thread(handler);
		clientThread.start();	
		
		//after we connect, lets send a message to play a video, and see how it works. muahah
		handler.sendMessage(new MovieMessage("Movies/Red_720p.mkv", MovieMessage.Command.PLAY));
		
		//sleep for 60 seconds
		try {
			Thread.sleep(1000 * 60);
		}
		catch (InterruptedException e) {
			//gah
		}
		//after 20 seconds stop the movie to see if this works
		handler.sendMessage(new MovieMessage("Movies/Red_720p.mkv", MovieMessage.Command.STOP));
		
	}
	
	/** Returns the socket that this client is connected on
	 */
	
	public Socket getSocket() {
		return socket;
	}
	
	/** Returns if the client is currently connected or not */
	
	public boolean isConnected() {
		return connected;
	}
	
	/** Sets the connected value of this client */
	
	protected void setConnected(boolean connected) {
		this.connected = connected;
	}
}
