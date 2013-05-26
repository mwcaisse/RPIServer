package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ricex.rpi.remote.android.cache.RemoteProperties;
import com.ricex.rpi.remote.android.network.ServerConnector;


public class ServerFragment extends Fragment implements OnClickListener {

	/** Buttons for updating and connecting */
	private Button butUpdate;
	private Button butConnect;
	
	/** Text field for the server address */
	private EditText textServerAddress;
	
	/** Text field for the server port */
	private EditText textServerPort;
	
	/** Text field for the status of the server */
	private TextView textServerStatus;
	
	/** The server connector */
	private ServerConnector serverConnector;
	
	/**
	 * {@inheritDoc}
	 */
	
	public ServerFragment() {
		serverConnector = ServerConnector.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.server_layout, container, false);
		
		butUpdate = (Button) view.findViewById(R.id.server_button_save);
		butConnect = (Button) view.findViewById(R.id.server_button_connect);
		
		textServerAddress = (EditText) view.findViewById(R.id.server_address);
		textServerPort = (EditText) view.findViewById(R.id.server_port);
		
		textServerStatus = (TextView) view.findViewById(R.id.server_status);
		
		butUpdate.setOnClickListener(this);
		butConnect.setOnClickListener(this);
		
		textServerAddress.setText(RemoteProperties.getInstance().getServerAddress());	
		textServerPort.setText(RemoteProperties.getInstance().getServerPort());
		
		return view;
		
	}
	
	/** Updates the server status text field as well as the text on the connect/disconnect button
	 * 
	 */	
	private void updateServerStatus() {
		
		if (serverConnector.isConnected()) {
			butConnect.setText("Disconnect");
			textServerStatus.setText("Connected");
		}
		else {
			butConnect.setText("Connect");
			textServerStatus.setText("Disconnected");
		}	
	}

	@Override
	public void onClick(View v) {
		
		if (v.equals(butUpdate)) {
			String serverAddress = textServerAddress.getText().toString();
			int serverPort = Integer.parseInt(textServerPort.getText().toString());
			RemoteProperties.getInstance().setServerAddress(serverAddress);
		}
		else if (v.equals(butConnect)) {
			if (serverConnector.isConnected()) {
				serverConnector.disconnect();
			}
			else {
				serverConnector.connect();
			}
		}
		
		updateServerStatus();		
		
	}
}
