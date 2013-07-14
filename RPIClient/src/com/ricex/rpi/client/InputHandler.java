package com.ricex.rpi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;

/** Thread for handling the user input for the client
 * 
 * @author Mitchell
 *
 */

public class InputHandler implements Runnable {

	/** The rpi client */
	private RPIClient client;
	
	/** The movie parser to parse the movies */
	private MovieParser movieParser;
	
	/** The root directory */
	private Video rootDirectory;
	
	/** Indicates whether or not the input handler should continue running */
	private boolean running;
	
	/** Creates a new instance of InputHandler
	 * 
	 */
	
	public InputHandler() {
		movieParser = new MovieParser(RPIClientProperties.getInstance().getBaseDir());
		rootDirectory = movieParser.parseVideos();
		client = new RPIClient(rootDirectory);
		running = true;
	}
	
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to RPIClient!");
		System.out.println("Usage: c - connect to server \n\t d - disconnect from server \n\t q - quit \n\t s - status" + 
					"\n\t r -reparse");
		System.out.print("RPI>");
		
		while (running) {
			try {
				if (System.in.available() > 0) {
					String line = reader.readLine().toLowerCase();
					processInput(line);	
					if (running) {
						System.out.print("RPI>");
					}
				}
				else {
					Thread.sleep(500);
				}
			}
			catch (IOException e) {
				System.out.println("Error reading input");
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("We are exiting the client..");
	}
	
	/** Processes the given input line
	 * 
	 * @param line The line of input the user has entered
	 * @throws IOException IOException if there was an issue connecting to the server
	 */
	
	private void processInput(String line) throws IOException {
		if (line.toLowerCase().startsWith("c")) {
			if (!client.isConnected()) {
				//only re-connect to the server if we are not already connected
				client.connectToServer();
			}
		}
		else if (line.startsWith("d")) {
			client.disconnectFromServer();
			System.out.println("We have disconnected from the server");
		}
		else if (line.startsWith("q")) {
			if (client.isConnected()) {
				client.disconnectFromServer();
			}
			running = false;
		}
		else if (line.startsWith("s")) {
			if (client.isConnected()) {
				System.out.println("We are currently connected to server: " + client.getServerInfo());
			}
			else {
				System.out.println("We are not currently connected to any server");
			}	
		}
		else if (line.startsWith("r")) {
			rootDirectory = movieParser.parseVideos();
			System.out.println("Finished parsing videos");
			if (client.isConnected()) {
				client.updateRootDirectory(rootDirectory);
			}						
		}	
	}
}
