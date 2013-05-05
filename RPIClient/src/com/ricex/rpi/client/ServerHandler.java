package com.ricex.rpi.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.MovieMessage;
import com.ricex.rpi.common.StatusMessage;
import com.ricex.rpi.common.StatusRequestMessage;
import com.ricex.rpi.common.StringMessage;

/**
 * Server handler, contols all interact with the server
 * 
 * @author Mitchell
 * 
 */

public class ServerHandler implements Runnable {

	/** Socket that contains the connection to the server */
	private Socket serverSocket;

	/** Input stream to read data from the sever */
	private ObjectInputStream inStream;

	/** Output stream to write to the server */
	private ObjectOutputStream outStream;

	/** The thread that this class uses to listen for input from the server */
	private Thread thread;

	/**
	 * Creates a new ServerHandle with the given socket
	 * 
	 * @param socket
	 * @return
	 * @throws IOException
	 *             if it cannot create in/out streams
	 */

	public ServerHandler(Socket socket) throws IOException {
		this.serverSocket = socket;

		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());
		sendMessage(new StringMessage("Hello server!"));
	}

	/**
	 * Sends the given message to the server
	 * 
	 * @param msg
	 *            The message to send
	 * @return True if successful false otherwise
	 */

	public boolean sendMessage(IMessage msg) {
		try {
			outStream.writeObject(msg);
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

		Object input;
		try {
			while ((input = inStream.readObject()) != null) {
				if (!(input instanceof IMessage)) {
					System.out.println("Received invalid message object");
					continue;
				}
				IMessage msg = (IMessage) input;
				processMessage(msg); // process the received message
			}
		}
		catch (ClassNotFoundException | IOException e) {
			System.out.println("Received invalid class");
			e.printStackTrace();
		}

		System.out.println("Disconnecting from server");

	}

	/**
	 * Processes the message received
	 * 
	 * @param message
	 *            The message that was received from the server
	 */

	private void processMessage(IMessage message) {
		if (message instanceof MovieMessage) {
			System.out.println("We received a movie message from the server");
			
			// this is a movie message, lets print it out
			((MovieMessage)message).execute(ThreadedPlayerModule.getInstance());
		}
		else if (message instanceof StatusRequestMessage) {
			//send a status message to the server
			IMessage toSend = new StatusMessage(ThreadedPlayerModule.getInstance().getStatus());
			sendMessage(toSend);
		}
		else {
			System.out.println("Msg received: " + message);
		}
	}
}
