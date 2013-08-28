package com.ricex.rpi.remote.player.view.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.remote.client.RPIClient;

/**
 * The table model for the ClientTableView
 * 
 * @author Mitchell Caisse
 * 
 */

public class ClientTableModel implements TableModel {

	/** The table Model listeners */
	private List<TableModelListener> tableModelListeners;
	
	/** The list of RPI Clients that this table model will display */
	private List<RPIClient> clients;
	
	/** The array containing the class types of the columns */
	private final Class<?>[] columnTypes = {String.class, RPIStatus.class};
	
	/** The names of the columns */
	private final String[] columnNames = {"Name", "Status"};

	/**
	 * Creates a new instance of Client Table Model
	 * 
	 */

	public ClientTableModel() {
		tableModelListeners = new ArrayList<TableModelListener>();
		clients = new ArrayList<RPIClient>();
	}	


	/**
	 * {@inheritDoc}
	 */

	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	/**
	 * {@inheritDoc}
	 */

	public int getColumnCount() {
		return columnTypes.length;
	}

	/**
	 * {@inheritDoc}
	 */

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	/**
	 * {@inheritDoc}
	 */

	public int getRowCount() {
		return clients.size();
	}

	/**
	 * {@inheritDoc}
	 */

	public Object getValueAt(int rowIndex, int columnIndex) {
		RPIClient client = clients.get(rowIndex);		
		switch (columnIndex) {
			case 0: return client.getName();
			case 1: return client.getStatus();
			default: return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		//TODO: not currently implemented as table is not editable
	}
	
	/** Sets the list of RPI Clients to display to the given RPI clients
	 * 
	 * @param clients The new RPI clients
	 */
	
	public void setClients(List<RPIClient> clients) {
		this.clients = clients;
		fireTableChangedEvent(new TableModelEvent(this));
	}
	
	/** Adds the given RPI client
	 * 
	 * @param client the client to add
	 */
	
	public void addClient(RPIClient client) {
		clients.add(client);
		fireTableChangedEvent(new TableModelEvent(this));
	}
	
	/** Removes the given client
	 * 
	 * @param client The client to remove
	 */
	//TODO: update the event to be more specific
	public void removeClient(RPIClient client) {
		clients.remove(client);
		fireTableChangedEvent(new TableModelEvent(this));
	}

	/**
	 * {@inheritDoc}
	 */

	public void addTableModelListener(TableModelListener listener) {
		tableModelListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */

	public void removeTableModelListener(TableModelListener listener) {
		tableModelListeners.remove(listener);
	}
	
	/** Sends the given table model event to all of the listeners
	 * 
	 * @param e The table model event to sent
	 */
	
	private void fireTableChangedEvent(TableModelEvent e) {
		for (TableModelListener listener: tableModelListeners) {
			listener.tableChanged(e);
		}
	}

}
