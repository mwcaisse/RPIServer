package com.ricex.rpi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ricex.rpi.server.client.RemoteClient;

/** Thread for handling the user input for the client
 * 
 * @author Mitchell Caisse
 *
 */

public class InputHandler implements Runnable {
	
	/** The remote server */
	private Server<RemoteClient> remoteServer;
	
	/** Indicates whether or not the input handler should continue running */
	private boolean running;
	
	/** Creates a new instance of InputHandler
	 * 
	 */
	
	public InputHandler() {
		remoteServer = RemoteServer.getInstance();
		running = true;
	}
	
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to RPIClient!");
		System.out.println("Usage: start - start server to server \n\t stop - stop server \n\t quit - quit \n\t status - status");
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
		if (line.toLowerCase().startsWith("start")) {
			remoteServer.startServer();
		}
		else if (line.startsWith("stop")) {
			remoteServer.stopServer();
		}
		else if (line.startsWith("quit")) {
			running = false;
		}
		else if (line.startsWith("status")) {
			if (remoteServer.isRunning()) {
				System.out.println("Server is running");
			}
			else {
				System.out.println("Server is not running");
			}
		}
	}
}
