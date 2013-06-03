package com.ricex.rpi.server.player;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.RPIServerProperties;


public class VideoListView extends BorderPane {

	private static Image movieIconImage = new Image(VideoListView.class.getResourceAsStream("/data/icons/movie.png"));
	private static Image directoryIconImage = new Image(VideoListView.class.getResourceAsStream("/data/icons/directory.png"));
	
	/** The tree view for displaying the videos */
	private TreeView<Video> videoTree;
	
	/** The current root video */
	private Video rootVideo;
	
	/** Creates a new video list view
	 * 
	 */
	
	public VideoListView() {
		videoTree = new TreeView<Video>();		
		//create the movie parser to use     

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
		TreeItem<Video> dirNode = new TreeItem<Video>(v, new ImageView(directoryIconImage));		
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
		return new TreeItem<Video>(v, new ImageView(movieIconImage));
	}
	
	
}
