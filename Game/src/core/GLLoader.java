package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import game.Main;

public class GLLoader {
	public static void loadSprites(String filename) {

		ArrayList<GLSpriteData> spriteData = new ArrayList<GLSpriteData>();

		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sprite");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					GLSpriteData newSpriteData = new GLSpriteData();

					Element eElement = (Element) nNode;
					String newName = eElement.getAttribute("name");
					System.out.println("Name: " + newName);

					newSpriteData.name = newName;
					NodeList nList2 = eElement.getElementsByTagName("size");
					Node nNode2 = nList2.item(0);
					if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement2 = (Element) nNode2;
						int sizeWidth = Integer.parseInt(eElement2.getAttribute("width"));
						int sizeHeight = Integer.parseInt(eElement2.getAttribute("height"));

						newSpriteData.size = new GLSize(sizeWidth, sizeHeight);
					}

					NodeList nList3 = eElement.getElementsByTagName("texture");
					Node nNode3 = nList3.item(0);
					if (nNode3.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement2 = (Element) nNode3;
						float texX = Float.parseFloat(eElement2.getAttribute("x")) / Main.texture.getTextureWidth();
						float texY = Float.parseFloat(eElement2.getAttribute("y")) / Main.texture.getTextureHeight();
						float texWidth = Float.parseFloat(eElement2.getAttribute("width"))
								/ Main.texture.getTextureWidth();
						float texHeight = Float.parseFloat(eElement2.getAttribute("height"))
								/ Main.texture.getTextureHeight();
						newSpriteData.textureData = new Vector4f(texX, texY, texWidth, texHeight);
					}
					spriteData.add(newSpriteData);
				}
			}

		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (GLSpriteData singleSpriteData : spriteData) {
			Main.sprites.put(singleSpriteData.name.toUpperCase(), singleSpriteData);
		}
	}
}