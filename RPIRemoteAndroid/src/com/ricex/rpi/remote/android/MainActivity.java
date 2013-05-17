package com.ricex.rpi.remote.android;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	/** The action bar of the activity, used for adding the navigational tabs */
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab moviesTab = actionBar.newTab().setText("Movies");
		Tab controlTab = actionBar.newTab().setText("Controls");
		Tab playersTab = actionBar.newTab().setText("Players");
		Tab serverTab = actionBar.newTab().setText("Server");
		
		moviesTab.setTabListener(new NavigationTabListener(new MoviesListFragment()));
		controlTab.setTabListener(new NavigationTabListener(new ControlsFragment()));
		playersTab.setTabListener(new NavigationTabListener(new PlayersListFragment()));
		serverTab.setTabListener(new NavigationTabListener(new ServerFragment()));
		
		actionBar.addTab(moviesTab);
		actionBar.addTab(controlTab);
		actionBar.addTab(playersTab);	
		actionBar.addTab(serverTab);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class NavigationTabListener implements TabListener {

		/** The fragment to switch to when this tab is selected */
		private Fragment fragment;
		
		private NavigationTabListener(Fragment fragment) {
			this.fragment = fragment;
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			//Toast.makeText(getBaseContext(),  "Reselected: " + tab.getText(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			//Toast.makeText(getBaseContext(),  "Selected: " + tab.getText(), Toast.LENGTH_SHORT).show();
			ft.replace(R.id.fragment_container, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			//Toast.makeText(getBaseContext(),  "Unselected: " + tab.getText(), Toast.LENGTH_SHORT).show();
			ft.remove(fragment);
		}
		
	}

}
