package com.ricex.rpi.server.player;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;


public class VideoListView extends BorderPane {

	/** The tree view for displaying the videos */
	private TreeView<Video> videoTree;
	
	private Video rootVideo;
	
	public VideoListView(Video rootVideo) {
		videoTree = new TreeView<Video>();	
		
		updateVideos(rootVideo);
		
		setCenter(videoTree);

	}
	
	/** Updates the videos in the tree with the given rootVide
	 * 
	 * @param rootVideo
	 */
	
	public void updateVideos(Video rootVideo) {
		this.rootVideo = rootVideo;
		
		//lets just do one level atm.
		TreeItem<Video> rootNode = new TreeItem<Video>(rootVideo, rootVideo.getIcon());
		
		for (Video video : rootVideo.getChildren()) {
			rootNode.getChildren().add(new TreeItem<Video>(video, video.getIcon()));
		}
		
		videoTree.setRoot(rootNode);
	}
	
	/** Returns the selected video item  */
	
	public Video getSelectedItem() {		
		return videoTree.getSelectionModel().getSelectedItem().getValue();
	}
	
	
}
