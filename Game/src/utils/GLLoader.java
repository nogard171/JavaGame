package utils;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import classes.GLSprite;
import classes.GLSpriteData;

public class GLLoader {
	public static GLSprite getSprite(String filename) {
		GLSprite sprite = new GLSprite();
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tiles.png"));
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
