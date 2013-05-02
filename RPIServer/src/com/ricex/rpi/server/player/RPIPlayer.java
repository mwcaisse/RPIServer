package com.ricex.rpi.server.player;

import javafx.application.Application;
import javafx.stage.Stage;

import com.ricex.rpi.server.RPIServer;

/** The gui for the server that will report status of the server, as well
 * 		as allowing the user to control the playing movie
 * 
 * @author Mitchell
 *
 */

public class RPIPlayer extends Application {

	
	/** Instance of the server that this GUI will interact with */
	//TODO: should probally make this an interface later on
	private RPIServer server;
	
	/** The thread that the sever is running in */
	private Thread serverThread;
	
	public RPIPlayer() {
		server = new RPIServer(RPIServer.PORT);
		serverThread = new Thread(server);
		serverThread.setDaemon(true);
		serverThread.start();	
		
	}
	
	public static void main (String[] args) {
		launch();
	}
	

	public void start(Stage stage) {
		stage.setTitle("RPI Player");
		stage.show();
		stage.centerOnScreen();
	}
}
