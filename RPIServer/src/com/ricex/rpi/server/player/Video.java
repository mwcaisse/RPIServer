package com.ricex.rpi.server.player;

import java.util.List;

/** Interface representing a video, to be displayed
 * 
 * @author Mitchell
 *
 */

public interface Video {

	/** Returns the path to the video file */
	public String getVideoFile(); 
	
	/** Returns a list of children in this sub interface */
	
	public List<Video> getChildren();
	
}
