package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.Video;

/** The view that will display a playlist and allow user to modify / play / create one 
 * 
 * @author Mitchell
 *
 */

public class PlaylistView extends BorderPane {

	
	/** The listview for displaying the selected playlist items */
	private ListView<Video> listViewPlaylistItems;
	
	/** The list view for displaying the playlists */
	private ListView<Playlist> listViewPlaylists;
	
	private List<Playlist> playlists;
	
	public PlaylistView() {
	
		playlists = new ArrayList<Playlist>();
		
		for (int i=0;i<10;i++) {
			Playlist playlist = new Playlist("Playlist " + (i+1));
			for (int j=0;j<(i+1)*5;j++) {
				playlist.addItem(new Movie("Playlist " + (i+1) + " Movie " + (j+1)));
			}
			playlists.add(playlist);
		}
		
		
		listViewPlaylistItems = new ListView<Video>();		
		listViewPlaylists = new ListView<Playlist>();
		
		listViewPlaylists.getSelectionModel().selectedItemProperty().addListener(new PlaylistChangeListener());
		
		ObservableList<Playlist> data = FXCollections.observableArrayList(playlists);
		
		listViewPlaylists.setItems(data);

		
		setCenter(listViewPlaylistItems);
		setRight(listViewPlaylists);
		
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
}
