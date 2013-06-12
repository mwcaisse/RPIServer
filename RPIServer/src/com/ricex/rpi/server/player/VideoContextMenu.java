package com.ricex.rpi.server.player;

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
	}	
	
	/** Populates the context menu with the given items
	 * 
	 */
	
	private void generateItems() {
		Menu playlistItem = new Menu("Add to playlist");
		for (Playlist playlist : activeClient.getPlaylistController().getAllPlaylists()) {
			MenuItem item = new MenuItem(playlist.getName());
			playlistItem.getItems().add(item);
		}
		getItems().add(playlistItem);
	}
	                              
	                              
}
