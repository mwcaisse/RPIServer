package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Directory implements Video {

	/** Name of this directory */
	private String name;
	
	/** List of children */
	private List<Video> children;
	
	/** The icon this directory will use */
	private ImageView icon;	
	
	private static Image iconImage = new Image(Directory.class.getResourceAsStream("/data/icons/directory.png"));
	
	/** Creates a new directory with the given name 
	 * 
	 * @param name
	 */
	public Directory(String name) {
		this(name, new ArrayList<Video>());
	}
	
	/** Creates a new directory with the given name and list of children
	 * 
	 * @param name
	 * @param children
	 */
	public Directory(String name, List<Video> children) {
		this.name = name;
		this.children = children;
		icon = new ImageView(iconImage);

	}

	public String getVideoFile() {
		return ""; // this has no video file nae
	}

	public List<Video> getChildren() {		
		return children;
	}
	
	public void addChild(Video video) {
		children.add(video);
	}
	
	/** Returns the icon to use when displaying this in the tree */
	public Node getIcon() {
		return icon;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isDirectory() {
		return true;
	}

}
