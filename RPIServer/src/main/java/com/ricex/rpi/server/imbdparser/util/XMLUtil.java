package com.ricex.rpi.server.imbdparser.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** XML Utility for reading data using XPath from XML files
 * 
 * @author Mitchell Caisse
 *
 */

public enum XMLUtil {
	                                     
	/** The singleton instance of this class */
	INSTANCE;                             
	
	/** The document factory used to build documents */
	private DocumentBuilderFactory documentFactory;
	
	/**The factory to create new XPaths */
	private XPathFactory xpathFactory;
	
	private XMLUtil() {
		documentFactory = DocumentBuilderFactory.newInstance();
		xpathFactory = XPathFactory.newInstance();
	}
	
	/** Returns teh XML Document representing the given xml file
	 * 
	 * @param xmlFile The xml file to parse the document for
	 * @return the xml document, or null if any errors occured
	 */
	
	public Document getXMLDocument(File xmlFile) {
		Document xmlDocument = null;
		try {
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			xmlDocument = builder.parse(xmlFile);
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return xmlDocument;
	}
	
	/** Returns the object returned from executing the expression on the xml document
	 * 
	 * @param expression The expression to execute
	 * @param document The document to execute the expression on
	 * @return The resulting object of the expression
	 */
	
	public NodeList getXMLObject(String expression, Document document) {
		NodeList results = null;
		try {			
			XPath xPath = xpathFactory.newXPath();
			XPathExpression expr = xPath.compile(expression);
			results = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {	
			e.printStackTrace();
		}
		
		return results;
	}
	
}
