package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import classes.GLMaterialData;
import classes.GLModel;
import classes.GLModelData;
import classes.GLPosition;
import classes.GLSize;
import classes.GLSprite;
import classes.GLSpriteData;
import engine.GLRenderer;
import game.GLData;

public class GLLoader {

	public static void loadSprites() {
		ArrayList<GLSpriteData> spriteData = new ArrayList<GLSpriteData>();
		File file = new File("resources/data/sprites.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);
			NodeList sprites = document.getElementsByTagName("sprite");
			for (int s = 0, size = sprites.getLength(); s < size; s++) {
				Node nNode = sprites.item(s);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					GLSpriteData data = new GLSpriteData();

					String spriteName = nNode.getAttributes().getNamedItem("name").getNodeValue();
					data.name = spriteName;

					String spriteWidth = null;
					String spriteHeight = null;
					if (nNode.getAttributes().getLength() >= 3) {
						spriteWidth = nNode.getAttributes().getNamedItem("width").getNodeValue();
						spriteHeight = nNode.getAttributes().getNamedItem("height").getNodeValue();
					}
					if (spriteWidth == null && spriteHeight == null) {
						spriteWidth = "-1";
						spriteHeight = "-1";
					}
					data.size = new GLSize(Float.parseFloat(spriteWidth), Float.parseFloat(spriteHeight));

					NodeList srcList = eElement.getElementsByTagName("src");
					String src = srcList.item(0).getChildNodes().item(0).getNodeValue();
					data.source = src;

					if (new File(data.source).exists()) {

						Texture a = null;
						try {
							a = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(data.source));
						} catch (IOException e) {
							e.printStackTrace();
						}

						NodeList xList = eElement.getElementsByTagName("x");
						float x = 0;
						if (xList != null && xList.getLength() > 0) {
							x = Float.parseFloat(xList.item(0).getChildNodes().item(0).getNodeValue())
									/ a.getTextureWidth();
						}

						NodeList yList = eElement.getElementsByTagName("y");
						float y = 0;
						if (yList != null && xList.getLength() > 0) {
							y = Float.parseFloat(yList.item(0).getChildNodes().item(0).getNodeValue())
									/ a.getTextureHeight();
						}
						data.texturePosition = new GLPosition(x, y);

						NodeList widthList = eElement.getElementsByTagName("width");
						float width = 1;
						if (widthList != null && widthList.getLength() > 0) {
							width = Float.parseFloat(widthList.item(0).getChildNodes().item(0).getNodeValue())
									/ a.getTextureWidth();
						}

						NodeList heightList = eElement.getElementsByTagName("height");
						float height = 1;
						if (heightList != null && heightList.getLength() > 0) {
							height = Float.parseFloat(heightList.item(0).getChildNodes().item(0).getNodeValue())
									/ a.getTextureHeight();
						}
						data.textureSize = new GLSize(width, height);
						spriteData.add(data);
					}
				}
			}

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
		compileSprites(spriteData);
	}

	public static void compileSprites(ArrayList<GLSpriteData> spriteData) {
		for (GLSpriteData data : spriteData) {
			GLSprite sprite = new GLSprite();
			Texture texture = null;
			try {
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(data.source));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (data.size.width == -1 || data.size.height == -1) {

				data.size = new GLSize(texture.getTextureWidth(), texture.getTextureHeight());
			}

			sprite.textureID = texture.getTextureID();

			int displayList = GL11.glGenLists(1);

			GL11.glNewList(displayList, GL11.GL_COMPILE);

			// RenderSprite(32, 32);

			new GLRenderer().RenderSubSprite(data);

			GL11.glEndList();

			sprite.displayLists = displayList;

			new GLData().sprites.put(data.name, sprite);
		}
	}

	public static GLSprite getSprite(String filename) {
		GLSprite sprite = new GLSprite();
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/sprites/tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sprite.textureID = texture.getTextureID();

		int displayList = GL11.glGenLists(1);

		GL11.glNewList(displayList, GL11.GL_COMPILE);

		new GLRenderer().RenderSprite(32, 32);

		GL11.glEndList();

		sprite.displayLists = displayList;

		return sprite;
	}

	public static ArrayList<GLModel> getModelList() {
		ArrayList<GLModel> models = new ArrayList<GLModel>();
		File file = new File("resources/data/models.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);
			NodeList sprites = document.getElementsByTagName("model");
			for (int s = 0, size = sprites.getLength(); s < size; s++) {
				Node nNode = sprites.item(s);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					GLModelData data = new GLModelData();

					String modelName = nNode.getAttributes().getNamedItem("name").getNodeValue();
					String matFilename = nNode.getAttributes().getNamedItem("mat").getNodeValue();
					String objFilename = nNode.getAttributes().getNamedItem("obj").getNodeValue();

					GLModelData modelData = getModelData(objFilename);

					GLMaterialData materialData = getMaterialData(matFilename);

					GLModel model = new GLModel();

					model.name = modelName;

					model.textureID = materialData.textureID;

					int displayList = GL11.glGenLists(1);

					GL11.glNewList(displayList, GL11.GL_COMPILE);

					new GLRenderer().RenderModel(modelData, materialData);

					GL11.glEndList();

					model.displayList = displayList;

					models.add(model);
				}
			}

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
		return models;
	}

	public static void loadModels() {
		ArrayList<GLModel> models = getModelList();

		HashMap<String, GLModel> dataModels = new HashMap<String, GLModel>();

		for (GLModel model : models) {
			System.out.println(model.name);
			dataModels.put(model.name, model);
		}

		new GLData().models = dataModels;
	}

	public static GLModelData getModelData(String modelFilename) {
		GLModelData modelData = new GLModelData();

		ArrayList<GLPosition> newVectors = new ArrayList<GLPosition>();
		ArrayList<Byte> newIndices = new ArrayList<Byte>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(modelFilename));

			String line = br.readLine();

			while (line != null) {

				if (line != null) {
					String[] data = line.split(" ");

					String constructor = data[0];

					if (constructor.startsWith("v")) {
						newVectors.add(new GLPosition(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
					}
					if (constructor.startsWith("i")) {
						newIndices.add(Byte.parseByte(data[1]));
						newIndices.add(Byte.parseByte(data[2]));
						newIndices.add(Byte.parseByte(data[3]));

					}
				}
				line = br.readLine();

			}
			if (br != null) {
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		modelData.vectors = newVectors;
		modelData.indices = newIndices;

		return modelData;
	}

	public static GLMaterialData getMaterialData(String materialFilename) {
		GLMaterialData materialData = new GLMaterialData();

		ArrayList<GLPosition> newTextureVectors = new ArrayList<GLPosition>();
		ArrayList<Byte> newTextureIndices = new ArrayList<Byte>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(materialFilename));

			String line = br.readLine();

			while (line != null) {

				if (line != null) {
					String[] data = line.split(" ");

					String constructor = data[0];

					if (constructor.startsWith("tv")) {
						newTextureVectors.add(new GLPosition(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
					}
					if (constructor.startsWith("ti")) {
						newTextureIndices.add(Byte.parseByte(data[1]));
						newTextureIndices.add(Byte.parseByte(data[2]));
						newTextureIndices.add(Byte.parseByte(data[3]));

					}
					if (constructor.startsWith("src")) {
						Texture texture = null;
						try {
							texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(data[1]));
						} catch (IOException e) {
							e.printStackTrace();
						}

						materialData.textureID = texture.getTextureID();
					}
				}
				line = br.readLine();
			}
			if (br != null) {
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		materialData.textureVectors = newTextureVectors;
		materialData.textureIndices = newTextureIndices;

		return materialData;
	}
}
