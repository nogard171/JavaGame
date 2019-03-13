package utils;

import org.lwjgl.opengl.GL11;

import core.GLPosition;
import core.GLSpriteData;
import game.Game;

public class GLRenderer {
	public static void RenderSprite(GLPosition position, GLSpriteData spriteData) {
		
	

		int offsetX = spriteData.ox;
		int offsetY = spriteData.oy;

		int x = (int) position.getX() + offsetX;
		int y = (int) position.getY() + offsetY;
		int width = spriteData.w;
		int height = spriteData.h;
		float textureX = spriteData.tx;
		float textureY = spriteData.ty;
		float textureW = spriteData.tw;
		float textureH = spriteData.th;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(textureX, textureY);
		GL11.glVertex2i(x, y);
		GL11.glTexCoord2f(textureX + textureW, textureY);
		GL11.glVertex2i(x + width, y);
		GL11.glTexCoord2f(textureX + textureW, textureY + textureH);
		GL11.glVertex2i(x + width, y + height);
		GL11.glTexCoord2f(textureX, textureY + textureH);
		GL11.glVertex2i(x, y + height);

		GL11.glEnd();
	}
}
