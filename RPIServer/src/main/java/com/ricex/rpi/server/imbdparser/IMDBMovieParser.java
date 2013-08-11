package com.ricex.rpi.server.imbdparser;

import java.io.File;
import java.io.FileFilter;

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;

/**
 * Movie parser that reads data from XML meta file, as well as attempting to
 * fetch data from IMBD
 * 
 * 
 * @author Mitchell
 * 
 */

public class IMDBMovieParser implements MovieParser {

	private static final String[] acceptedFileNames = { "avi", "mkv", "mp4" };

	/** The base folder for the movies */
	private String baseFolder;

	/** The file representing the root dir */
	private File baseFile;

	/** Filter for filterting files based on directory, and extension */
	private MovieFileFilter movieFilter;

	public IMDBMovieParser() {
		movieFilter = new MovieFileFilter();
	}

	/**
	 * Sets the base folder to the given base folder
	 * 
	 * @param baseFolder
	 */

	private void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
		baseFile = new File(baseFolder);
	}

	/**
	 * {@inheritDoc}
	 */

	public Video parseVideos(String baseFolder) {
		setBaseFolder(baseFolder);
		File rootDir = baseFile;

		if (rootDir.isDirectory()) {
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
		return new Movie(parseMovieName(file.getName()), getRelativeFilePath(file));
	}

	private String getRelativeFilePath(File file) {
		return baseFile.toURI().relativize(file.toURI()).getPath();
	}

	/**
	 * Parses the name of the movie from the file name
	 * 
	 * @param fileName
	 *            The name of the file
	 * @return The name of the movie, or the name of the file if movie name
	 *         could not be parsed
	 */

	private String parseMovieName(String fileName) {
		int lastDot = fileName.lastIndexOf(".");
		String name = fileName.substring(0, lastDot);
		return name;
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
