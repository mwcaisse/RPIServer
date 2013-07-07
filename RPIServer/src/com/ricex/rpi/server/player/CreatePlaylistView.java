package com.ricex.rpi.server.player;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** Class for displaying the controls to create a playlist
 * 
 * @author Mitchell
 *
 */

public class CreatePlaylistView extends JPanel implements ClientConnectionListener<RPIClient>, ActionListener {

	/** Button for creating / updating the play list */
	private JButton butSave;
	
	/** Button to cancel editing the given playlist */
	private JButton butCancel;
	
	/** The textfield for the playlist name */
	private JTextField txtPlaylistName;
	
	/** The label for the playlist name */
	private JLabel labPlaylistName;
	
	/** the label for the client */
	private JLabel labClient;
	
	/** The combo box containing the list of clients */
	private JComboBox<RPIClient> cbxClients;
	
	/** The ComboBoxModel for the client combo box */
	private DefaultComboBoxModel<RPIClient> clientModel;
	
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
		labClient = new JLabel("Client:");
		
		clientModel = new DefaultComboBoxModel<RPIClient>();
		
		for (RPIClient client: RPIServer.getInstance().getConnectedClients()) {
			clientModel.addElement(client);
		}
		
		
		txtPlaylistName = new JTextField();
		cbxClients = new JComboBox<RPIClient>(clientModel);
		
		butSave.addActionListener(this);
		butCancel.addActionListener(this);
		
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, labPlaylistName, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labPlaylistName, HORIZONTAL_PADDING, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, txtPlaylistName, 0, SpringLayout.NORTH, labPlaylistName);
		layout.putConstraint(SpringLayout.WEST, txtPlaylistName, HORIZONTAL_PADDING, SpringLayout.EAST, labPlaylistName);
		layout.putConstraint(SpringLayout.EAST, txtPlaylistName, -HORIZONTAL_PADDING, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH, labClient, 0, SpringLayout.NORTH, cbxClients);
		layout.putConstraint(SpringLayout.WEST, labClient, HORIZONTAL_PADDING, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, cbxClients, VERTICAL_PADDING , SpringLayout.SOUTH, txtPlaylistName);
		layout.putConstraint(SpringLayout.WEST, cbxClients, 0, SpringLayout.WEST, txtPlaylistName);
		layout.putConstraint(SpringLayout.EAST, cbxClients, -HORIZONTAL_PADDING , SpringLayout.WEST, butSave);
		
		layout.putConstraint(SpringLayout.EAST, butCancel, -HORIZONTAL_PADDING, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, butCancel, 0, SpringLayout.NORTH, cbxClients);
		
		layout.putConstraint(SpringLayout.EAST, butSave, -HORIZONTAL_PADDING, SpringLayout.WEST, butCancel);
		layout.putConstraint(SpringLayout.NORTH, butSave, 0, SpringLayout.NORTH, butCancel);
		
		setLayout(layout);
		
		add(butSave);
		add(butCancel);
		add(txtPlaylistName);
		add(labPlaylistName);
		add(labClient);
		add(cbxClients);
		
		setPreferredSize(new Dimension(250,60));
		
		RPIServer.getInstance().addConnectionListener(this);
	}

	@Override
	public void clientConnected(RPIClient client) {
		clientModel.addElement(client);		
	}

	@Override
	public void clientDisconnected(RPIClient client) {
		clientModel.removeElement(client);
	}
	
	/** Saves the given play list */
	
	private void save() {
		String name = txtPlaylistName.getText();
		RPIClient client = (RPIClient) cbxClients.getSelectedItem();
		if (client != null && !name.trim().isEmpty()) {
			Playlist playlist = new Playlist(name);
			client.getPlaylistController().addPlaylist(playlist);
			txtPlaylistName.setText("");
			playlistView.refreshPlaylists();
		}
		else {
			//there is no selected client, for now do nothing
		}
	}

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
