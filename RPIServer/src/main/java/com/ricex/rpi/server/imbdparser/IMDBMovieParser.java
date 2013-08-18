package com.ricex.rpi.server.imbdparser;

import java.io.File;
import java.io.FileFilter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.imbdparser.util.XMLUtil;

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
			//TODO: implement this
			return parseFile(rootDir, null);
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
		
	/** Returns the value of the property in for the file
	 * 
	 * @param xmlDocument The xmlDocument to parse the property from
	 * @param fileName The name of the movie file to fetch the property for
	 * @param propertyName The name of the property to fetch
	 * @return The value of the property, or null if it does not exist
	 */
	
	private String getVideoProperty(Document xmlDocument,String fileName, String propertyName) {
		String propteryValue = null;
		//fetch the property from the xml
		NodeList nodes = XMLUtil.INSTANCE.getXMLObject("/videos/video[@filename='" + fileName + "']/" + propertyName, xmlDocument);
		//check to make sure the property exists
		if (nodes != null && nodes.getLength() == 1) {
			//if it does set its value
			propteryValue = nodes.item(0).getFirstChild().getNodeValue();
		}		
		return propteryValue;
	}
	
	/** Determines if video properties for the video with the 
	 * 		given file name exist
	 * @param xmlDocument The xml document to check if properties exist
	 * @param fileName The name of the file to check
	 * @return True if the properties exist, false otherwise
	 */
	
	private boolean videoPropertiesExist(Document xmlDocument, String fileName) {
		NodeList nodes = XMLUtil.INSTANCE.getXMLObject("/videos/video[@filename='" + fileName + "']", xmlDocument);
		return (nodes != null && nodes.getLength() > 0);
	}

	/** Parses the movie information for the given file, if it exists
	 * 
	 * @param file The file to parse
	 * @param directoryConfig The Document containing the xml config for the directory
	 * 		containing the movie to parse
	 * @return The movie representing the given file
	 */

	private Movie parseFile(File file, Document directoryConfig) {
		IMDBMovie movie = new IMDBMovie();
		String fileName = file.getName();
		if (videoPropertiesExist(directoryConfig, fileName)) {
			movie.setName(getVideoProperty(directoryConfig, fileName, "name"));
			movie.setReleaseDate(getVideoProperty(directoryConfig, fileName, "releaseDate"));
			movie.setDescription(getVideoProperty(directoryConfig, fileName, "description"));
		}
		else {
			System.out.println("No properties for file " + fileName + " exist.");
		}
		
		return movie;
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
