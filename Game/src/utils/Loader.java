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

import classes.MaterialData;
import classes.ModelData;
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

	public static void loadMaterials() {
		try {
			GameData.materialData.clear();
			File fXmlFile = new File(GameData.config.getProperty("assets.material_data"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Node texturesNode = doc.getElementsByTagName("materials").item(0);

			if (texturesNode.getNodeType() == Node.ELEMENT_NODE) {

				Element texturesElement = (Element) texturesNode;
				String textureFile = texturesElement.getAttribute("texture_file");
				if (textureFile != null && textureFile != "") {
					GameData.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureFile));
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
						GameData.materialData.put(name, mat);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MaterialData loadMaterial(String filename) {
		MaterialData mat = new MaterialData();

		ArrayList<Vector2f> vecs = new ArrayList<Vector2f>();
		ArrayList<Byte> inds = new ArrayList<Byte>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				String[] data = line.split(" ");
				if (data[0].contains("tv")) {
					vecs.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));

				}
				if (data[0].contains("ti")) {
					inds.add(Byte.parseByte(data[1]));
					inds.add(Byte.parseByte(data[2]));
					inds.add(Byte.parseByte(data[3]));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mat.vectors = new Vector2f[vecs.size()];
		for (int i = 0; i < vecs.size(); i++) {
			mat.vectors[i] = vecs.get(i);
		}
		mat.indices = new byte[inds.size()];
		for (int i = 0; i < inds.size(); i++) {
			mat.indices[i] = inds.get(i);
		}

		return mat;
	}

	public static void loadModels() {
		try {
			GameData.modelData.clear();
			File fXmlFile = new File(GameData.config.getProperty("assets.model_data"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Node texturesNode = doc.getElementsByTagName("models").item(0);
			NodeList textureNodes = doc.getElementsByTagName("model");

			for (int temp = 0; temp < textureNodes.getLength(); temp++) {

				Node textureNode = textureNodes.item(temp);

				if (textureNode.getNodeType() == Node.ELEMENT_NODE) {

					Element textureElement = (Element) textureNode;
					String name = textureElement.getAttribute("name");

					Node dataNodes = textureElement.getElementsByTagName("data").item(0);

					if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

						Element dataNode = (Element) dataNodes;

						String modelFile = dataNode.getAttribute("file");
						ModelData raw = loadModel(modelFile);

						GameData.modelData.put(name, raw);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ModelData loadModel(String filename) {
		ModelData raw = new ModelData();

		ArrayList<Vector2f> vecs = new ArrayList<Vector2f>();
		ArrayList<Byte> ind = new ArrayList<Byte>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				String[] data = line.split(" ");
				if (data[0].contains("v")) {
					vecs.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));

				}
				if (data[0].contains("i")) {
					ind.add(Byte.parseByte(data[1]));
					ind.add(Byte.parseByte(data[2]));
					ind.add(Byte.parseByte(data[3]));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		raw.vectors = new Vector2f[vecs.size()];
		for (int i = 0; i < vecs.size(); i++) {
			raw.vectors[i] = vecs.get(i);
		}
		raw.indices = new byte[ind.size()];
		for (int i = 0; i < ind.size(); i++) {
			raw.indices[i] = ind.get(i);
		}

		return raw;
	}
}
