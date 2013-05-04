package com.ricex.rpi.server.player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.ServerPlayerModule;
import com.ricex.rpi.server.StatusListener;

/** The gui for the server that will report status of the server, as well
 * 		as allowing the user to control the playing movie
 * 
 * @author Mitchell
 *
 */

public class RPIPlayer extends Application implements StatusListener {
	
	/** Instance of the server that this GUI will interact with */
	//TODO: should probally make this an interface later on
	private RPIServer server;
	
	/** The thread that the sever is running in */
	private Thread serverThread;
	
	/** The lsit view for the movies */
	private VideoListView movieListView;
	
	/** The pane for the button controls on the right */
	private ButtonPane buttonPane;
	
	/** The player module for this GUI */
	private PlayerModule playerModule;	
	
	/** The status label */
	Label labStatus;
	
	public RPIPlayer() {
		server = new RPIServer();
		serverThread = new Thread(server);
		serverThread.setDaemon(true);
		serverThread.start();	
		
		playerModule = new ServerPlayerModule(server);
		
		
	}
	
	public static void main (String[] args) {
		launch();		
	}
	

	public void start(Stage stage) {
		stage.setTitle("RPI Player");
		
		movieListView = new VideoListView(createVideoRoot());	
		buttonPane = new ButtonPane(playerModule, movieListView);
		
		labStatus = new Label("Status goes here");
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(movieListView);		
		borderPane.setRight(buttonPane);
		borderPane.setBottom(labStatus);
		
		Scene scene = new Scene(borderPane, 800, 600);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	
	private Video createVideoRoot() {
		Directory root = new Directory("Movies");
		
		for (int i=0;i<10;i++) {
			root.addChild(new Movie("Movie: " + i));
		}
		
		return root;
	}


	public void statusChanged(RPIStatus status, String filePlaying) {
		if (status == RPIStatus.IDLE) {
			labStatus.setText("Status: " + status);
		}
		else {
			labStatus.setText("Status: " + status + " " + filePlaying);
		}
	}
}
