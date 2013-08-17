package com.ricex.rpi.server.player.view.video;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.player.RPIPlayer;
import com.ricex.rpi.server.player.view.PlayableView;

/** View that will display a tree of the videos for the active clietn
 * 
 * @author Mitchell Caisse
 * 
 * TODO: Add right clicking to directory, to add all sub movies to a playlist
 * 	--POSSIBLY DONE, NOT TEST
 * 
 * TODO: Enable adding multiple videos to the playlist
 *
 */

public class VideoTreeView extends JPanel implements PlayableView {

	/** The tree containing the list of videos */
	protected JTree videoTree;

	/** The tree model for the tree view */
	protected DefaultTreeModel treeModel;

	/** The currently active client */
	protected RPIClient activeClient;

	/** Creates a new instance of VideoTreeView
	 * 
	 */

	public VideoTreeView() {
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Videos"));
		videoTree = new JTree(treeModel);

		// if an active client exists, update the tree view with its videos

		updateTree(RPIPlayer.getInstance().getRootDirectory());

		JScrollPane scrollPane = new JScrollPane();

		//scrollPane.getViewport().setLayout(new BorderLayout());
		scrollPane.getViewport().add(videoTree);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		videoTree.addMouseListener(new TreeViewMouseListener());
	}

	/** Creates the tree view from the given rootDirectory
	 * 
	 * @param rootDirectory
	 */

	protected void updateTree(Video rootDirectory) {
		if (rootDirectory == null) {
			treeModel.setRoot(new DefaultMutableTreeNode("Videos"));
		}
		else {
			treeModel.setRoot(processDirectory(rootDirectory));
		}
	}

	/** Clears the tree view by removing all of its nodes
	 * 
	 */

	protected void clearTree() {
		treeModel.setRoot(new DefaultMutableTreeNode("Videos"));
	}

	/** Processes a directory tree node
	 * 
	 * @param directory
	 * @return
	 */

	protected DefaultMutableTreeNode processDirectory(Video directory) {
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

	protected DefaultMutableTreeNode processVideo(Video video) {
		return new DefaultMutableTreeNode(video);
	}

	protected class TreeViewMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				TreePath selectedPath = videoTree.getPathForLocation(e.getX(), e.getY());
				videoTree.setSelectionPath(selectedPath); // set the selected path of the tree, so right click vissualy selects
				if (selectedPath != null && videoTree.getSelectionCount() == 1) { //only do this is selection count is 1
					Video selectedItem = (Video)((DefaultMutableTreeNode)selectedPath.getLastPathComponent()).getUserObject();
					if (!selectedItem.isDirectory()) {
						VideoPopupMenu menu = new VideoPopupMenu(selectedItem);
						menu.show(videoTree, e.getX(), e.getY());
					}
				}
			}
		}
	}

	/** Return the playlist to play
	 * 
	 */

	public Playlist getPlaylistToPlay() {
		Playlist playlist = new Playlist();
		if (videoTree.getSelectionPath() != null) {
			Video selectedVideo = (Video) ((DefaultMutableTreeNode)videoTree.getSelectionPath().getLastPathComponent()).getUserObject();
			playlist.addItem(selectedVideo);
		}
		return playlist;

	}

}
