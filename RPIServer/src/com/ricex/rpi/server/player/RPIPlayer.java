package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.RemoteServer;
import com.ricex.rpi.server.client.RPIClient;

/** The gui for the server that will report status of the server, as well
 * 		as allowing the user to control the playing movie
 * 
 * 
 * @author Mitchell
 *
 */

public class RPIPlayer extends Application {

	/** Instance of the server that this GUI will interact with */
	private RPIServer rpiServer;

	/** The server for the remotes */
	private RemoteServer remoteServer;

	/** The thread that the rpi server will run in */
	private Thread rpiServerThread;

	/** The thread that the remote server will run in */
	private Thread remoteServerThread;

	/** The list view for the movies */
	private VideoListView movieListView;

	/** The list view for the clients */
	private ClientTableView clientTableView;

	/** The view for the playlists */
	private PlaylistView playlistView;

	/** The pane for the button controls on the right */
	private ButtonPane buttonPane;

	/** The status label */
	private Label labStatus;

	/** The tab pane for the different views */
	private TabPane tabPane;
	
	/** The currently active RPIClient */
	private RPIClient activeClient;
	
	/** List of the active client listeners */
	private List<ActiveClientListener> activeClientListeners;

	public RPIPlayer() {
		activeClientListeners = new ArrayList<ActiveClientListener>();
		initServers();
		activeClient = null;
	}

	/** Initialize two servers, and start thier threads */

	private void initServers() {
		rpiServer = RPIServer.getInstance();
		remoteServer = RemoteServer.getInstance();

		rpiServerThread = new Thread(rpiServer);
		remoteServerThread = new Thread(remoteServer);

		rpiServerThread.setDaemon(true);
		remoteServerThread.setDaemon(true);

		rpiServerThread.start();
		remoteServerThread.start();

	}

	public static void main (String[] args) {
		launch();
	}


	@Override
	public void start(Stage stage) {
		
		stage.setTitle("RPI Player");

		movieListView = new VideoListView(this);
		clientTableView = new ClientTableView(rpiServer);
		buttonPane = new ButtonPane(this, rpiServer, movieListView);
		playlistView = new PlaylistView();



		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		Tab tabMovieView = new Tab("Movies");
		tabMovieView.setContent(movieListView);

		Tab tabClientView = new Tab("Clients");
		tabClientView.setContent(clientTableView);

		Tab tabPlaylistView = new Tab("Playlists");
		tabPlaylistView.setContent(playlistView);


		tabPane.getTabs().add(tabMovieView);
		tabPane.getTabs().add(tabClientView);
		tabPane.getTabs().add(tabPlaylistView);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(tabPane);
		borderPane.setBottom(buttonPane);

		Scene scene = new Scene(borderPane, 800, 600);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	
	/**
	 * @return the activeClient
	 */
	public RPIClient getActiveClient() {
		return activeClient;
	}

	
	/**
	 * @param activeClient the activeClient to set
	 */
	public void setActiveClient(RPIClient activeClient) {
		this.activeClient = activeClient;
		
		for (ActiveClientListener listener : activeClientListeners) {
			listener.activeClientChanged(activeClient);
		}
	}
	
	/** Adds the given active client listener */
	
	public void addActiveClientListener(ActiveClientListener listener) {
		activeClientListeners.add(listener);
	}
	
	/** Removes the given active client listener */
	
	public void removeActiveClientListener(ActiveClientListener listener) {
		activeClientListeners.remove(listener);
	}
	
	
	
}
