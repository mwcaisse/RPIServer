package com.ricex.rpi.remote.player.view.playlist;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.remote.player.RPIRemote;

/** Class for displaying the controls to create a playlist
 * 
 * @author Mitchell Caisse
 *
 */

public class CreatePlaylistView extends JPanel implements ActionListener {

	/** Button for creating / updating the play list */
	private JButton butSave;

	/** Button to cancel editing the given playlist */
	private JButton butCancel;

	/** The textfield for the playlist name */
	private JTextField txtPlaylistName;

	/** The label for the playlist name */
	private JLabel labPlaylistName;

	/** The view that this playlistView corresponds to */
	private PlaylistView playlistView;

	private static final int HORIZONTAL_PADDING = 5;
	private static final int VERTICAL_PADDING = 5;

	/** Creates a new instance of the create playlist view
	 *
	 */

	public CreatePlaylistView(PlaylistView playlistView) {
		this.playlistView = playlistView;

		//initialize the buttons
		butSave = new JButton("Save");
		butCancel = new JButton("Cancel");

		labPlaylistName = new JLabel("Name:");

		txtPlaylistName = new JTextField();


		butSave.addActionListener(this);
		butCancel.addActionListener(this);

		SpringLayout layout = new SpringLayout();

		layout.putConstraint(SpringLayout.NORTH, labPlaylistName, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labPlaylistName, HORIZONTAL_PADDING, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, txtPlaylistName, 0, SpringLayout.NORTH, labPlaylistName);
		layout.putConstraint(SpringLayout.WEST, txtPlaylistName, HORIZONTAL_PADDING, SpringLayout.EAST, labPlaylistName);
		layout.putConstraint(SpringLayout.EAST, txtPlaylistName, -HORIZONTAL_PADDING, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.EAST, butCancel, -HORIZONTAL_PADDING, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, butCancel, VERTICAL_PADDING , SpringLayout.SOUTH, txtPlaylistName);

		layout.putConstraint(SpringLayout.EAST, butSave, -HORIZONTAL_PADDING, SpringLayout.WEST, butCancel);
		layout.putConstraint(SpringLayout.NORTH, butSave, 0, SpringLayout.NORTH, butCancel);

		setLayout(layout);

		add(butSave);
		add(butCancel);
		add(txtPlaylistName);
		add(labPlaylistName);

		setPreferredSize(new Dimension(250,60));

	}

	/** Saves the given play list */

	private void save() {
		String name = txtPlaylistName.getText();
		if (!name.trim().isEmpty()) { 
			//if the user entered a name, add the playlist
			Playlist playlist = new Playlist(name);
			RPIRemote.getInstance().getPlaylistController().addPlaylist(playlist);
			txtPlaylistName.setText("");
			playlistView.refreshPlaylists();
		}
	}

	/** Handles save and or cancel being pressed, if saved is press it verifies the playlist then saves it,
	 *  canel will clear all changes
	 * 
	 * @param e {@inheritDoc}
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(butSave)) {
			save();
		}
		else {
			txtPlaylistName.setText("");
		}
	}

}
