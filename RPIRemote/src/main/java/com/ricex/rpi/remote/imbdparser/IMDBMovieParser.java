package com.ricex.rpi.remote.imbdparser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.imbdparser.util.XMLUtil;

/**
 * Movie parser that reads data from XML meta file, as well as attempting to
 * fetch data from IMBD
 * 
 * 
 * @author Mitchell
 * 
 */

public class IMDBMovieParser implements MovieParser {
	
	private static final Logger log = LoggerFactory.getLogger(IMDBMovieParser.class);

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
		
		Document directoryConfig = getDirectoryConfig(dir);

		for (File file : dir.listFiles(movieFilter)) {
			if (file.isDirectory()) {
				root.addChild(parseDirectory(file));
			}
			else {
				root.addChild(parseFile(file, directoryConfig));
			}
		}

		return root;
	}
	
	/** Parses the xml file containing the configuration in the directory
	 * 
	 * @param directory The directory to parse the configuration for
	 * @return The document representing the configuration
	 */
	
	private Document getDirectoryConfig(File directory) {
		File[] configFiles = directory.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return name.equals("rpiplayer.xml");
			}
		});		
		Document xmlDocument = null;		
		if (configFiles.length > 0) {
			xmlDocument = XMLUtil.INSTANCE.getXMLDocument(configFiles[0]);
		}	
		else {	
			log.info("No directory config exists for directory {}", directory.getName());
		}
		return xmlDocument;
	}
	
	/** Fetches the Properties map for a movie file
	 * 
	 * @param directoryConfig The configuration for the directory the file is in
	 * @param fileName The name of the file
	 * @return The properties map, or null if none exist
	 */
	//Doesnt work if there is a quote in the file name.. -.-
	private Map<String, String> getMovieProperties(Document directoryConfig, String fileName) {	
		NodeList nodes = XMLUtil.INSTANCE.getXMLObject("/videos/video[@filename='" + fileName + "']/*",directoryConfig);
		if (nodes == null || nodes.getLength() == 0) {
			return null;
		}
		else {
			return XMLUtil.INSTANCE.getXMLElementMap(nodes);
		}
	}
	
	/** Checks the file name for any illegal characters, such as quotes
	 * 
	 * @param fileName The file name to check
	 * @return True if no illegal characters were found, false otherwise
	 */
	
	private boolean checkFileName(String fileName) {
		return !(fileName.contains("\"") || fileName.contains("'"));
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
		movie.setName(fileName);
		if (directoryConfig == null ) {
			//dont log, as this is logged previously
		}
		else if (!checkFileName(fileName)) {
			log.warn("Illegal characters in file name {}" , fileName);
		}
		else {
			Map<String, String> movieProperties = getMovieProperties(directoryConfig, file.getName());
			if (movieProperties == null) {
				log.info("No properties for file {} exist", fileName);
			}
			else {
				movie.setName(movieProperties.get("name"));
				movie.setDescription(movieProperties.get("description"));
				movie.setReleaseDate(movieProperties.get("releaseDate"));
			}	
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
