package com.ricex.rpi.common.video;

import java.io.Serializable;
import java.util.List;

/** Interface representing a video, to be displayed
 * 
 * @author Mitchell
 *
 */

public interface Video extends Serializable {

	/** Returns the path to the video file */
	public String getVideoFile(); 
	
	/** Returns a list of children in this sub interface */
	
	public List<Video> getChildren();
	
	/** Returns whether or not this Video is a directory */
	public boolean isDirectory();
	
}
