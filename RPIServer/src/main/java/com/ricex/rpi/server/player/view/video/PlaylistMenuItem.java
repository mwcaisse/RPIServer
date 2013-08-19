package com.ricex.rpi.server.player.view.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;

/** Menu item representing a playlist
 * 
 * @author Mitchell Caisse
 *
 */

public class PlaylistMenuItem extends JMenuItem implements ActionListener {

	/** The playlist to add the video to */
	private Playlist playlist;
	
	/** The list of videos to add to the play list */
	private List<Video> videos;
	
	/**Creates a new playlist menu item
	 * 
	 * @param playlist The playlist to add the videos to
	 * @param videos The list of videos to add to the play list
	 */
	
	public PlaylistMenuItem(Playlist playlist, List<Video> videos) {
		super(playlist.getName());
		this.playlist = playlist;
		this.videos = videos;
		
		addActionListener(this);
	}
	
	/** Creates a new playlist menu item, with just one video
	 * 
	 * @param playlist The playlist to add the video to
	 * @param video The video to add
	 */
	
	public PlaylistMenuItem(Playlist playlist, Video video) {
		super(playlist.getName());
		this.playlist = playlist;
		this.videos = new ArrayList<Video>();
		videos.add(video);
		
		addActionListener(this);
	}
	
	/** Adds each of the videos in the list to the play list
	 * 
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//add each video to the playlist
		for (Video video : videos) {
			playlist.addItem(video);
		}
	}
	
}
