package com.ricex.rpi.remote.android.cache;

import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Video;


public class DirectoryCache {

	/** The singleton instance */
	private static DirectoryCache _instance;
	
	/** Returns the singleton instance of this class */
	
	public static DirectoryCache getInstance() {
		if (_instance == null) {
			_instance = new DirectoryCache();
		}
		return _instance;
	}
	
	/** The video representign the root directory of the directory listing */
	private Video rootDirectory;
	
	/** The listeners listening for changes in the root directory */
	private List<DirectoryChangeListener> listeners;
	
	/** Creates a new directory cache with an empty directory structure
	 * */
	private DirectoryCache() {
		rootDirectory = new Directory("root");
		listeners = new ArrayList<DirectoryChangeListener>();
	}
	
	/** Sets the root directory to the given directory 
	 * 
	 * @param rootDirectory
	 */
	public void setRootDirectory(Video rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	/** Returns the root directory */
	public Video getRootDirectory() {
		return rootDirectory;
	}
	
	/** Adds the given directory listener
	 * 
	 * @param listener The listener to add
	 */
	
	public void addListener(DirectoryChangeListener listener) {
		listeners.add(listener);
	}
	
	/** Removes the given listener
	 * 
	 * @param listener The listenner to remove
	 */
	
	public void removeListener(DirectoryChangeListener listener) {
		listeners.remove(listener);
	}
	
	/** Notifies all of the listeners that a change to the root directory has been made 
	 */	
	
	private void notifyListeners() {
		for (DirectoryChangeListener listener : listeners) {
			listener.rootDirectoryChanged(rootDirectory);
		}
	}
}
