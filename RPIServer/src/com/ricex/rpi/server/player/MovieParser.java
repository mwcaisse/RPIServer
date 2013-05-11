package com.ricex.rpi.server.player;

import java.io.File;
import java.io.FileFilter;

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.Video;

/**
 * Parses the movies from the given base folder
 * 
 * @author Mitchell
 * 
 */

public class MovieParser {

	private static final String[] acceptedFileNames = { "avi", "mkv", "mp4" };

	/** The base folder for the movies */
	private String baseFolder;

	/** The file representing the root dir */
	private File baseFile;

	/** Filter for filterting files based on directory, and extension */
	private MovieFileFilter movieFilter;

	public MovieParser(String baseFolder) {
		this.baseFolder = baseFolder;
		baseFile = new File(baseFolder);
		movieFilter = new MovieFileFilter();
	}

	/**
	 * Parses the videos from the base folder
	 * 
	 * @return root Video node
	 */

	public Video parseVideos() {
		File rootDir = baseFile;

		if (baseFile.isDirectory()) {
			return parseDirectory(rootDir);
		}
		else {
			return parseFile(rootDir);
		}
	}

	/**
	 * Returns the Directory representing the given File
	 * 
	 * @return
	 */

	private Directory parseDirectory(File dir) {
		Directory root = new Directory(dir.getName());

		for (File file : dir.listFiles(movieFilter)) {
			if (file.isDirectory()) {
				root.addChild(parseDirectory(file));
			}
			else {
				root.addChild(parseFile(file));
			}
		}

		return root;
	}

	/** Returns the Movie representing the given File */

	private Movie parseFile(File file) {
		return new Movie(file.getName(), getRelativeFilePath(file));
	}

	private String getRelativeFilePath(File file) {
		return baseFile.toURI().relativize(file.toURI()).getPath();
	}

	private class MovieFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true; // accept directories
			}
			// it is a file, check for accepted file extensions
			String name = file.getName();
			for (String ext : acceptedFileNames) {
				if (name.endsWith(ext)) {
					return true;
				}
			}
			return false;
		}

	}
}
