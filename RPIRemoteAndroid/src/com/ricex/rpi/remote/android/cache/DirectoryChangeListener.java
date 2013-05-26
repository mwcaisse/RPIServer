package com.ricex.rpi.remote.android.cache;

import com.ricex.rpi.common.video.Video;

/** Listens for changes in the root directory.
 * 
 *  Used to notify when a new root directory is received from the server
 * @author Mitchell
 *
 */

public interface DirectoryChangeListener {

	/** Called when the client received a new root directory from the server
	 * 
	 * @param rootDirectory The updated root directory
	 */
	
	public void rootDirectoryChanged(Video rootDirectory);
	
}
