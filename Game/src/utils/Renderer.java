package utils;

import java.awt.Font;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import classes.TextureData;
import core.GameData;

public class Renderer {
	private static boolean intersectCheck = true;
	private static int tempBatch = -1;

	public static void beginBatch(int mode) {
		tempBatch = GL11.glGenLists(1);
		GL11.glNewList(tempBatch, GL11.GL_COMPILE);
		GL11.glBegin(mode);
		intersectCheck = false;
	}

	public static int endBatch() {
		GL11.glEnd();
		GL11.glEndList();
		intersectCheck = true;
		return tempBatch;
	}

	public static void beginDraw(int mode) {
		GL11.glBegin(mode);
	}

	public static void endDraw() {
		GL11.glEnd();
	}

	public static void drawText(float x, float y, String t, int s, Color c) {
		drawText(x, y, t, s, c, intersectCheck);
	}

	public static void drawText(float x, float y, String t, int s, Color c, boolean intersectCheck) {

		boolean validDraw = true;
		if (intersectCheck) {
			if (!GameData.view.intersects(x, y, t.length() * 10, s)) {
				validDraw = false;
			}
		}
		if (validDraw) {
			TrueTypeFont f = GameData.fonts.get(s);
			if (f == null) {
				Font awtFont = new Font("Times New Roman", Font.PLAIN, s);
				f = new TrueTypeFont(awtFont, false);
				GameData.fonts.put(s, f);
			}
			TextureImpl.bindNone();
			f.drawString(x, y, t, c);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			GameData.renderCount++;
		}
	}

	public static void drawQuad(float x, float y, int w, int h, Color color, boolean intersectCheck) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		boolean validDraw = true;
		if (intersectCheck) {
			if (!GameData.view.intersects(x, y, w, h)) {
				validDraw = false;
			}
		}
		if (validDraw) {
			Renderer.beginDraw(GL11.GL_QUADS);
			GL11.glColor4f(color.r, color.g, color.b, color.a);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + w, y);
			GL11.glVertex2f(x + w, y + h);
			GL11.glVertex2f(x, y + h);
			GL11.glEnd();
			GameData.renderCount++;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawQuad(float x, float y, int w, int h, Color color) {
		drawQuad(x, y, w, h, color, intersectCheck);
	}

	public static void drawQuad(int x, int y, int w, int h, Color color) {
		drawQuad(x, y, w, h, color, intersectCheck);
	}

	public static void drawBatch(int batchId) {
		GL11.glCallList(batchId);
		GameData.renderCount++;
	}

	public static int getBatchID() {
		int ID = tempBatch;
		tempBatch = -1;
		return ID;
	}

	public static void renderTexture(float x, float y, String texture) {
		renderTexture(x, y, texture, Color.white, intersectCheck);
	}

	public static void renderTexture(float x, float y, String texture, boolean intersectCheck) {
		renderTexture(x, y, texture, Color.white, intersectCheck);
	}

	public static void renderTexture(float x, float y, String texture, Color c, boolean intersectCheck) {
		boolean validDraw = true;
		if (intersectCheck) {
			if (!GameData.view.intersects(x, y, 32, 32)) {
				validDraw = false;
			}
		}
		if (validDraw) {

			TextureData data = GameData.textureData.get(texture);
			if (data != null) {

				GL11.glColor4f(c.r, c.g, c.b, c.a);
				float texX = (float) data.shape.x / (float) GameData.texture.getImageWidth();
				float texY = (float) data.shape.y / (float) GameData.texture.getImageHeight();

				GL11.glTexCoord2f(texX, texY);
				GL11.glVertex2f(x, y);

				texX = ((float) data.shape.x + (float) data.shape.width) / (float) GameData.texture.getImageWidth();

				GL11.glTexCoord2f(texX, texY);
				GL11.glVertex2f(x + data.shape.width, y);

				texX = ((float) data.shape.x + (float) data.shape.width) / (float) GameData.texture.getImageWidth();
				texY = ((float) data.shape.y + (float) data.shape.height) / (float) GameData.texture.getImageHeight();

				GL11.glTexCoord2f(texX, texY);
				GL11.glVertex2f(x + data.shape.width, y + data.shape.height);

				texX = (float) data.shape.x / (float) GameData.texture.getImageWidth();

				GL11.glTexCoord2f(texX, texY);
				GL11.glVertex2f(x, y + data.shape.height);

				GameData.renderCount++;
			}

		}
	}
}
