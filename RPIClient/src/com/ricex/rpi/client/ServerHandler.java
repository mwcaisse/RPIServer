package com.ricex.rpi.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.common.message.NameMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.StatusMessage;
import com.ricex.rpi.common.message.StatusRequestMessage;

/**
 * Server handler, contols all interact with the server
 * 
 * @author Mitchell Caisse
 * 
 */

public class ServerHandler implements Runnable {

	/** Socket that contains the connection to the server */
	private Socket serverSocket;

	/** Input stream to read data from the sever */
	private ObjectInputStream inStream;

	/** Output stream to write to the server */
	private ObjectOutputStream outStream;
	
	/** The playermodule that will be used by this handler */
	private PlayerModule playerModule;
	
	/** Whether or not we are connected to the server */
	private boolean connected;
	
	/** The client this server handler is listening for */
	private RPIClient client;

	/**
	 * Creates a new ServerHandle with the given socket
	 * 
	 * @param socket
	 * @return
	 * @throws IOException
	 *             if it cannot create in/out streams
	 */

	public ServerHandler(RPIClient client, Socket socket) throws IOException {
		this.client = client;
		this.serverSocket = socket;

		playerModule = new ThreadedPlayerModule(this);
		
		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());
		
		connected = true;
		sendName();
	}
	
	/** Sends the name of this client to the server */
	
	protected void sendName() {
		String name = RPIClientProperties.getInstance().getName();
		// if there is a name set, send it to server
		if (name != null) {
			sendMessage(new NameMessage(name));
		}
	}

	/**
	 * Sends the given message to the server
	 * 
	 * @param msg
	 *            The message to send
	 * @return True if successful false otherwise
	 */

	public synchronized boolean sendMessage(IMessage msg) {
		try {
			System.out.println("Sending message to server: " + msg);
			outStream.writeObject(msg);
			outStream.flush();
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Waits for the server to send a message, and prints it to stdout when one
	 * is received
	 * 
	 */

	public void run() {

		System.out.println("We connected to the server, lets wait for messages!");
		while (connected) {
			try {
				Object input = inStream.readObject();
				if (!(input instanceof IMessage)) {
					System.out.println("Received invalid message object");
					continue;
				}
				IMessage msg = (IMessage) input;
				processMessage(msg); // process the received message
			}
			catch (ClassNotFoundException e) {
				System.out.println("Received invalid class");
				e.printStackTrace();
			}
			catch (IOException e) {
				System.out.println("IOException");
				connected = false; //received an IO exception, lets disconnect..
			}
		}
		
		sendMessage(new QuitMessage());
		
		//while we are quitting we should close the out stream
		try {
			outStream.close();
		}
		catch (IOException e) {
			System.out.println("Could not close the out stream");
		}

		System.out.println("Disconnecting from server");
		//stop the playerModule from playing
		System.out.println("We are abotu to stop the player module");
		playerModule.stop();
		System.out.println("The player module has stopped");

	}	
	
	/**
	 * Processes the message received
	 * 
	 * @param message
	 *            The message that was received from the server
	 */

	public void processMessage(IMessage message) {
		if (message instanceof MovieMessage) {
			System.out.println("We received a movie message from the server");
			
			// this is a movie message, lets print it out
			((MovieMessage)message).execute(playerModule);
		}
		else if (message instanceof StatusRequestMessage) {
			//send a status message to the server
			System.out.println("We received a StatusRequestMessage from server, sending status message");
			IMessage toSend = new StatusMessage(playerModule.getStatus());
			sendMessage(toSend);
		}
		else if (message instanceof QuitMessage) {
			disconnect();
		}
		else {
			System.out.println("Msg received: " + message);
		}
	}
	
	/** Disconnects from the server
	 * 
	 */
	
	public void disconnect() {
		connected = false;
		try {
			inStream.close();
		}
		catch (IOException e) {
			System.out.println("Unable to close input stream");
		}
	}
	
	/** Returns whether or not this is not connected to the server
	 * 
	 * @return
	 */
	
	public boolean isConnected() {
		return connected;
	}
}
