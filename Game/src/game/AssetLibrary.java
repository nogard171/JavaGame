package game;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static game.AssetLoader.*;

public class AssetLibrary {
	private static HashMap<String, Material> materials = new HashMap<String, Material>();

	public static Material getMaterial(String materialName) {
		Material mat = null;

		if (materials.containsKey(materialName)) {
			mat = materials.get(materialName);
		}
		return mat;
	}

	public static void loadAssetLibrary() {
		try {

			File fXmlFile = new File("resources/data/assets.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("asset");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					loadAsset(eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadAsset(Element eElement) {
		String name = eElement.getAttribute("name");
		String type = eElement.getAttribute("type").toLowerCase();
		String filename = eElement.getAttribute("file");

		switch (type) {
		case "material":
			Material mat = loadMaterial(filename);
			System.out.println("test: " + mat);
			materials.put(name, mat);
			break;
		}

	}

}
