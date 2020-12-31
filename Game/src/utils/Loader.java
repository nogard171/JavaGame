package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.GameData;

public class Loader {
	public static void loadConfig(String file)
	{
		try (InputStream input = new FileInputStream(file)) {

            GameData.config = new Properties();

            // load a properties file
            GameData.config.load(input);

            // get the property value and print it out
            System.out.println( GameData.config.getProperty("db.url"));
            System.out.println( GameData.config.getProperty("db.user"));
            System.out.println( GameData.config.getProperty("db.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	/*
	public static void loadTexture() {
			try {
				File fXmlFile = new File(GameData.config.getProperty("texture"));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				doc.getDocumentElement().normalize();

				Node texturesNode = doc.getElementsByTagName("materials").item(0);

				if (texturesNode.getNodeType() == Node.ELEMENT_NODE) {

					Element texturesElement = (Element) texturesNode;
					String textureFile = texturesElement.getAttribute("texture_file");
					if (textureFile != null && textureFile != "") {
						WorldData.texture = TextureLoader.getTexture("PNG",
								ResourceLoader.getResourceAsStream(textureFile));
					}
				}
				NodeList textureNodes = doc.getElementsByTagName("material");

				for (int temp = 0; temp < textureNodes.getLength(); temp++) {

					Node textureNode = textureNodes.item(temp);

					if (textureNode.getNodeType() == Node.ELEMENT_NODE) {
						MaterialData mat = null;
						Element textureElement = (Element) textureNode;
						String name = textureElement.getAttribute("name");

						Node dataNodes = textureElement.getElementsByTagName("data").item(0);

						if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element dataNode = (Element) dataNodes;

							String materialName = dataNode.getAttribute("file");

							mat = loadMaterial(materialName);

						}
						Node offsetNodes = textureElement.getElementsByTagName("offset").item(0);
						if (offsetNodes != null) {
							if (offsetNodes.getNodeType() == Node.ELEMENT_NODE) {

								Element offsetNode = (Element) offsetNodes;

								int offset_X = Integer.parseInt(offsetNode.getAttribute("x"));
								int offset_Y = Integer.parseInt(offsetNode.getAttribute("y"));

								mat.offset = new Vector2f(offset_X, offset_Y);
							}
						}
						if (mat != null) {
							UIData.materialData.put(name, mat);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
}
