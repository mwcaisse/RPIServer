package com.ricex.rpi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.MovieMessage;
import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.StatusMessage;
import com.ricex.rpi.common.StatusRequestMessage;

/**
 * RPI client that connects to the server
 * 
 * @author Mitchell
 * 
 */

public class RPIClient {

	/** The socket this will use to conenct to the server */
	private Socket socket;
	
	/** The server handle for this client */
	private ServerHandler serverHandler;
	
	/** The ip address of the server */
	private String serverIp;
	
	/** Port of the server to connect to */
	private int serverPort;
	
	/** The player module used by this client */
	private PlayerModule playerModule;

	public RPIClient() throws UnknownHostException, IOException {
		serverIp = RPIClientProperties.getInstance().getServerIp();
		serverPort = RPIClientProperties.getInstance().getRPIPort();
		
		socket = new Socket(serverIp, serverPort);
		serverHandler = new ServerHandler(socket, this);
		playerModule = new ThreadedPlayerModule(serverHandler);
		
		//block on the server handler
		serverHandler.run();
	}

	public static void main(String[] args) {

		RPIClient client;
		
		while (true) {			
			try {
				client = new RPIClient();
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				System.out.println("Connection refused, try again in 15 mins");
				try {
					Thread.sleep( 1000 * 60 * 15); //sleep for 15 mins
					
				}
				catch (InterruptedException ex) {
					System.out.println("Waiting interupted, trying to connect again");
				}
			}
		}
	}
	
	/**
	 * Processes the message received
	 * 
	 * @param message
	 *            The message that was received from the server
	 */

	public synchronized void processMessage(IMessage message) {
		if (message instanceof MovieMessage) {
			System.out.println("We received a movie message from the server");
			
			// this is a movie message, lets print it out
			((MovieMessage)message).execute(playerModule);
		}
		else if (message instanceof StatusRequestMessage) {
			//send a status message to the server
			System.out.println("We received a StatusRequestMessage from server, sending status message");
			IMessage toSend = new StatusMessage(playerModule.getStatus());
			serverHandler.sendMessage(toSend);
		}
		else {
			System.out.println("Msg received: " + message);
		}
	}
}
