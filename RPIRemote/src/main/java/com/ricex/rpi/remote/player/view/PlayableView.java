package com.ricex.rpi.remote.player.view;

import com.ricex.rpi.common.Playlist;

/** An interface for defining a view that has a playable playlist
 * 
 * @author Mitchell Caisse
 *
 */

public interface PlayableView {

	/** Returns a playlist to play when a user presses the play button
	 * 
	 * @return
	 */
	
	public Playlist getPlaylistToPlay();
}
