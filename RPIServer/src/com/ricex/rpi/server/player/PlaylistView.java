package com.ricex.rpi.server.player;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/** The view that will display a playlist and allow user to modify / play / create one 
 * 
 * @author Mitchell
 *
 */

public class PlaylistView extends BorderPane {

	
	/** The listview for displaying the selected playlist items */
	private ListView<String> listViewPlaylistItems;
	
	/** The list view for displaying the playlists */
	private ListView<String> listViewPlaylists;
	
	public PlaylistView() {
	
		listViewPlaylistItems = new ListView<String>();
		
		listViewPlaylists = new ListView<String>();
		
		setCenter(listViewPlaylistItems);
		setRight(listViewPlaylists);
		
	}
}
