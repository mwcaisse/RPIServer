package com.ricex.rpi.server.player;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;


public class VideoListView extends BorderPane {

	/** The tree view for displaying the videos */
	private TreeView<String> videoTree;
	
	
	
	public VideoListView() {		

		videoTree = new TreeView<String>();
		
		TreeItem<String> rootItem = new TreeItem<String>("Root");
		
		rootItem.getChildren().add(new TreeItem<String>(new Movie("AA").toString()));
		
		videoTree.setRoot(rootItem);
		
		setCenter(videoTree);

	}
	
	
}
