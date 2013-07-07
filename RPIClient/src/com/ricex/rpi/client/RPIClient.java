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
	
	/** The ip address of the server */
	private String serverIp;
	
	/** Port of the server to connect to */
	private int serverPort;
	
	/** The directory listing of the videos this client has to play */
	private Video rootDirectory;
	
	/** The movie parser to parse the movies */
	private MovieParser movieParser;

	public RPIClient() throws UnknownHostException, IOException {
		//parse the movies
		movieParser = new MovieParser(RPIClientProperties.getInstance().getBaseDir());
		rootDirectory = movieParser.parseVideos();		
		
		serverIp = RPIClientProperties.getInstance().getServerIp();
		serverPort = RPIClientProperties.getInstance().getRPIPort();
		
		socket = new Socket(serverIp, serverPort);
		serverHandler = new ServerHandler(socket);	
		
		//send the directory listing to the server
		serverHandler.sendMessage(new DirectoryListingMessage(rootDirectory));
		
		//block on the server handler
		serverHandler.run();
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
	}
	
	public void disconnectFromServer() throws IOException {
		serverHandler.disconnect();
		socket.close();
	}
	/*
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
	*/
}
