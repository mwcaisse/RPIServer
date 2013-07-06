package com.ricex.rpi.server.player;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.ricex.rpi.server.client.RPIClient;

/** Class for displaying the controls to create a playlist
 * 
 * @author Mitchell
 *
 */

public class CreatePlaylistView extends JPanel {

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
	
	
	private static final int HORIZONTAL_PADDING = 5;
	private static final int VERTICAL_PADDING = 5;
	
	/** Creates a new instance of the create playlist view
	 *
	 */
	
	public CreatePlaylistView() {
		
		//initialize the buttons 
		butSave = new JButton("Save");
		butCancel = new JButton("Cancel");
		
		txtPlaylistName = new JTextField();
		
		labPlaylistName = new JLabel("Name:");
		labClient = new JLabel("Client:");
		
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, labPlaylistName, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labPlaylistName, HORIZONTAL_PADDING, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, txtPlaylistName, 0, SpringLayout.NORTH, labPlaylistName);
		layout.putConstraint(SpringLayout.WEST, txtPlaylistName, HORIZONTAL_PADDING, SpringLayout.EAST, labPlaylistName);
		layout.putConstraint(SpringLayout.EAST, txtPlaylistName, -HORIZONTAL_PADDING, SpringLayout.EAST, this);
		
		setLayout(layout);
		
		//add(butSave);
		//add(butCancel);
		add(txtPlaylistName);
		add(labPlaylistName);
		
		setPreferredSize(new Dimension(250,50));
	}
	
}
