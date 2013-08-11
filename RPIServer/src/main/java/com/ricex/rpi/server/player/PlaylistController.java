package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricex.rpi.common.Playlist;

/** Maintains a list of playlists
 * 
 * @author Mitchell
 *
 */

public class PlaylistController {

	/** The list fo play lists */
	private Map<String, Playlist> playlists;

	/** Creates a new instance of playlist controller */
	public PlaylistController() {
		playlists = new HashMap<String, Playlist>();
	}

	/** Removes the given play list */

	public List<Playlist> getAllPlaylists() {
		return new ArrayList<Playlist>(playlists.values());
	}

	/** Adds the given playlist to the list
	 * If a playlist with the same name already exists, it will be replaced with the given playlist
	 * 
	 * @param playlist The playlist to add
	 */

	public void addPlaylist(Playlist playlist) {
		playlists.put(playlist.getName(), playlist);
	}

	/** Returns the playlist with the given name */

	public Playlist getPlaylist(String name) {
		return playlists.get(name);
	}

	/** Removes the playlist with the given name */

	public Playlist removePlaylist(String name) {
		return playlists.remove(name);
	}

	/** Removes the given playlist from the list */

	public Playlist removePlaylist(Playlist playlist) {
		return playlists.remove(playlist.getName());
	}


}
