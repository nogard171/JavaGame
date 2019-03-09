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
					int x = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "x"));
					int y = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "y"));
					int w = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "w"));
					int h = Integer.parseInt(GLXMLHandler.getAttribute(spriteNode, "h"));

					GLSpriteData spriteData = new GLSpriteData();

					spriteData.x = (float) x / DataHub.texture.getImageWidth();
					spriteData.y = (float) y / DataHub.texture.getImageHeight();
					spriteData.w = (float) w / DataHub.texture.getImageWidth();
					spriteData.h = (float) h / DataHub.texture.getImageHeight();

					DataHub.spriteData.put(GLSpriteType.getType(name), spriteData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
