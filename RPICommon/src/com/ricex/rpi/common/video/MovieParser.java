package com.ricex.rpi.common.video;

/** Interface for parsing movies
 * 
 * @author Mitchell Caisse
 *
 */

public interface MovieParser {

	/** Parses all of the videos in the specified directory,
	 *  
	 * @param baseFolder The folder to parse for videos
	 * @return A video representing all of the videos in the base folder
	 */
	
	public Video parseVideos(String baseFolder);
}
