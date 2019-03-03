package utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GLXMLHandler {
	public static Document getXMLDoc(String filename) {
		Document doc = null;
		try {
			File xmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	public static NodeList getRootNode(Document doc, String tag) {

		NodeList nList = doc.getElementsByTagName(tag);
		return nList;
	}

	public static NodeList getNodes(Node node, String tag) {

		NodeList nList = null;

		if (node.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) node;

			nList = eElement.getElementsByTagName(tag);
		}
		return nList;
	}

	public static String getAttribute(Node node, String attributeName) {
		String attributeValue = "";
		if (node.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) node;

			attributeValue = eElement.getAttribute(attributeName);
		}
		return attributeValue;
	}
}
