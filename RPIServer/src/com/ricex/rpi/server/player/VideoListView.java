package com.ricex.rpi.server.player;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;


public class VideoListView extends BorderPane {

	/** The tree view for displaying the videos */
	private TreeView<Video> videoTree;
	
	/** The current root video */
	private Video rootVideo;
	
	/** The Parser used to read movies from the base directory */
	private MovieParser movieParser;
	
	public VideoListView(Video rootVideo) {
		videoTree = new TreeView<Video>();		
		//create the movie parser to use      
		movieParser = new MovieParser(RPIPlayer.baseDirectory);	
		updateVideos(); // update the videos using the parser
		setCenter(videoTree);

	}
	
	/** Updates the videos in the tree with the given rootVide
	 * 
	 * @param rootVideo
	 */
	
	public void updateVideos(Video rootVideo) {
		this.rootVideo = rootVideo;
		parseVideos();
	}
	
	/** Updates the videos using its built in movie parser 
	 * 
	 */
	
	public void updateVideos() {
		updateVideos(movieParser.parseVideos());
	}
	
	/** Returns the selected video item  */
	
	public Video getSelectedItem() {		
		return videoTree.getSelectionModel().getSelectedItem().getValue();
	}
	
	/** Parses the videos from the root videos, and sets the tree accordinly

	 */
	
	private void parseVideos() {
		TreeItem<Video> rootNode;
		if (rootVideo.isDirectory()) {
			rootNode = parseDirectory(rootVideo);
		}
		else {
			rootNode = parseMovie(rootVideo);
		}
		
		rootNode.setExpanded(true);
		videoTree.setRoot(rootNode);
		
	}
	
	/** Parses the given directory 
	 * 
	 * @param v The video directory to parse
	 * @return a TreeItem representing this directory
	 */
	
	private TreeItem<Video> parseDirectory(Video v) {
		TreeItem<Video> dirNode = new TreeItem<Video>(v, v.getIcon());		
		for (Video child: v.getChildren()) {
			if (child.isDirectory()) {
				dirNode.getChildren().add(parseDirectory(child));
			}
			else {
				dirNode.getChildren().add(parseMovie(child));
			}
		}
		
		return dirNode;
	}
	
	/** Parses the given video file 
	 * 
	 * @param v The video file to parse
	 * @return a TreeItem representing this movie
	 */
	
	private TreeItem<Video> parseMovie(Video v) {
		return new TreeItem<Video>(v, v.getIcon());
	}
	
	
}
