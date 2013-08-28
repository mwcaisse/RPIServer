package com.ricex.rpi.server.player.view.video;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.player.RPIPlayer;
import com.ricex.rpi.server.player.view.PlayableView;

/** View that will display a tree of the videos for the active clietn
 * 
 * @author Mitchell Caisse 

 *
 */

public class VideoTreeView extends JPanel implements PlayableView {

	/** Logger */
	private static final Logger log = LoggerFactory.getLogger(VideoTreeView.class);
	
	/** The tree containing the list of videos */
	protected JTree videoTree;

	/** The tree model for the tree view */
	private DefaultTreeModel treeModel;

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
		videoTree.addMouseListener(new TreeViewMouseListener());
		
		addComponents();
	}
	
	/** Adds the components to the view and sets the view layout
	 * 
	 */
	
	protected void addComponents() {
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.getViewport().setLayout(new BorderLayout());
		scrollPane.getViewport().add(videoTree);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
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

		//TODO: refactor to allow multiple selection
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				
				rightMouseClicked(e.getX(), e.getY());
				
				//if none is selected, use selection path
				/*
				TreePath selectedPath = videoTree.getPathForLocation(e.getX(), e.getY());
				videoTree.setSelectionPath(selectedPath); // set the selected path of the tree, so right click vissualy selects
				if (selectedPath != null && videoTree.getSelectionCount() == 1) { //only do this is selection count is 1
					Video selectedItem = (Video)((DefaultMutableTreeNode)selectedPath.getLastPathComponent()).getUserObject();
					createPopupMenu(selectedItem).show(videoTree, e.getX(), e.getY());
				}*/
			}
		}
	}
	
	/** The right mouse button was pressed, at the given location
	 * 
	 * @param x The x location that was clicked
	 * @param y The y location that was clicked
	 * 
	 * TODO: refactor this method
	 * 
	 */
	
	protected void rightMouseClicked(int x, int y) {
		int selectionCount = videoTree.getSelectionModel().getSelectionCount();
		TreePath rightClickPath = videoTree.getPathForLocation(x,y);
		
		JPopupMenu popupMenu = null;
		
		log.info("SelectionCount: {} rightClickpath: {}", selectionCount, rightClickPath);
		
		if (selectionCount > 1) {
			log.info("Selection count is greater than 1");
			//more than one item was selected
			List<Video> selectedVideos = getVideosFromTreePaths(videoTree.getSelectionModel().getSelectionPaths());
			if (rightClickPath == null) {
				log.info("Nothing was right clicked on, creating a multiple popup menu");
				popupMenu = createPopupMenu(selectedVideos);				
			}
			else {
				//user right clicked somewhere
				log.info("A video was right clicked on ");
				Video rightClickVideo = (Video) ((DefaultMutableTreeNode)rightClickPath.getLastPathComponent()).getUserObject();
				if (selectedVideos.contains(rightClickVideo)) {
					log.info("Creating a popup menu for the selected videos");
					popupMenu = createPopupMenu(selectedVideos);	
				}
				else {
					log.info("Creating a popup menu for the video right clicked on");
					videoTree.setSelectionPath(rightClickPath);
					popupMenu = createPopupMenu(rightClickVideo);
				}
			}
		}
		else if (selectionCount == 1) {
			//one video was selected
			if (rightClickPath != null) {
				Video rightClickVideo = (Video) ((DefaultMutableTreeNode)rightClickPath.getLastPathComponent()).getUserObject();
				videoTree.setSelectionPath(rightClickPath);
				popupMenu = createPopupMenu(rightClickVideo);
			}
			else {
				popupMenu = createPopupMenu(getSelectedVideo());
			}
		}
		else {
			//selection count is 0
			if (rightClickPath != null) {
				Video rightClickVideo = (Video) ((DefaultMutableTreeNode)rightClickPath.getLastPathComponent()).getUserObject();
				videoTree.setSelectionPath(rightClickPath);
				popupMenu = createPopupMenu(rightClickVideo);
			}
		}
		
		if (popupMenu != null) {
			log.info("Showing popup menu at {},{}", x, y);
			popupMenu.show(videoTree, x, y);
		}
	}
	
	/** Puts all of the videos in the tree paths in a list, ignoring directories
	 * 
	 * @param treePaths The array of tree paths
	 * @return a list of videos in the tree paths
	 */
	
	private List<Video> getVideosFromTreePaths(TreePath[] treePaths) {
		List<Video> videos = new ArrayList<Video>();
		for (TreePath path : treePaths) {
			Video toAdd = (Video) ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
			if (!toAdd.isDirectory()) {
				videos.add(toAdd);	
			}
		}
		return videos;
	}
	
	/** Creates a popup menu for the selected video
	 * 
	 * @param video The video to create the popup menu for
	 */
	
	private JPopupMenu createPopupMenu(Video video) {
		JPopupMenu menu;
		if (video.isDirectory()) {
			menu = new DirectoryPopupMenu(video);
		}
		else {
			menu = new VideoPopupMenu(video);
		}		
		return menu;
	}
	
	/** Creates a popup menu for the list of selected videos
	 * 
	 * @param videos The videos to create the popup menu for
	 * @return The popup menu
	 */
	
	private JPopupMenu createPopupMenu(List<Video> videos) {
		return new MultipleVideoPopupMenu(videos);
	}

	/** Return the playlist to play
	 * 
	 */

	public Playlist getPlaylistToPlay() {
		Playlist playlist = new Playlist();
		if (videoTree.getSelectionPath() != null) {
			Video selectedVideo = getSelectedVideo();
			playlist.addItem(selectedVideo);
		}
		return playlist;

	}
	
	/** Returns the currently selected video
	 * 
	 * @return The selected video
	 */
	
	protected Video getSelectedVideo() {
		return  (Video) ((DefaultMutableTreeNode)videoTree.getSelectionPath().getLastPathComponent()).getUserObject();
	}
	
	/** Checks if more than one video is selected in the Video Tree.
	 *  Will return false if 0 or 1 movies are selected
	 * 
	 * @return True if more than one video is selected, false otherwise.
	 */
	
	protected boolean multipleVideosSelected() {
		return videoTree.getSelectionCount() > 1;
	}
	
	/** Adds the given tree selection listener as a listener on the video tree
	 * 
	 * @param listener The listener to add
	 */
	
	protected void addTreeSelectionListener(TreeSelectionListener listener) {
		videoTree.addTreeSelectionListener(listener);
	}
	
	/** Removes the given tree selection listener from the video tree
	 * 
	 * @param listener The listener to remove
	 */
	
	protected void removeTreeSelectionListener(TreeSelectionListener listener) {
		videoTree.removeTreeSelectionListener(listener);
	}

}
