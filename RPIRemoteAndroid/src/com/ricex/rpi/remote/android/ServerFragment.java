package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ricex.rpi.remote.android.cache.RemoteProperties;
import com.ricex.rpi.remote.android.network.ServerConnectionListener;
import com.ricex.rpi.remote.android.network.ServerConnector;


public class ServerFragment extends Fragment implements OnClickListener, ServerConnectionListener {

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
		serverConnector.addConnectionListener(this);
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
		textServerPort.setText(RemoteProperties.getInstance().getServerPort() + "");

		updateViewElements(serverConnector.isConnected());
		return view;

	}

	/** Updates the view elements based upon the current connection status */
	private void updateViewElements(boolean connected) {
		if (butConnect != null) {
			if (connected) {
				textServerStatus.setText("Status: Connected");
				butConnect.setText("Disconnect");

			}
			else {
				textServerStatus.setText("Status: Disconnected");
				butConnect.setText("Connect");
			}
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
				textServerStatus.setText("Status: Connecting...");
			}
		}

	}

	@Override
	public void serverConnectionChanged(final boolean connected) {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				updateViewElements(connected);
			}
		});		

	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void errorConnecting(final Exception e) {
		final Context context = getActivity();
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(context, "Error connecting to server: " + e.getMessage(), Toast.LENGTH_LONG).show();
				textServerStatus.setText("Status: Disconnected");
			}
		});
	}
}
