package com.ricex.rpi.server.player;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;

/** View that will display a tree of the videos for the active clietn
 * 
 * @author Mitchell
 *
 */

public class VideoTreeView extends JPanel implements ActiveClientListener {

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
		
		// if an active client exists, update the tree view with its videos
		if (RPIPlayer.getInstance().activeClientExists()) {
			updateTree(RPIPlayer.getInstance().getActiveClient().getRootDirectory());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		//scrollPane.getViewport().setLayout(new BorderLayout());
		scrollPane.getViewport().add(videoTree);
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		
		RPIPlayer.getInstance().addActiveClientListener(this);
	}
	
	/** Creates the tree view from the given rootDirectory
	 * 
	 * @param rootDirectory
	 */
	
	private void updateTree(Video rootDirectory) {
		treeModel.setRoot(processDirectory(rootDirectory));		
	}
	
	/** Clears the tree view by removing all of its nodes
	 * 
	 */
	
	private void clearTree() {
		treeModel.setRoot(new DefaultMutableTreeNode("Videos"));
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

	/** Updates the tree view when the active client is chagned
	 */
	
	@Override
	public void activeClientChanged(RPIClient activeClient) {
		updateTree(activeClient.getRootDirectory());
		
	}
	
	/** Clears teh tree view when the active client is removed
	 */

	@Override
	public void activeClientRemoved() {
		clearTree();
	}	
}
