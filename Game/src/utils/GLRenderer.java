package utils;

import org.lwjgl.opengl.GL11;

import core.GLPosition;
import core.GLSpriteData;
import game.Game;

public class GLRenderer {
	public static void RenderSprite(GLPosition position, GLSpriteData spriteData) {

		int x = (int) position.getX();
		int y = (int) position.getY();
		float textureX = spriteData.x;
		float textureY = spriteData.y;
		float textureW = spriteData.w;
		float textureH = spriteData.h;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(textureX, textureY);
		GL11.glVertex2i(x, y);
		GL11.glTexCoord2f(textureX + textureW, textureY);
		GL11.glVertex2i(x + 32, y);
		GL11.glTexCoord2f(textureX + textureW, textureY + textureH);
		GL11.glVertex2i(x + 32, y + 32);
		GL11.glTexCoord2f(textureX, textureY + textureH);
		GL11.glVertex2i(x, y + 32);

		GL11.glEnd();
	}
}
