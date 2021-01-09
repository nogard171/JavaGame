package utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import classes.TextureData;
import core.GameData;

public class Loader {
	public static void loadConfig(String file) {
		try (InputStream input = new FileInputStream(file)) {
			GameData.config = new Properties();
			GameData.config.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void loadTextures() {
		try {
			GameData.textureData.clear();
			File fXmlFile = new File(GameData.config.getProperty("assets.texture_data"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Node texturesNode = doc.getElementsByTagName("textures").item(0);

			if (texturesNode.getNodeType() == Node.ELEMENT_NODE) {

				Element texturesElement = (Element) texturesNode;
				String textureFile = texturesElement.getAttribute("file");
				if (textureFile != null && textureFile != "") {
					GameData.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureFile));
				}
			}
			NodeList textureNodes = doc.getElementsByTagName("texture");

			for (int temp = 0; temp < textureNodes.getLength(); temp++) {

				Node textureNode = textureNodes.item(temp);

				if (textureNode.getNodeType() == Node.ELEMENT_NODE) {
					TextureData data = new TextureData();
					Element textureElement = (Element) textureNode;
					String name = textureElement.getAttribute("name");

					Node dataNodes = textureElement.getElementsByTagName("shape").item(0);

					if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

						Element dataNode = (Element) dataNodes;

						int x = Integer.parseInt(dataNode.getAttribute("x"));
						int y = Integer.parseInt(dataNode.getAttribute("y"));
						int w = Integer.parseInt(dataNode.getAttribute("w"));
						int h = Integer.parseInt(dataNode.getAttribute("h"));
						System.out.println("shape: " + x + "," + y + "," + w + "," + h);

						data.shape = new Rectangle(x, y, w, h);

					}

					Node colliderNodes = textureElement.getElementsByTagName("collider").item(0);
					if (colliderNodes != null) {
						if (colliderNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element colliderNode = (Element) colliderNodes;

							int x = Integer.parseInt(colliderNode.getAttribute("x"));
							int y = Integer.parseInt(colliderNode.getAttribute("y"));
							int w = Integer.parseInt(colliderNode.getAttribute("w"));
							int h = Integer.parseInt(colliderNode.getAttribute("h"));

							System.out.println("collider: " + x + "," + y + "," + w + "," + h);

							data.collider = new Rectangle(x, y, w, h);
						}
					}
					GameData.textureData.put(name, data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
