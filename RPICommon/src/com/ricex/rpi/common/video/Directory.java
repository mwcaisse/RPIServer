package com.ricex.rpi.common.video;

import java.util.ArrayList;
import java.util.List;

public class Directory implements Video {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7152244055944820407L;

	/** Name of this directory */
	private final String name;
	
	/** List of children */
	private List<Video> children;
	
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
	
	public String toString() {
		return name;
	}
	
	public boolean isDirectory() {
		return true;
	}
	
	public String getName() {
		return name;
	}

}
