package com.ricex.rpi.server.player.view.client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** View for displaying a list of all the clients currently connected to the server
 * 
 * TODO: Add change listener for name changes
 * 
 * @author Mitchell Caisse
 *
 */

public class ClientTableView extends JPanel implements ClientConnectionListener<RPIClient> {

	/** The table to display all the clients */
	private JTable clientTable;
	
	/** The table model for the client */
	private ClientTableModel clientTableModel;
	
	/** The scroll pane for the table view */
	private JScrollPane tableScrollPane;
	
	public ClientTableView() {			
		
		clientTableModel = new ClientTableModel();
		clientTable = new JTable(clientTableModel);

		clientTableModel.setClients(RPIServer.getInstance().getConnectedClients());
		
		tableScrollPane = new JScrollPane(clientTable);
		
		//set the border layout
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		//add the list to the layout
		add(tableScrollPane, BorderLayout.CENTER);
		
		//add this as a connection listener
		RPIServer.getInstance().addConnectionListener(this);
	}
	


	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void clientConnected(RPIClient client) {
		clientTableModel.addClient(client);
	}
	
	/**
	 * {@inheritDoc}
	 */	

	@Override
	public void clientDisconnected(RPIClient client) {
		clientTableModel.removeClient(client);
	}
}
