package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;

/** The view that will display a playlist and allow user to modify / play / create one 
 * 
 * @author Mitchell
 *
 */

public class PlaylistView extends BorderPane implements ActiveClientListener {

	/** The RPIPlayer */
	private RPIPlayer player;
	
	/** The listview for displaying the selected playlist items */
	private ListView<Video> listViewPlaylistItems;
	
	/** The list view for displaying the playlists */
	private ListView<Playlist> listViewPlaylists;
	
	/** Creates a new playlist view */
	
	public PlaylistView(RPIPlayer player) {
		this.player = player;		
		
		//initialize the list views
		listViewPlaylistItems = new ListView<Video>();		
		listViewPlaylists = new ListView<Playlist>();
		
		//add the selection change listener
		listViewPlaylists.getSelectionModel().selectedItemProperty().addListener(new PlaylistChangeListener());
		
		player.addActiveClientListener(this);
		activeClientChanged(player.getActiveClient());	
		
		//add the list views to the border pane
		setCenter(listViewPlaylistItems);
		setRight(listViewPlaylists);
		
	}
	
	/** Updates the playlists being displayed with the given list of playlists */
	
	private void updatePlaylistList(List<Playlist> playlists) {
		ObservableList<Playlist> data = FXCollections.observableArrayList(playlists);
		listViewPlaylists.setItems(data);
	}
	
	private class PlaylistChangeListener implements ChangeListener<Playlist> {
		
		private PlaylistChangeListener() {
			
		}

		@Override
		public void changed(ObservableValue<? extends Playlist> ob, Playlist oldVal, Playlist newVal) {
			ObservableList<Video> playlistItems = FXCollections.observableArrayList(newVal.getItems());
			listViewPlaylistItems.setItems(playlistItems);
		}
	}

	@Override
	public void activeClientChanged(RPIClient activeClient) {
		if (activeClient == null) {
			//if the active client is null, clear the listview items
			listViewPlaylists.setItems(FXCollections.observableArrayList(new ArrayList<Playlist>()));
			listViewPlaylistItems.setItems(FXCollections.observableArrayList(new ArrayList<Video>()));
		}
		else {
			listViewPlaylists.setItems(FXCollections.observableArrayList(activeClient.getPlaylistController().getAllPlaylists()));
		}
		
	}

	@Override
	public void activeClientRemoved() {
		// TODO Auto-generated method stub
		
	}
}
