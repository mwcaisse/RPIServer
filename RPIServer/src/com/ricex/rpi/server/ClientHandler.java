package com.ricex.rpi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.NameMessage;
import com.ricex.rpi.common.StatusMessage;

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
		}
		catch (IOException e) {
			System.out.println("Error creating output stream");
		}
	}

	public void run() {
		try {
			inStream = new ObjectInputStream(client.getSocket().getInputStream());
			Object inputObject;
			while ( (inputObject = inStream.readObject()) != null) {
				processMessage((IMessage) inputObject);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("ClientHandler, we left server loop");
		
		//we are done, notify of disconnect
		client.setConnected(false);
	}
	
	/** Closes the input and output streams used by this handler
	 * 
	 * @throws IOException
	 */
	
	public synchronized void close() throws IOException {
		outStream.close();
		inStream.close();
	}
	
	private void processMessage(IMessage msg) {
		if (msg instanceof StatusMessage) {
			StatusMessage smsg = (StatusMessage) msg;
			client.setStatus(smsg.getStatus());
			System.out.println("Received status message from client: " + smsg.getStatus());
		}
		else if (msg instanceof NameMessage) {
			NameMessage nmsg = (NameMessage) msg;
			client.setName(nmsg.getName());
			System.out.println("Received name message from client: " + nmsg.getName());
		}
		else {
			System.out.println("Message received from client: " + msg);
		}
	}
	
	/** Sends the given message to the client
	 * 
	 * @param msg Object representing the message to send
	 * @return
	 */
	
	public synchronized boolean sendMessage(IMessage msg) {
		try {			
			outStream.writeObject(msg);
			outStream.flush();
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}
}
