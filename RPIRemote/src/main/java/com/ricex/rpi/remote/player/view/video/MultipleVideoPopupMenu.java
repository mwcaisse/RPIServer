package com.ricex.rpi.remote.player.view.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.player.RPIRemote;

/**
 * Popup menu create in VideoTreeView when multiple videos are selected
 * 
 * @author Mitchell Caisse
 * 
 */
public class MultipleVideoPopupMenu extends JPopupMenu implements ActionListener {

	/** The list of videos that are selected */
	private List<Video> videos;

	/** Menu item to add the items to play list */
	private JMenu addItemsToPlaylist;

	/** Menu item to play the videos */
	private JMenuItem playItems;

	/**
	 * Creates a new PopupMenu when the given videos are selected
	 * 
	 * @param videos
	 *            The selected videos
	 */

	public MultipleVideoPopupMenu(List<Video> videos) {
		this.videos = videos;
		initializePlayItem();
		initializePlaylistItem();
	}
	
	/** Initializes the menu item to play all of the selected movies
	 * 
	 */
	
	protected void initializePlayItem() {
		playItems = new JMenuItem("Play videos");
		playItems.addActionListener(this);
		if (RPIRemote.getInstance().activePlayerExists()) {
			add(playItems);
		}
	}	

	/** Initializes the add item to playlist
	 * 
	 */
	
	protected void initializePlaylistItem() {
		List<Playlist> playlists = RPIRemote.getInstance().getPlaylistController().getAllPlaylists();
		// if playlists exist
		if (!playlists.isEmpty()) {
			addItemsToPlaylist = new JMenu("Add to playlist");
			for (Playlist playlist : playlists) {
				addItemsToPlaylist.add(new PlaylistMenuItem(playlist, videos));
			}
			add(addItemsToPlaylist);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Playlist playlist = new Playlist();
		playlist.addAll(videos);
	}
}
