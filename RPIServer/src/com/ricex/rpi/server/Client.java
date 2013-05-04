package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.RPIStatus;

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
	
	/** The RPIServer */
	private RPIServer server;
	
	public Client (Socket socket, RPIServer server) {
		this.socket = socket;
		this.handler = new ClientHandler(this);		
		
		connected = true;
		
		clientThread = new Thread(handler);
		clientThread.start();
		
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
	
	/** Sends a message to this client
	 * 
	 * @param message The message to send
	 * @return Whether the send was sucessful or not
	 */
	
	public boolean sendMessage(IMessage message) {
		System.out.println("Sending message to client: " + message);
		return handler.sendMessage(message);
	}
	
	public void notifyStatusListeners(RPIStatus status, String filePlaying) {
		server.notifityStatusListeners(status,filePlaying);
	}
}
