package com.ricex.rpi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ricex.rpi.common.message.DirectoryListingMessage;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;

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
	
	/** The thread that the server handler will run in */
	private Thread serverHandlerThread;
	
	/** The ip address of the server */
	private String serverIp;
	
	/** Port of the server to connect to */
	private int serverPort;
	
	/** The directory listing of the videos this client has to play */
	private Video rootDirectory;
	
	/** The movie parser to parse the movies */
	private MovieParser movieParser;

	public RPIClient() {
		//parse the movies
		movieParser = new MovieParser(RPIClientProperties.getInstance().getBaseDir());
		rootDirectory = movieParser.parseVideos();		
		
		serverIp = RPIClientProperties.getInstance().getServerIp();
		serverPort = RPIClientProperties.getInstance().getRPIPort();
	
	}
	
	/** Parses the movies and then sends the results to the server
	 * 
	 */
	
	public void parseMovies() {
		//parse the movies
		movieParser = new MovieParser(RPIClientProperties.getInstance().getBaseDir());
		rootDirectory = movieParser.parseVideos();
		serverHandler.sendMessage(new DirectoryListingMessage(rootDirectory));
	}
	
	/** Connects to the server
	 * 
	 * @throws UnknownHostException If the host cannot be found
	 * @throws IOException
	 */
	
	public void connectToServer() throws UnknownHostException, IOException {
		socket = new Socket(serverIp, serverPort);
		serverHandler = new ServerHandler(socket);	
		
		//create and start the server handler thread
		serverHandlerThread = new Thread(serverHandler);
		serverHandlerThread.start();
	}
	
	/** Disconnects from the server, waits for the server thread to finish, and closes the socket connections
	 * 
	 * @throws IOException
	 */
	
	public void disconnectFromServer() {
		serverHandler.disconnect();
		try {
			serverHandlerThread.join();
		}
		catch (InterruptedException e) {
			System.out.println("Error waiting on server handler thread");
			e.printStackTrace();
		}
		serverHandlerThread = null;
		
		try {
			socket.close();
		}
		catch (IOException e) {
			System.out.println("Error closing the server socket");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		InputHandler handler = new InputHandler();
		handler.run();
	}
}
