package com.ricex.rpi.server.player;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.Server;
import com.ricex.rpi.server.ServerPlayerModule;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** Will probally need to change this in future, but temporary for now
 * 
 *  Contains the buttons on the side for interacting with the movie list
 *  
 * @author Mitchell
 *
 */

public class ButtonPane extends HBox implements EventHandler<ActionEvent>, ClientConnectionListener<RPIClient>, ClientChangeListener<RPIClient>,
			ChangeListener<RPIClient> {
	
	/** Button to start playing the selected movie in the list view */
	private Button butPlay;	
	
	/** Button to pause / resume the playing movie */
	private Button butPause;
	
	/** Button to stop playing the current movie */
	private Button butStop;
	
	/** Button for seeking to the left 30 seconds */
	private Button butSeekLeft;
	
	/** Button for seeking to the left 600 seconds */
	private Button butSeekLeftFast;
	
	/** Button for seeking to the right 30 seconds */
	private Button butSeekRight;
	
	/** Button for seeking to the right 600 secconds */
	private Button butSeekRightFast;
	
	/** Button to advance to the next chapter */
	private Button butNextChapter;
	
	/** Button to go back to the last chapter */
	private Button butLastChapter;
	
	/** The combobox to select which client to currently control */
	private ComboBox<RPIClient>  cboxClients;
	
	/** List of currently connected clients */
	private ObservableList<RPIClient> connectedClients;
	
	/** The player module interface to complete the given actions */
	private ServerPlayerModule playerModule;
	
	/** The list view representing the movies */
	private VideoListView movieView;
	
	/** The RPI server that is running */
	private Server<RPIClient> server;
	
	/** The width of the buttons */
	private final int BUTTON_WIDTH = 55;
	
	/** Creates a new ButtonPane for controlling the movies */
	
	public ButtonPane(Server<RPIClient> server, ServerPlayerModule playerModule, VideoListView movieView) {
		this.server = server;
		this.playerModule = playerModule;		
		this.movieView = movieView;
		
		server.addConnectionListener(this);
		
		//create the buttons
		butPlay = new Button("Play");
		butPause = new Button("Pause");
		butStop = new Button("Stop");
		butSeekLeft = new Button("<");
		butSeekLeftFast = new Button("<<");
		butSeekRight = new Button(">");
		butSeekRightFast = new Button(">>");
		butNextChapter = new Button(">>|");
		butLastChapter = new Button("|<<");		
		
		
		//set the width	
		butPlay.setPrefWidth(BUTTON_WIDTH);
		butPause.setPrefWidth(BUTTON_WIDTH);
		butStop.setPrefWidth(BUTTON_WIDTH);
		butSeekLeft.setPrefWidth(BUTTON_WIDTH);
		butSeekLeftFast.setPrefWidth(BUTTON_WIDTH);
		butSeekRight.setPrefWidth(BUTTON_WIDTH);
		butSeekRightFast.setPrefWidth(BUTTON_WIDTH);
		butNextChapter.setPrefWidth(BUTTON_WIDTH);
		butLastChapter.setPrefWidth(BUTTON_WIDTH);
				

		
		//add the listeners
		butPlay.setOnAction(this);
		butPause.setOnAction(this);
		butStop.setOnAction(this);
		butSeekLeft.setOnAction(this);
		butSeekLeftFast.setOnAction(this);
		butSeekRight.setOnAction(this);
		butSeekRightFast.setOnAction(this);
		butNextChapter.setOnAction(this);
		butLastChapter.setOnAction(this);
		
		setPrefSize(100, 30);
		//setPadding(new Insets(15, 12, 15, 12));
		setSpacing(5); // spacing between the children
		setAlignment(Pos.CENTER);
		
		
		//set up the client combo box
		cboxClients = new ComboBox<RPIClient>();
		connectedClients = FXCollections.observableArrayList(RPIServer.getInstance().getConnectedClients());
		cboxClients.setItems(connectedClients);
		
		cboxClients.valueProperty().addListener(this);
		
		
		//add all of the elements
		getChildren().add(butPlay);
		getChildren().add(butPause);
		getChildren().add(butStop);
		getChildren().add(butSeekLeft);
		getChildren().add(butSeekLeftFast);
		getChildren().add(butLastChapter);
		getChildren().add(butSeekRight);
		getChildren().add(butSeekRightFast);	
		getChildren().add(butNextChapter);
		
		//add the children combo box
		getChildren().add(cboxClients);
	
		
	}

	public void handle(ActionEvent e) {
		Object source = e.getSource();		
		
		if (source.equals(butPlay)) {
			String movieFile = movieView.getSelectedItem().getVideoFile();
			playerModule.play(movieFile);
		}
		else if (source.equals(butPause)) {
			playerModule.pause();
		}
		else if (source.equals(butStop)) {
			playerModule.stop();
		}
		else if (source.equals(butSeekLeft)) {
			playerModule.seekBackwardSlow();
		}
		else if (source.equals(butSeekLeftFast)) {
			playerModule.seekBackwardFast();
		}
		else if (source.equals(butSeekRight)) {
			playerModule.seekForwardSlow();
		}
		else if (source.equals(butSeekRightFast)) {
			playerModule.seekForwardFast();
		}
		else if (source.equals(butNextChapter)) {
			playerModule.nextChapter();
		}
		else if (source.equals(butLastChapter)) {
			playerModule.previousChapter();
		}
		
	}

	@Override
	public void clientConnected(final RPIClient client) {
		Platform.runLater(new Runnable() {
			public void run() {
				connectedClients.add(client);
				if (connectedClients.size() == 1) {
					//only one element in the list , automatically select it.		
					//cboxClients.setValue(client);
					//cboxClients.getSelectionModel().select(0);
				}
			}
		});
		
	}

	@Override
	public void clientDisconnected(final RPIClient client) {
		Platform.runLater(new Runnable() {
			public void run() {
				connectedClients.remove(client);	
				if (connectedClients.isEmpty()) {
					//there are no more connected clients, remove the active client
					playerModule.removeActiveClient();
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clientChanged(RPIClient client) {
		Platform.runLater(new Runnable() {
			public void run() {
				cboxClients.setVisible(false);
				cboxClients.setVisible(true);
			}
		});
				
	}

	@Override
	public void changed(ObservableValue<? extends RPIClient> ov, RPIClient oldVal, RPIClient newVal) {
		System.out.println("ButtonPane CBOX Selected value changed to: " + newVal);
		if (newVal == null) {
			playerModule.removeActiveClient();
		}
		else {
			playerModule.setActiveClient(newVal); // set the playerModule's active client to the selected value
		}
	}
}
