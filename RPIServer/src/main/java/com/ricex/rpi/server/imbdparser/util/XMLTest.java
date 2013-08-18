package com.ricex.rpi.server.imbdparser.util;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLTest {

	
	public static void main(String[] args) {
		XMLUtil util = XMLUtil.INSTANCE;
	
		File xmlFile = new File("E:\\Video\\Movies\\rpiplayer.xml");
		
		Document document = util.getXMLDocument(xmlFile);
		
		NodeList nodes = util.getXMLObject("/videos/video[@filename='Airplane.avi']/name", document);		
		System.out.println("NodeName: name value: " + nodes.item(0).getFirstChild().getNodeValue());
		
		nodes = util.getXMLObject("/videos/video[@filename='Airplane.avi']/description", document);		
		System.out.println("NodeName: name value: " + nodes.item(0).getFirstChild().getNodeValue());
		
		nodes = util.getXMLObject("/videos/video[@filename='Airplane.avi']/year", document);		
		System.out.println("NodeName: year value: " + nodes.item(0).getFirstChild().getNodeValue());
		
		
	
	}
}
