package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class ServerFragment extends Fragment implements OnClickListener {

	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.server_layout, container, false);
		
		Button butUpdate = (Button) view.findViewById(R.id.server_button_save);
		Button butConnect = (Button) view.findViewById(R.id.server_button_connect);
		
		return view;
		
	}

	@Override
	public void onClick(View v) {
		
	}
}
