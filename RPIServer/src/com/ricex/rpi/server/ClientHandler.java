package com.ricex.rpi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.StringMessage;

public class ClientHandler implements Runnable {

	/** The client that this handler operations on */
	private Client client;

	/** Stream to read data from the client */
	private ObjectInputStream inStream;
	
	/** Stream to write data to the client */
	private ObjectOutputStream outStream;

	public ClientHandler(Client client) {
		this.client = client;
		
		try {
			outStream = new ObjectOutputStream(client.getSocket().getOutputStream());
			inStream = new ObjectInputStream(client.getSocket().getInputStream());
		}
		catch (IOException e) {
			System.out.println("Error creating input/output streams");
		}
	}

	public void run() {
		try {		
			Object inputObject;
			while ( (inputObject = inStream.readObject()) != null) {
				System.out.println("Received message from client: " + inputObject);
				sendMessage(new StringMessage("Ack"));
			}

		}
		catch (IOException e) {

		}
		catch (ClassNotFoundException e) {
			
		}

		
		//we are done, notify of disconnect
		client.setConnected(false);
	}
	
	/** Sends the given message to the client
	 * 
	 * @param msg Object representing the message to send
	 * @return
	 */
	
	public synchronized boolean sendMessage(IMessage msg) {
		try {
			outStream.writeObject(msg);
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}
}
