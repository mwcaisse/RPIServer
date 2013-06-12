package com.ricex.rpi.server.player;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.ClientChangeEvent;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.RPIClient;


public class VideoListView extends BorderPane implements ClientChangeListener<RPIClient>, ActiveClientListener {

	//TODO: get the video list from the currently active client
	
	private static Image movieIconImage = new Image(VideoListView.class.getResourceAsStream("/data/icons/movie.png"));
	private static Image directoryIconImage = new Image(VideoListView.class.getResourceAsStream("/data/icons/directory.png"));
	
	/** The tree view for displaying the videos */
	private TreeView<Video> videoTree;
	
	/** The currently active client */
	private RPIClient activeClient;
	
	/** The current root video */
	private Video rootVideo;
	
	/** Creates a new video list view
	 * 
	 */
	
	public VideoListView(RPIPlayer player) {
		player.addActiveClientListener(this);
		videoTree = new TreeView<Video>();		
		//create the movie parser to use     

		setCenter(videoTree);
	}
	
	/** Updates the videos in the tree with the given rootVide
	 * 
	 * @param rootVideo
	 */
	
	public void updateVideos(Video rootVideo) {
		videoTree.setRoot(new TreeItem<Video>());
		//videoTree.getRoot().getChildren().clear();
		if (rootVideo == null) {
			return;
		}
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

	@Override
	public void activeClientChanged(RPIClient activeClient) {
		if (this.activeClient != null) {
			this.activeClient.removeChangeListener(this);
		}
		this.activeClient = activeClient;		
		if (activeClient != null) {
			updateVideos(activeClient.getRootDirectory());
			activeClient.addChangeListener(this);
		}
		else {
			updateVideos(null);
		}
		
	}

	@Override
	public void activeClientRemoved() {
		activeClient.removeChangeListener(this);
		activeClient = null;
		updateVideos(null);
	}

	@Override
	public void clientChanged(ClientChangeEvent<RPIClient> changeEvent) {
		if (changeEvent.getEventType() == ClientChangeEvent.EVENT_ROOT_DIRECTORY_CHANGE) {
			updateVideos(changeEvent.getSource().getRootDirectory());
		}
		
	}
	
	
}
