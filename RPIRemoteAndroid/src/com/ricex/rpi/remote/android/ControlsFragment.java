package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** The fragment for displaying the movie controls 
 * 
 * @author Mitchell
 *
 */

public class ControlsFragment extends Fragment {	

	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.controls_layout, container, false);	
		
		return view;
		
	}
}
