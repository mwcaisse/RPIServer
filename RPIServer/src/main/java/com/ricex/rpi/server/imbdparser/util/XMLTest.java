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
		
		//NodeList nodes = util.getXMLObject("/videos/video[@filename='Airplane.avi']/*", document);		
		NodeList nodes = util.getXMLObject("/videos/video[@filename='Transporter.mkv']/*", document);	
		
		for (int i=0;i<nodes.getLength();i++) {
			Node node = nodes.item(i);
			System.out.println(node.getNodeName() + " " + node.getFirstChild().getNodeValue());
		}		
	
	}
}
