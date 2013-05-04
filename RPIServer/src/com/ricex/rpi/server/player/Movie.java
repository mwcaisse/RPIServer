package com.ricex.rpi.server.player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

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
	
	/** The icon for this movie in the tree view */
	private Node icon;
	
	/** Creates a new movie with the given file path */
	
	public Movie(String filePath) {
		this(filePath, filePath);
	}
	
	/** Creates a new movie with the given name and file path */
	
	public Movie(String name, String filePath) {
		this.name = name;
		ImageIcon o = (ImageIcon)FileSystemView.getFileSystemView().getSystemIcon(new File(" "));
				
		
	}

	/** Returns the file of this movie */
	
	public String getVideoFile() {
		return filePath;
	}

	/** Returns the list of sub children in this video node, in this case there are none */
	
	public List<Video> getChildren() {
		return new ArrayList<Video>();
	}
	
	/** Returns the icon to use when displaying this in the tree */
	public Node getIcon() {
		return new ImageView();
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isDirectory() {
		return false;
	}
}
