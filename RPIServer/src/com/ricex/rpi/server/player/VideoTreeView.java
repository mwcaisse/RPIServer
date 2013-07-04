package com.ricex.rpi.server.player;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.client.ClientChangeEvent;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** View that will display a tree of the videos for the active clietn
 * 
 * @author Mitchell
 *
 */

public class VideoTreeView extends JPanel implements ClientConnectionListener<RPIClient>, ClientChangeListener<RPIClient> {

	/** The tree containing the list of videos */
	private JTree videoTree;
	
	/** The tree model for the tree view */
	private DefaultTreeModel treeModel;
	
	/** Creates a new instance of VideoTreeView
	 * 
	 */
	
	public VideoTreeView() {
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Videos"));		
		videoTree = new JTree(treeModel);
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		add(videoTree, BorderLayout.CENTER);
		
		RPIServer.getInstance().addConnectionListener(this);
	}
	
	/** Creates the tree view from the given rootDirectory
	 * 
	 * @param rootDirectory
	 */
	
	private void updateTree(Video rootDirectory) {
		treeModel.setRoot(processDirectory(rootDirectory));		
	}
	
	/** Processes a directory tree node 
	 * 
	 * @param directory
	 * @return
	 */
	
	private DefaultMutableTreeNode processDirectory(Video directory) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(directory);
		for (Video child : directory.getChildren()) {
			if (child.isDirectory()) {
				node.add(processDirectory(child));
			}
			else {
				node.add(processVideo(child));
			}
		}
		return node;
	}
	
	/** Processes a video tree node 
	 * 
	 * @param video
	 * @return
	 */
	
	private DefaultMutableTreeNode processVideo(Video video) {
		return new DefaultMutableTreeNode(video);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void clientConnected(RPIClient client) {
		client.addChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void clientDisconnected(RPIClient client) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void clientChanged(ClientChangeEvent<RPIClient> changeEvent) {
		if (changeEvent.getEventType() == ClientChangeEvent.EVENT_ROOT_DIRECTORY_CHANGE) {
			//update the tree with the new directory
			updateTree(changeEvent.getSource().getRootDirectory());
		}		
	}
	
}
