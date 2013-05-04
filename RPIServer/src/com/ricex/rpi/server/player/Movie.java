package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

/** Class representing a movie, its file and information about it
 * 
 * @author Mitchell
 *
 */

public class Movie implements Video {
	
	/** The name of this movie  */
	private String name;
	
	/** The path to this video file */
	private String filePath;	
	
	/** Creates a new movie with the given file path */
	
	public Movie(String filePath) {
		this(filePath, filePath);
	}
	
	/** Creates a new movie with the given name and file path */
	
	public Movie(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	/** Returns the file of this movie */
	
	public String getVideoFile() {
		return "Movies\\Airplane.avi";
	}

	/** Returns the list of sub children in this video node, in this case there are none */
	
	public List<Video> getChildren() {
		return new ArrayList<Video>();
	}
	
	public String toString() {
		return name;
	}
}
