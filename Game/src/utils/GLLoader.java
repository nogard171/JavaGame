package utils;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.GLSpriteData;
import core.GLSpriteType;
import game.DataHub;

public class GLLoader {
	GLXMLHandler xmlHandler;

	public static void loadSprites() {
		try {
			String spriteSheet = "";

			String spriteDataFile = "resources/data/sprites.xml";
			Document doc = GLXMLHandler.getXMLDoc(spriteDataFile);
			NodeList spritesList = GLXMLHandler.getRootNode(doc, "sprites");
			for (int temp = 0; temp < spritesList.getLength(); temp++) {
				Node spritesNode = spritesList.item(temp);
				spriteSheet = GLXMLHandler.getAttribute(spritesNode, "spriteSheet");

				if (spriteSheet != null || spriteSheet != "") {

					DataHub.texture = TextureLoader.getTexture("PNG",
							ResourceLoader.getResourceAsStream("resources/sprites/" + spriteSheet));
				}

				NodeList spriteList = GLXMLHandler.getNodes(spritesNode, "sprite");
				for (int temp2 = 0; temp2 < spriteList.getLength(); temp2++) {
					Node spriteNode = spriteList.item(temp2);
					String name = GLXMLHandler.getAttribute(spriteNode, "name");

					GLSpriteData spriteData = new GLSpriteData();

					NodeList spriteDataList = GLXMLHandler.getNodes(spriteNode, "data");
					for (int temp3 = 0; temp3 < spriteDataList.getLength(); temp3++) {
						Node spriteDataNode = spriteDataList.item(temp3);
						int x = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "x"));
						int y = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "y"));
						int tw = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "w"));
						int th = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "h"));

						spriteData.tx = (float) x / DataHub.texture.getImageWidth();
						spriteData.ty = (float) y / DataHub.texture.getImageHeight();
						spriteData.tw = (float) tw / DataHub.texture.getImageWidth();
						spriteData.th = (float) th / DataHub.texture.getImageHeight();

						int w = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "w"));
						int h = Integer.parseInt(GLXMLHandler.getAttribute(spriteDataNode, "h"));

						spriteData.w = w;
						spriteData.h = h;
					}

					NodeList spriteOffsetList = GLXMLHandler.getNodes(spriteNode, "offset");
					for (int temp3 = 0; temp3 < spriteOffsetList.getLength(); temp3++) {
						Node spriteOffsetNode = spriteOffsetList.item(temp3);
						int x = Integer.parseInt(GLXMLHandler.getAttribute(spriteOffsetNode, "x"));
						int y = Integer.parseInt(GLXMLHandler.getAttribute(spriteOffsetNode, "y"));

						spriteData.ox = x;
						spriteData.oy = y;
					}

					DataHub.spriteData.put(GLSpriteType.getType(name), spriteData);
					/*
					 * int x = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "x")); int y =
					 * Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "y")); int tw =
					 * Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "w")); int th =
					 * Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "h"));
					 * 
					 * int w = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "w")); int h =
					 * Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "h"));
					 * 
					 * 
					 * 
					 * spriteData.tx = (float) x / DataHub.texture.getImageWidth(); spriteData.ty =
					 * (float) y / DataHub.texture.getImageHeight(); spriteData.tw = (float) tw /
					 * DataHub.texture.getImageWidth(); spriteData.th = (float) th /
					 * DataHub.texture.getImageHeight();
					 * 
					 * spriteData.w = w; spriteData.h = h;
					 * 
					 * DataHub.spriteData.put(GLSpriteType.getType(name), spriteData);
					 */

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
