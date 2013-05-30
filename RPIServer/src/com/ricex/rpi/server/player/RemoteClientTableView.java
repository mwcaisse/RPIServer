package com.ricex.rpi.server.player;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.RemoteServer;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.client.RemoteClient;


/**The view for displaying a table of the known clients, and thier status + id and possibly name
 * 
 * @author Mitchell
 *
 */

public class RemoteClientTableView extends BorderPane implements ClientChangeListener<RemoteClient>, ClientConnectionListener<RemoteClient> {

	/** Instance of the running server to fetch information about the clients */
	private RemoteServer server;
	
	/** List of the clients currently being displayed */	
	private ObservableList<RemoteClient> remoteClients;
	
	/** The table view for displaying the clients */
	private TableView<RemoteClient> clientTable;
	
	
	@SuppressWarnings("unchecked")
	public RemoteClientTableView(RemoteServer server) {
		this.server = server;
		
		server.addConnectionListener(this);
		
		clientTable = new TableView<RemoteClient>();
		remoteClients = FXCollections.observableArrayList(server.getConnectedClients());
		clientTable.setItems(remoteClients);
		
		TableColumn<RemoteClient, Long> idColumn = new TableColumn<RemoteClient, Long>("Id");
		TableColumn<RemoteClient, String> nameColumn = new TableColumn<RemoteClient, String>("Name");
		
		idColumn.setCellValueFactory(new PropertyValueFactory<RemoteClient, Long>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<RemoteClient, String>("name"));
		
		//set the widths.
		idColumn.prefWidthProperty().bind(clientTable.widthProperty().multiply(0.20)); // 10%
		nameColumn.prefWidthProperty().bind(clientTable.widthProperty().multiply(0.8));
		
		clientTable.getColumns().addAll(idColumn, nameColumn);
		
		setCenter(clientTable);
	}


	public void clientConnected(RemoteClient remoteClient) {
		remoteClients.add(remoteClient);
		remoteClient.addChangeListener(this);
	}

	public void clientDisconnected(RemoteClient remoteClient) {
		remoteClients.remove(remoteClient);
		remoteClient.removeChangeListener(this);
	}

	public void clientChanged(RemoteClient remoteClient) {
		System.out.println("Client has changed thier status");
		
		Platform.runLater(new Runnable() {
			public void run() {
				clientTable.getColumns().get(0).setVisible(false);
				clientTable.getColumns().get(0).setVisible(true);
			}
		});
		
	}
}
