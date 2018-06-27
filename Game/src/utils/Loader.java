package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import classes.Sprite;

public class Loader {
	private static int textureX = 0;
	private static int textureY = 0;

	public static Sprite loadSprite(String spriteFile, int width, int height) {
		Sprite newSprite = null;

		Texture texture = loadTexture("resources/sprites/" + spriteFile);

		textureX = texture.getTextureWidth() / width;
		textureY = texture.getTextureHeight() / height;

		newSprite = new Sprite(textureX, textureY);

		newSprite.setTextureID(texture.getTextureID());

		for (int x = 0; x < textureX; x++) {
			for (int y = 0; y < textureY; y++) {
				int displayListID = GL11.glGenLists(1);

				GL11.glNewList(displayListID, GL11.GL_COMPILE);

				renderSprite(x, y, 32, 32);

				GL11.glEndList();

				newSprite.setFrame(displayListID, x, y);
			}
		}

		return newSprite;
	}

	private static void renderSprite(int x, int y, int width, int height) {

		float textureStepX = (float) 1 / (float) textureX;
		float textureStepY = (float) 1 / (float) textureY;

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(textureStepX * x, textureStepY * y);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(textureStepX * (x + 1), textureStepY * y);
		GL11.glVertex2i(width, 0);
		GL11.glTexCoord2f(textureStepX * (x + 1), textureStepY * (y + 1));
		GL11.glVertex2i(width, height);
		GL11.glTexCoord2f(textureStepX * x, textureStepY * (y + 1));
		GL11.glVertex2i(0, height);

		GL11.glEnd();
	}

	public static Texture loadTexture(String filename) {
		Texture texture = null;

		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return texture;
	}
}
