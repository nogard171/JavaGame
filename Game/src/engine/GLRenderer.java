package engine;

import org.lwjgl.opengl.GL11;

import classes.GLChunk;
import classes.GLSpriteData;

public class GLRenderer {

	public static void RenderChunk(GLChunk chunk) {
		if (chunk.chunkDisplayList == -1) {
			chunk.chunkDisplayList = GL11.glGenLists(1);

			GL11.glNewList(chunk.chunkDisplayList, GL11.GL_COMPILE);
		}
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
		GL11.glVertex2f(SpriteData.size.width, 0);

		GL11.glTexCoord2f(SpriteData.texturePosition.x + SpriteData.textureSize.width,
				SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(SpriteData.size.width, SpriteData.size.height);

		GL11.glTexCoord2f(SpriteData.texturePosition.x, SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(0, SpriteData.size.height);

		GL11.glEnd();
	}
}
