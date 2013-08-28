package com.ricex.rpi.remote.client.handler;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.remote.client.Client;
import com.ricex.rpi.remote.imbdparser.IMDBMovieParser;


public abstract class ClientHandler<T extends Client> implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

	
	/** The client that this handler is for */
	final protected T client;
	
	/** The input stream of this handler */
	private ObjectInputStream inStream;
	
	/** The output stream of this handler */
	private ObjectOutputStream outStream;
	
	/** Creates a new ClientHandler to handle the given client */
	
	public ClientHandler(T client) {
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
			log.error("Unable to create client output stream", e);
		}	
	}
	
	public void run() {
		//create the input stream
		try {
			inStream = new ObjectInputStream(client.getSocket().getInputStream());
		}
		catch (IOException e) {
			log.error("Unable to create input stream for the client", e);
			return;
		}
		
		//only listen for messages while the client is connected
		while (client.isConnected()) {
			try {
				Object inputObject = inStream.readObject();
				processMessage((IMessage) inputObject);
			}
			catch (EOFException e) {
				log.warn("The input stream of the client has ended", e);
				break;
			}
			catch (IOException e) {
				log.error("Error while listening for message", e);
			}
			catch (ClassNotFoundException e) {
				log.error("Received unsupported class", e);
			}
		}	
		log.debug("ClientHandler has left server loop");
		client.disconnect();
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
