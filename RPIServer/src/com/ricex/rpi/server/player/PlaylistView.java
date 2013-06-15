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
	
	/** The control view for the play lists */
	private PlaylistControlView playlistControlView;
	
	/** Creates a new playlist view */
	
	public PlaylistView(RPIPlayer player) {
		this.player = player;		
		
		//initialize the list views
		listViewPlaylistItems = new ListView<Video>();		
		listViewPlaylists = new ListView<Playlist>();
		playlistControlView = new PlaylistControlView(this);
		
		//add the selection change listener
		listViewPlaylists.getSelectionModel().selectedItemProperty().addListener(new PlaylistChangeListener());
		
		player.addActiveClientListener(this);
		activeClientChanged(player.getActiveClient());	
		
		//add the list views to the border pane
		setCenter(listViewPlaylistItems);
		setRight(listViewPlaylists);
		setBottom(playlistControlView);
		
	}
	
	/** Creates a playlist with the given name
	 * 
	 * @param name The name of the playlist to create
	 * @return True if the creation was sucessful, false otherwise
	 */
	
	public boolean createPlaylist(String name) {
		if (player.getActiveClient() == null) {
			return false;
		}
		System.out.println("We are adding a playlist ");
		Playlist toAdd = new Playlist(name);
		player.getActiveClient().getPlaylistController().addPlaylist(toAdd);
		refresh();
		return true;
	}
	
	/** Deletes the currently selected playlist
	 * 
	 */
	
	public void deleteSelectedPlaylist() {
		if (player.getActiveClient() == null) {
			return;
		}
		Playlist toDelete = listViewPlaylists.getSelectionModel().getSelectedItem();
		player.getActiveClient().getPlaylistController().removePlaylist(toDelete);
	}
	/** Updates the playlists being displayed with the given list of playlists */
	
	private void updatePlaylistList(List<Playlist> playlists) {
		System.out.println("we are updating the items in the play list view");
		ObservableList<Playlist> data = FXCollections.observableArrayList(playlists);
		listViewPlaylists.setItems(data);
	}	
	
	/** Clears the items from the play list views */
	
	private void clearPlaylistViews() {
		
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
	
	/** Refreshes the list views of the playlists
	 * 
	 */
	
	public void refresh() {
		System.out.println("We are refreshing");
		if (player.getActiveClient() == null) {
			// no active client, clean the playlist views
			clearPlaylistViews();
		}
		else {
			//we have an active client update the list view.
			updatePlaylistList(player.getActiveClient().getPlaylistController().getAllPlaylists());
		}
	}

	@Override
	public void activeClientRemoved() {

		
	}
	
	/** Listens for changes in the list of palylists on the right 
	 * 
	 * @author Mitchell
	 *
	 */
	
	private class PlaylistChangeListener implements ChangeListener<Playlist> {
		
		private PlaylistChangeListener() {
			
		}

		@Override
		public void changed(ObservableValue<? extends Playlist> ob, Playlist oldVal, Playlist newVal) {
			//if there actual is a new val
			if (newVal != null) {	
				System.out.println("Changed!?" + newVal);
				ObservableList<Video> playlistItems = FXCollections.observableArrayList(newVal.getItems());
				listViewPlaylistItems.setItems(playlistItems);
				System.out.println("Size?: " + newVal.getItems().size());
			}
			else {
				//TODO: Implement this
			}
		}
	}

}
