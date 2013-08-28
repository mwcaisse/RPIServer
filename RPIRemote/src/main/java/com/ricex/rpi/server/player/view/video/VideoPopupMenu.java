package com.ricex.rpi.server.player.view.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.player.RPIPlayer;

/** Popup menu created on a Video in the Tree view
 * 
 * @author Mitchell Caisse
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
		
		//add the play item if active client exists
		if (RPIPlayer.getInstance().activeClientExists()) {
			add(itemPlay);
		}		

		//add the play list menu items
		List<Playlist> playlists = RPIPlayer.getInstance().getPlaylistController().getAllPlaylists();		
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
}
