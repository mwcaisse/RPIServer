package com.ricex.rpi.remote.android.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.remote.ClientListMessage;
import com.ricex.rpi.common.message.remote.ClientStatusUpdateMessage;
import com.ricex.rpi.common.message.remote.ClientUpdateMessage;
import com.ricex.rpi.common.message.remote.DirectoryListingMessage;
import com.ricex.rpi.common.message.remote.RemoteClient;
import com.ricex.rpi.remote.android.cache.ClientCache;
import com.ricex.rpi.remote.android.cache.DirectoryCache;


/** Runs in the background and listens for messages received from the server
 * 
 * @author Mitchell
 *
 */
public class ServerHandler implements Runnable {

	/** The socket throught which this client is connected to the server */
	private Socket socket;

	/** The stream in from the server */
	private ObjectInputStream inStream;

	/** The stream out to the server */
	private ObjectOutputStream outStream;

	/** Boolean whether to cointinue running or not */
	private boolean running;

	/** The server connection listener to notify when we disconnect from the server */
	private ServerConnectionListener connectionListener;


	/** Creates a new server handle to listen on the given socket
	 * 
	 * @param socket The socket representing the connection to the server
	 */
	public ServerHandler(Socket socket, ServerConnectionListener connectionListener) throws IOException {
		this.socket = socket;
		this.connectionListener = connectionListener;
		running = true;

		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());

		Log.i("ServerHandler", "We have created the in and out streams");
	}


	/** Sends the given message to the server
	 * 
	 * @param message The message to send
	 * @return True if sucessful false otherwise
	 */

	public synchronized boolean sendMessage(IMessage message) {
		Log.i("RPIServerHandler", "Sending message to server: " + message);
		try {
			outStream.writeObject(message);
			outStream.flush();
		}
		catch (IOException e) {
			Log.e("RPIServerHandler","Failed to send message to server.", e);
			return false; // send failed
		}
		return true;
	}

	/** Runs the handler, listeners for messages from the server, and processes the message that
	 * it received
	 */

	@Override
	public void run() {
		//listn for messages from the server

		Log.i("RPIServerHandler", "We are starting the server handler thread");

		Object input;

		while (running) {
			try {
				input = inStream.readObject();

				//we have the message lets do something with it
				if (!(input instanceof IMessage)) {
					Log.e("RPIServerHandler", "Received unsupported message from the server");
				}
				else {
					processMessage((IMessage) input);
				}
			}
			catch (ClassNotFoundException e) {
				Log.e("RPIServerHandler", "Received a message of an unknown class type", e);
			}
			catch (EOFException e) {
				//stream has been disconnected, exit from loop
				break;
			}
			catch (IOException e) {
				Log.e("RPIServerHandler", "IOException when recieving message from server", e);
			}
		}

		try {
			inStream.close();
			outStream.close();
		}
		catch (IOException e) {
			Log.e("RPIServerHandler", "Error closing input and output stream", e);
		}
		finally {
			//we have left the server loop, and we are disconnected from the server
			//notify our listener
			connectionListener.serverConnectionChanged(false);
		}

		Log.i("RPIServerHandler", "Server Handler thread has completed");
	}

	public void close() {
		running = false;
	}

	/** Processes a message that has been received from the server
	 * 
	 * @param message The message received from the server
	 */

	protected synchronized void processMessage(IMessage message) {
		Log.i("ServerHandler", "Message received from server! " + message);
		if (message instanceof ClientListMessage) {
			ClientListMessage msg = (ClientListMessage) message;
			//put the clients into the client cache
			ClientCache.getInstance().setClients(msg.getClients());
		}
		else if (message instanceof ClientUpdateMessage) {
			ClientUpdateMessage msg = (ClientUpdateMessage) message;
			RemoteClient client = new RemoteClient(msg.getId(), msg.getName(), new RPIStatus(RPIStatus.IDLE));
			if (msg.isConnected()) {
				
			}
			else {
				//the client in the message has disconnected
			}
		}
		else if (message instanceof ClientStatusUpdateMessage) {
			ClientStatusUpdateMessage msg = (ClientStatusUpdateMessage) message;
			RemoteClient client = ClientCache.getInstance().getClient(msg.getId());
			client.setStatus(msg.getStatus()); //update the status of the client
			
		}
		else if (message instanceof DirectoryListingMessage) {
			DirectoryListingMessage msg = (DirectoryListingMessage) message;
			//put the root directory into the dir cache
			DirectoryCache.getInstance().setRootDirectory(msg.getRootDirectory());
		}
	}
}
