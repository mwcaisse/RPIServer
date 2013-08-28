package com.ricex.rpi.remote.player.view.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.player.RPIPlayer;


public class DirectoryPopupMenu extends JPopupMenu implements ActionListener {

	/** The video this popup menu will control */
	private Video video;
	
	/** Popup menu item to add the given video to the selected playlist */
	private JMenu itemAddToPlaylist;
	
	/** Creates a new popup menu for the given video
	 * 
	 * @param v
	 */
	
	public DirectoryPopupMenu(Video video) {
		this.video = video;	
		
		// only do anything if this video is in deed a directory
		if (video.isDirectory()) {
			List<Video> subVideos = getSubVideos(video);
			//add the play list menu items
			List<Playlist> playlists = RPIPlayer.getInstance().getPlaylistController().getAllPlaylists();
			//if there are playlists and subVideos, add the menu items
			if (!playlists.isEmpty() && !subVideos.isEmpty()) {
				itemAddToPlaylist = new JMenu("Add all to playlist");		
				for (Playlist playlist : playlists) {
					itemAddToPlaylist.add(new PlaylistMenuItem(playlist, subVideos));
				}
				
				add(itemAddToPlaylist);
			}
		}
	}
	
	/** Parses all of the sub videos of the directory, not including sub directories
	 *  into a list
	 * @param directory The directory to parse
	 * @return The videos
	 */
	
	protected List<Video> getSubVideos(Video directory) {
		List<Video> videos = new ArrayList<Video>();		
		for (Video video : directory.getChildren()) {
			if (!video.isDirectory()) {
				// if the video is not a directory add it to the movies list
				videos.add(video);
			}
		}
		
		return videos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Playlist playlist = new Playlist();
		playlist.addItem(video);
		//TODO:
		//RPIPlayer.getInstance().getActiveClient().getPlayerModule().play(playlist);
	}
}