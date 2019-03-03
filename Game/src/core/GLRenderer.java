package core;

import org.lwjgl.opengl.GL11;

public class GLRenderer {
	public static void RenderSprite(GLSpriteData spriteData) {

		float textureX = spriteData.x;
		float textureY = spriteData.y;
		float textureW = spriteData.w;
		float textureH = spriteData.h;

		//GL11.glColor3f(1, 0, 0);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		//GL11.glTexCoord2f(textureX, textureY);
		GL11.glVertex2i(0, 0);
		//GL11.glTexCoord2f(textureX + textureW, textureY);
		GL11.glVertex2i(32, 0);
		//GL11.glTexCoord2f(textureX + textureW, textureY + textureH);
		GL11.glVertex2i(32, 32);

		//GL11.glTexCoord2f(textureX + textureW, textureY + textureH);
		GL11.glVertex2i(32, 32);
		//GL11.glTexCoord2f(textureX, textureY + textureH);
		GL11.glVertex2i(0, 32);
		//GL11.glTexCoord2f(textureX, textureY);
		GL11.glVertex2i(0, 0);

		GL11.glEnd();

	}
}
