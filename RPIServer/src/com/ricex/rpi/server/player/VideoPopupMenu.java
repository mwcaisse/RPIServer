package com.ricex.rpi.server.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;

/** Popup menu created on a Video in the Tree view
 * 
 * @author Mitchell
 *
 */

public class VideoPopupMenu extends JPopupMenu implements ActionListener {

	/** The video this popup menu will control */
	private Video video;
	
	/** Popup menu item to add the given video to the selected playlist */
	private JMenu itemAddToPlaylist;
	
	/** Popup menu item to play the given video */
	private JMenuItem itemPlay;
	
	/** Creates a new popup menu for the given video
	 * 
	 * @param v
	 */
	
	public VideoPopupMenu(Video video) {
		this.video = video;
		
		itemPlay = new JMenuItem("Play");
		itemPlay.addActionListener(this);
		add(itemPlay);
		
		List<Playlist> playlists = RPIPlayer.getInstance().getActiveClient().getPlaylistController().getAllPlaylists();
		if (!playlists.isEmpty()) {
			itemAddToPlaylist = new JMenu("Add to playlist");		
			for (Playlist playlist : playlists) {
				itemAddToPlaylist.add(new PlaylistMenuItem(playlist, video));
			}
			
			add(itemAddToPlaylist);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Playlist playlist = new Playlist();
		playlist.addItem(video);
		RPIPlayer.getInstance().getActiveClient().getPlayerModule().play(playlist);
	}
	
	private class PlaylistMenuItem extends JMenuItem implements ActionListener {

		/** The playlist to add the video to */
		private Playlist playlist;
		
		/** The video to add to the play list */
		private Video video;
		
		/**Creates a new playlist menu item
		 * 
		 * @param playlist The playlist to draw / add to
		 * @param video The video to add to the playlist
		 */
		
		private PlaylistMenuItem(Playlist playlist, Video video) {
			super(playlist.getName());
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: somethign here is null.
			playlist.addItem(video);
		}
		
	}
}
