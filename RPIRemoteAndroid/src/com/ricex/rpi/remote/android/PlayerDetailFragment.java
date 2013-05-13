package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ricex.rpi.common.message.remote.RemoteClient;


public class PlayerDetailFragment extends Fragment implements OnClickListener{

	/** The client to show details of */
	private RemoteClient client;
	
	/** Sets the client this detail fragment is going to display */
	public void setClient(RemoteClient client) {
		this.client = client;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.player_detail_layout, container, false);
		
		if (client!= null) {
			TextView clientName = (TextView) view.findViewById(R.id.player_detail_title);
			TextView clientStatus = (TextView) view.findViewById(R.id.player_detail_status);
			
			CheckBox clientEnabled = (CheckBox) view.findViewById(R.id.player_detail_enabled);
			
			clientName.setText(client.getName());
			clientStatus.setText(client.getStatus().toString());
			
			clientEnabled.setChecked(client.isEnabled());
			clientEnabled.setOnClickListener(this);

		}
		
		return view;
		
	}
	
	public void onClick(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		
		client.setEnabled(checked);
		Log.i("RPI Client Detail", "Setting client " + client.getName() + "{" + client + "} to enabled: " + checked);
	}



}
