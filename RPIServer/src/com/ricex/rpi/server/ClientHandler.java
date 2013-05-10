package com.ricex.rpi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ricex.rpi.common.IMessage;


public abstract class ClientHandler implements Runnable {

	/** The client that this handler is for */
	final protected Client client;
	
	/** The input stream of this handler */
	private ObjectInputStream inStream;
	
	/** The output stream of this handler */
	private ObjectOutputStream outStream;
	
	/** Creates a new ClientHandler to handle the given client */
	
	public ClientHandler(Client client) {
		this.client = client;
		createOutputStream();
	}
	
	/** Creates the output stream for this handler 
	 * 
	 */	
	private void createOutputStream() {
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
		//rPIClient.setConnected(false);
	}
	
	/** Processes the given message after it has been received from the server
	 * 
	 * @param message The message to be processed
	 */
	
	protected abstract void processMessage(IMessage message);
	
	public synchronized void close() throws IOException {
		outStream.close();
		inStream.close();
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
