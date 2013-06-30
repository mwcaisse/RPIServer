package com.ricex.rpi.server.player;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;


public class VideoContextMenu extends ContextMenu {

	/** The video that this context menu was opened on */
	private final Video video;
	
	/** The currently active client */
	private RPIClient activeClient;
	
	/** Creates a new context menu with the given video and active client
	 * 
	 * @param video
	 * @param activeClient
	 */
	
	public VideoContextMenu(Video video, RPIClient activeClient) {
		this.video = video;
		this.activeClient = activeClient;
		generateItems();
		setAutoHide(true);
	}	
	
	/** Populates the context menu with the given items
	 * 
	 */
	
	private void generateItems() {
		MenuItem playItem = new MenuItem("Play: " + video.getName());
		
		playItem.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				//create a playlist for the item, and then play the item
				Playlist playlist = new Playlist();
				playlist.addItem(video);
				activeClient.getPlayerModule().play(playlist);				
			}
			
		});
		getItems().add(playItem);
		
		//get the list of playlists for the active client
		List<Playlist> playlists = activeClient.getPlaylistController().getAllPlaylists();
		
		//check to make sure there are actualy some playlists
		if (playlists.size() > 0) { 
			Menu playlistItem = new Menu("Add to playlist");
			//create an add to for each playlist
			for (Playlist playlist : activeClient.getPlaylistController().getAllPlaylists()) {
				PlaylistMenuItem item = new PlaylistMenuItem(playlist);
				playlistItem.getItems().add(item);
			}
			//add the listener, and add it to the menu	
			getItems().add(playlistItem);
		}		
	}  

	/** Menu Item for a playlist
	 * 
	 * @author Mitchell
	 *
	 */
	
	private class PlaylistMenuItem extends MenuItem implements EventHandler<ActionEvent> {
		
		/** The playlist to add the video to */
		private Playlist playlist;
	
		/** Creates a new menu item, and sets the name of it to the Playlists name
		 * 
		 * @param playlist
		 */
		
		private PlaylistMenuItem(Playlist playlist) {
			super(playlist.getName());
			this.playlist = playlist;
			setOnAction(this);
		}

		/** Adds the video to this playlist */
		public void handle(ActionEvent e) {
			playlist.addItem(video);			
		}
	}
}
