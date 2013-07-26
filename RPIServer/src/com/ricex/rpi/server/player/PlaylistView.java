package com.ricex.rpi.server.player;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.video.Video;

/** View for displaying the list of playlists as well as creating new playlists
 * 
 * @author Mitchell
 *
 */

public class PlaylistView extends JPanel implements ListSelectionListener, PlayableView {

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

		createPlaylistView = new CreatePlaylistView(this);

		//if there is an active client, populate its playlists
		populatePlaylistModel(RPIPlayer.getInstance().getPlaylistController());


		//create the playlists
		playlistList = new JList<Playlist>(playlistModel);
		playlistItemList = new JList<Video>(playlistItemModel);

		//set each list to single selection
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//add the border around the lists
		/*playlistList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playlistItemList.setBorder(BorderFactory.createLineBorder(Color.BLACK));*/

		playlistList.addListSelectionListener(this);

		JScrollPane playlistScrollPane = new JScrollPane(playlistList);
		JScrollPane playlistItemScrollPane = new JScrollPane(playlistItemList);

		playlistScrollPane.setPreferredSize(new Dimension(200,250));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		centerPanel.add(playlistItemScrollPane, BorderLayout.CENTER);
		centerPanel.add(createPlaylistView, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(playlistScrollPane, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);


	}

	/** Populates the list view with the playlists from the given playlist controller
	 * 
	 * @param playlistController The playlist controller to populate the playlist model from
	 */

	private void populatePlaylistModel(PlaylistController playlistController) {
		playlistModel.clear();
		for (Playlist playlist : playlistController.getAllPlaylists()) {
			playlistModel.addElement(playlist);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Playlist selectedPlaylist = playlistList.getSelectedValue();
		if (selectedPlaylist == null) {
			return; //TODO: implement what happens when this is null
		}
		playlistItemModel.clear();
		for (Video video : selectedPlaylist.getItems()) {
			playlistItemModel.addElement(video);
		}
	}

	public void refreshPlaylists() {		
		populatePlaylistModel(RPIPlayer.getInstance().getPlaylistController());
	}

	/** Returns the selected playlist
	 * 
	 */

	@Override
	public Playlist getPlaylistToPlay() {
		Playlist playlist = playlistList.getSelectedValue();
		if (playlist == null) {
			playlist = new Playlist();
		}
		return playlist;
	}

}
