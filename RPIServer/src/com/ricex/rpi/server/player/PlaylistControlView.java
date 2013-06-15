package com.ricex.rpi.server.player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/** The playlist control view, allows the user to create playlists, and delete playlists
 * 
 * @author Mitchell
 *
 */

public class PlaylistControlView extends HBox implements EventHandler<ActionEvent>{

	/** Button to create a play list */
	private Button butCreatePlaylist;
	
	/** Button to delete a play list */
	private Button butDeletePlaylist;
	
	/** Refreshes the list views */
	private Button butRefresh;
	
	/** TextField for the name of the play list */
	private TextField txtPlaylistName;
	
	/** The label for the create playlist text field */
	private Label lblPlaylistName;
	
	/** The playlist view */
	private PlaylistView playlistView;
	
	/** Creates a new playlist control view */
	
	public PlaylistControlView(PlaylistView playlistView) {
		this.playlistView = playlistView;
		
		butCreatePlaylist = new Button("Create");
		butDeletePlaylist = new Button("Delete");
		butRefresh = new Button("Refresh");
		
		//set the on action event handlers
		butCreatePlaylist.setOnAction(this);
		butDeletePlaylist.setOnAction(this);
		butRefresh.setOnAction(this);
		
		txtPlaylistName = new TextField();
		
		lblPlaylistName = new Label("Playlist Name: ");
		
		setSpacing(5);
		
		getChildren().add(lblPlaylistName);
		getChildren().add(txtPlaylistName);
		getChildren().add(butCreatePlaylist);
		getChildren().add(butDeletePlaylist);
		getChildren().add(butRefresh);
	}

	@Override
	public void handle(ActionEvent e) {
		
		//check which button the user pressed
		if (e.getSource().equals(butCreatePlaylist)) {
			String playlistName = txtPlaylistName.getText();
			if (playlistView.createPlaylist(playlistName)) {
				// if we sucessfully added the playlist, clear the text field
				txtPlaylistName.clear();
			}
		}
		else if (e.getSource().equals(butRefresh)) {
			playlistView.refresh();
		}
		else {
			playlistView.deleteSelectedPlaylist();
		}
		
	}
	
}
