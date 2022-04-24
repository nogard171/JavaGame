package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import classes.ItemData;
import classes.ItemDrop;
import classes.RawMaterial;
import classes.RawModel;
import classes.ResourceData;
import classes.ResourceState;
import classes.TextureType;
import data.AssetData;
import data.EngineData;
import data.Settings;

public class Loader {

	public static void load() {
		loadResources();
		loadItems();
		loadMaterials();
		loadTextures();
		EngineData.dataLoaded = false;
	}

	private static void loadItems() {
		AssetData.itemData.clear();
		try {
			File fXmlFile = new File(Settings.itemsFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList itemNodes = doc.getElementsByTagName("item");

			for (int temp = 0; temp < itemNodes.getLength(); temp++) {
				Node itemNode = itemNodes.item(temp);
				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element itemElement = (Element) itemNode;
					ItemData data = new ItemData();
					String itemName = itemElement.getAttribute("name");
					data.type = TextureType.getType(itemName);
					for (int i = 0; i < itemElement.getElementsByTagName("attribute").getLength(); i++) {

						Node dataNodes = itemElement.getElementsByTagName("attribute").item(i);

						if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element dataNode = (Element) dataNodes;

							String attributeName = "";
							if (dataNode.hasAttribute("name")) {
								attributeName = dataNode.getAttribute("name");
							}
							String value = "";
							if (dataNode.hasAttribute("value")) {
								value = dataNode.getAttribute("value");
							}
							data.attributes.put(attributeName, value);
						}
					}
					System.out.println("Dtest" + itemName);
					AssetData.itemData.put(itemName, data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadResources() {
		/*
		 * AssetData.resourceData.put("TREE", new ResourceData(10, new
		 * ArrayList<ItemDrop>() { { add(new ItemDrop("TEST_ITEM")); } }));
		 */

		AssetData.resourceData.clear();
		try {
			File fXmlFile = new File(Settings.resourcesFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList resourceNodes = doc.getElementsByTagName("resource");

			for (int temp = 0; temp < resourceNodes.getLength(); temp++) {

				Node resourceNode = resourceNodes.item(temp);

				if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
					Element resourceElement = (Element) resourceNode;
					ResourceData data = new ResourceData();
					String resourceName = resourceElement.getAttribute("name");
					int newHitPoints = 0;
					if (resourceElement.hasAttribute("hitpoints")) {
						newHitPoints = Integer.parseInt(resourceElement.getAttribute("hitpoints"));
					}
					data.hitpoints = newHitPoints;

					
					for (int s = 0; s < resourceElement.getElementsByTagName("state").getLength(); s++) {

						Node stateNodes = resourceElement.getElementsByTagName("state").item(s);

						if (stateNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element stateNode = (Element) stateNodes;

							String stateName = "";
							if (stateNode.hasAttribute("name")) {
								stateName = stateNode.getAttribute("name");
							}
							String material = "";
							if (stateNode.hasAttribute("material")) {
								material = stateNode.getAttribute("material");
							}
							String model = "";
							if (stateNode.hasAttribute("model")) {
								model = stateNode.getAttribute("model");
							}
							ArrayList<ItemDrop> newItemDrops = new ArrayList<ItemDrop>();
							for (int i = 0; i < stateNode.getElementsByTagName("item").getLength(); i++) {

								Node dataNodes = resourceElement.getElementsByTagName("item").item(i);

								if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

									Element dataNode = (Element) dataNodes;

									String itemName = "";
									if (dataNode.hasAttribute("name")) {
										itemName = dataNode.getAttribute("name");
									}
									int minValue = 0;
									if (dataNode.hasAttribute("min")) {
										minValue = Integer.parseInt(dataNode.getAttribute("min"));
									}

									int maxValue = 0;
									if (dataNode.hasAttribute("max")) {
										maxValue = Integer.parseInt(dataNode.getAttribute("max"));
									}
									newItemDrops.add(new ItemDrop(itemName, minValue, maxValue));
								}
							}
							ResourceState state = new ResourceState();
							state.items = newItemDrops;
							state.material = material;
							state.model = model;
							data.states.put(stateName, state);
						}
					}
					AssetData.resourceData.put(resourceName, data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadMaterials() {
		AssetData.materialData.clear();
		try {
			File fXmlFile = new File(Settings.materialsFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Node texturesNode = doc.getElementsByTagName("materials").item(0);

			if (texturesNode.getNodeType() == Node.ELEMENT_NODE) {

				Element texturesElement = (Element) texturesNode;
				String textureFile = texturesElement.getAttribute("texture_file");
				if (textureFile != null && textureFile != "") {
					AssetData.texture = TextureLoader.getTexture("PNG",
							ResourceLoader.getResourceAsStream(textureFile));
				}
			}
			NodeList textureNodes = doc.getElementsByTagName("material");

			for (int temp = 0; temp < textureNodes.getLength(); temp++) {

				Node textureNode = textureNodes.item(temp);

				if (textureNode.getNodeType() == Node.ELEMENT_NODE) {

					Element textureElement = (Element) textureNode;
					String name = textureElement.getAttribute("name");
					for (int i = 0; i < textureElement.getElementsByTagName("data").getLength(); i++) {

						Node dataNodes = textureElement.getElementsByTagName("data").item(i);

						if (dataNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element dataNode = (Element) dataNodes;

							String materialName = dataNode.getAttribute("file");
							String id = "";
							if (dataNode.hasAttribute("id")) {
								id = dataNode.getAttribute("id");
							}
							System.out.println("Name: " + name + id);
							RawMaterial mat = loadMaterial(materialName);

							AssetData.materialData.put(name + id, mat);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RawMaterial loadMaterial(String filename) {
		RawMaterial mat = new RawMaterial();

		ArrayList<Vector2f> vecs = new ArrayList<Vector2f>();
		ArrayList<Byte> indices = new ArrayList<Byte>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				String[] data = line.split(" ");
				if (!data[0].startsWith("#")) {
					if (data[0].startsWith("tv")) {
						vecs.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
					}
					if (data[0].startsWith("ti")) {
						indices.add(Byte.parseByte(data[1]));
						indices.add(Byte.parseByte(data[2]));
						indices.add(Byte.parseByte(data[3]));
					}
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
		mat.indices = new byte[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			mat.indices[i] = indices.get(i);
		}

		return mat;
	}

	public static void loadTextures() {
		AssetData.modelData.clear();
		try {
			File fXmlFile = new File(Settings.textureFile);
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
						RawModel raw = loadModel(modelFile);

						AssetData.modelData.put(name, raw);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RawModel loadModel(String filename) {
		RawModel raw = new RawModel();

		ArrayList<Vector2f> vecs = new ArrayList<Vector2f>();
		ArrayList<Byte> boundIndices = new ArrayList<Byte>();
		ArrayList<Byte> ind = new ArrayList<Byte>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				String[] data = line.split(" ");
				if (!data[0].startsWith("#")) {

					if (data[0].startsWith("v")) {
						vecs.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
					}
					if (data[0].startsWith("bi")) {
						boundIndices.add(Byte.parseByte(data[1]));
					}
					if (data[0].startsWith("i")) {
						ind.add(Byte.parseByte(data[1]));
						ind.add(Byte.parseByte(data[2]));
						ind.add(Byte.parseByte(data[3]));
					}
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

		if (boundIndices.size() > 0) {
			raw.boundVectors = new Vector2f[boundIndices.size()];
			for (int i = 0; i < boundIndices.size(); i++) {
				raw.boundVectors[i] = raw.vectors[boundIndices.get(i)];
			}
		}

		return raw;
	}

}
