package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

import classes.GLPosition;
import classes.GLSize;
import classes.GLSprite;
import classes.GLSpriteData;
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

					NodeList xList = eElement.getElementsByTagName("x");
					String x = "0";
					if (xList != null && xList.getLength() > 0) {
						x = xList.item(0).getChildNodes().item(0).getNodeValue();
					}

					NodeList yList = eElement.getElementsByTagName("y");
					String y = "0";
					if (yList != null && xList.getLength() > 0) {
						y = xList.item(0).getChildNodes().item(0).getNodeValue();
					}
					data.texturePosition = new GLPosition(Float.parseFloat(x), Float.parseFloat(y));

					NodeList widthList = eElement.getElementsByTagName("width");
					String width = "1";
					if (widthList != null && widthList.getLength() > 0) {
						width = widthList.item(0).getChildNodes().item(0).getNodeValue();
					}

					NodeList heightList = eElement.getElementsByTagName("height");
					String height = "1";
					if (heightList != null && heightList.getLength() > 0) {
						height = heightList.item(0).getChildNodes().item(0).getNodeValue();
					}
					data.textureSize = new GLSize(Float.parseFloat(width), Float.parseFloat(height));
					spriteData.add(data);
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
			if (sprite.size.equals(new GLSize(-1, -1))) {
				sprite.size = new GLSize(texture.getImageWidth(), texture.getImageHeight());
			}

			sprite.textureID = texture.getTextureID();

			int displayList = GL11.glGenLists(1);

			GL11.glNewList(displayList, GL11.GL_COMPILE);

			// RenderSprite(32, 32);

			RenderSubSprite(data);

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

		RenderSprite(32, 32);

		GL11.glEndList();

		sprite.displayLists = displayList;

		return sprite;
	}

	public static void RenderSprite(int width, int height) {
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2i(0, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2i(width, 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2i(width, height);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2i(0, height);

		GL11.glEnd();
	}

	public static void RenderSubSprite(GLSpriteData SpriteData) {

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(SpriteData.texturePosition.x, SpriteData.texturePosition.y);
		GL11.glVertex2f(0, 0);

		GL11.glTexCoord2f(SpriteData.texturePosition.x + SpriteData.textureSize.width, SpriteData.texturePosition.y);
		GL11.glVertex2f(+SpriteData.size.width, 0);

		GL11.glTexCoord2f(SpriteData.texturePosition.x + SpriteData.textureSize.width,
				SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(+SpriteData.size.width, +SpriteData.size.height);

		GL11.glTexCoord2f(SpriteData.texturePosition.x, SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(0, +SpriteData.size.height);

		GL11.glEnd();
	}
}
