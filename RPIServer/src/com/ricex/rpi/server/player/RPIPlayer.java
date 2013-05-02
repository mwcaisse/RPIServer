package com.ricex.rpi.server.player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	
	/** The lsit view for the movies */
	private MovieListView movieListView;
	
	/** The pane for the button controls on the right */
	private ButtonPane buttonPane;
	
	public RPIPlayer() {
		/* For now lets not run the server
		server = new RPIServer(RPIServer.PORT);
		serverThread = new Thread(server);
		serverThread.setDaemon(true);
		serverThread.start();	
		*/
		
	}
	
	public static void main (String[] args) {
		launch();
	}
	

	public void start(Stage stage) {
		stage.setTitle("RPI Player");
		
		movieListView = new MovieListView();	
		buttonPane = new ButtonPane();
		
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(movieListView);		
		borderPane.setRight(buttonPane);
		
		Scene scene = new Scene(borderPane, 800, 600);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
}
