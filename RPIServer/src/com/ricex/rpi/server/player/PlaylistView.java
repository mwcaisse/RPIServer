package com.ricex.rpi.server.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.RPIClient;

/** View for displaying the list of playlists as well as creating new playlists 
 * 
 * @author Mitchell
 *
 */

public class PlaylistView extends JPanel implements ActiveClientListener, ListSelectionListener {

	/** The List of play lists */
	private JList<Playlist> playlistList;
	
	/** The list of playlist items */
	private JList<Video> playlistItemList;
	
	/** The list model for the playlists */
	private DefaultListModel<Playlist> playlistModel;
	
	/** The list model for the playlist items */
	private DefaultListModel<Video> playlistItemModel;
	
	/** The View for creating a new playlist */
	private JPanel createPlaylistView;
	
	
	/** Creates a new instance of the playlist view 
	 */
	
	public PlaylistView() {
		//create the playlist models
		playlistItemModel = new DefaultListModel<Video>();
		playlistModel = new DefaultListModel<Playlist>();
		
		//if there is an active client, populate its playlists 
		if (RPIPlayer.getInstance().activeClientExists()) {
			populatePlaylistModel(RPIPlayer.getInstance().getActiveClient());
		}
		
		//create the playlists
		playlistList = new JList<Playlist>(playlistModel);
		playlistItemList = new JList<Video>(playlistItemModel);
		
		//set each list to single selection
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//add the border around the lists
		playlistList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playlistItemList.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		playlistList.addListSelectionListener(this);
		
		playlistList.setPreferredSize(new Dimension(200,250));
		
		setLayout(new BorderLayout());
		add(playlistList, BorderLayout.EAST);
		add(playlistItemList, BorderLayout.CENTER);
		
		RPIPlayer.getInstance().addActiveClientListener(this);
		
	}
	
	/** Populates the list view with the playlists from the given active client
	 * 
	 * @param activeClient
	 */
	
	private void populatePlaylistModel(RPIClient activeClient) {
		playlistModel.clear();
		for (Playlist playlist : activeClient.getPlaylistController().getAllPlaylists()) {
			playlistModel.addElement(playlist);
		}
	}


	@Override
	public void activeClientChanged(RPIClient activeClient) {
		populatePlaylistModel(activeClient);
	}


	@Override
	public void activeClientRemoved() {
		//clear the items in the list views as there is no more active client
		playlistItemModel.clear();
		playlistModel.clear();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Playlist selectedPlaylist = playlistList.getSelectedValue();
		playlistItemModel.clear();
		for (Video video : selectedPlaylist.getItems()) {
			playlistItemModel.addElement(video);
		}
	}
	
}
