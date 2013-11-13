package com.ricex.rpi.remote.player.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ricex.rpi.remote.RPIRemoteProperties;

/** Pane that is responsible for controlling the connecting to the server
 *  
 *  For instance the address of the server to connect to, the port of the server, and connect / disconnect	
 * 
 * @author Mitchell Caisse
 *
 */

// TODO: Create a listener for the server status, disconnected / connected

public class ServerControllerPane extends JPanel implements ActionListener {

	/** Button to connect / disconnect to/from the server */
	private JButton butConnect;
	
	/** Text field to enter the server address */
	private JTextField txtServerAddress;
	
	/** Text field to enter the server port */
	private JTextField txtServerPort;
	
	/** Label for the server address text field */
	private JLabel labServerAddress;
	
	/** Label for the server port text field */
	private JLabel labServerPort;
	
	/** Label for displaying the status of the server, connected / disconnected. etc */
	private JLabel labServerStatus;
	
	public ServerControllerPane() {
		
		butConnect = new JButton("Connect");
		
		txtServerAddress = new JTextField(RPIRemoteProperties.getInstance().getServerAddress());
		txtServerPort = new JTextField(RPIRemoteProperties.getInstance().getServerPort());
		
		labServerPort = new JLabel("Port:");
		labServerAddress = new JLabel("Address:");
		labServerStatus = new JLabel("Status: Disconnected");
	}

	/** Called when the user clicks on the Connect button
	 * 
	 */
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(butConnect)) {
			//but connect was pressed.
			//right now we will just set the properties when connect is pressed.
			String serverAddress = txtServerAddress.getText();
			int serverPort = Integer.parseInt(txtServerPort.getText());
			
			RPIRemoteProperties.getInstance().setServerAddress(serverAddress);
			RPIRemoteProperties.getInstance().setServerPort(serverPort);
			
			//TODO: maybe actualy connect to the server
		}
		
	}
	
}
