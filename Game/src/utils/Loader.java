package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import classes.GLTextureData;

public class Loader {
	public HashMap<String, GLTextureData> loadTextureData() {
		HashMap<String, GLTextureData> textureData = new HashMap<String, GLTextureData>();
		System.out.println("test");
		try {
			File file = new File("res/dat/textureData.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);

			NodeList children = document.getElementsByTagName("textureData");
			int childCount = children.getLength();

			for (int c = 0; c < childCount; c++) {
				Node child = children.item(c);
				if (child != null) {
					if (child.hasAttributes()) {
						NamedNodeMap childAttributes = child.getAttributes();
						if (childAttributes != null) {
							String name = childAttributes.getNamedItem("name").getTextContent();

							float x = Float.parseFloat(childAttributes.getNamedItem("x").getTextContent());
							float y = Float.parseFloat(childAttributes.getNamedItem("y").getTextContent());
							float width = Float.parseFloat(childAttributes.getNamedItem("width").getTextContent());
							float height = Float.parseFloat(childAttributes.getNamedItem("height").getTextContent());
							textureData.put(name, new GLTextureData(x, y, width, height));
						}
					}
				}
			}
			//

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return textureData;
	}
}
