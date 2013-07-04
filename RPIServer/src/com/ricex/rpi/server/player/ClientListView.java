package com.ricex.rpi.server.player;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** View for displaying a list of all the clients currently connected to the server
 * 
 * TODO: Add change listener for name changes
 * TODO: Add a view for the status of the client
 * 
 * @author Mitchell
 *
 */

public class ClientListView extends JPanel implements ClientConnectionListener<RPIClient> {

	/** The list view of the clients */
	private JList<RPIClient> clientList;
	
	/** The list model for the clientList */
	private DefaultListModel<RPIClient> clientListModel;
	
	public ClientListView() {
		
		clientListModel = new DefaultListModel<RPIClient>();
		initListModel();
		
		clientList = new JList<RPIClient>(clientListModel);
		
		//set the border layout
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		//add the list to the layout
		add(clientList, BorderLayout.CENTER);
		
		//add this as a connection listener
		RPIServer.getInstance().addConnectionListener(this);
	}
	
	/** Populates the client list model from the list of clients in RPIServer
	 * 
	 */
	
	private void initListModel() {
		clientListModel.clear();
		for (RPIClient client : RPIServer.getInstance().getConnectedClients()) {
			clientListModel.addElement(client);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void clientConnected(RPIClient client) {
		clientListModel.addElement(client);
	}
	
	/**
	 * {@inheritDoc}
	 */	

	@Override
	public void clientDisconnected(RPIClient client) {
		clientListModel.removeElement(client);
	}
}
