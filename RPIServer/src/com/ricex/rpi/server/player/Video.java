package com.ricex.rpi.server.player;

import java.util.List;

import javafx.scene.Node;

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
	
	/** Returns the icon to use when displaying this in the tree */
	public Node getIcon();
	
	/** Returns whether or not this Video is a directory */
	public boolean isDirectory();
	
}
