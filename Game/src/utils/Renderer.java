package utils;

import java.awt.Font;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import core.GameData;

public class Renderer {

	public static int beginBatch(int mode) {
		int listID = GL11.glGenLists(1);
		GL11.glNewList(listID, GL11.GL_COMPILE);
		GL11.glBegin(mode);

		return listID;
	}

	public static void endBatch() {
		GL11.glEnd();
		GL11.glEndList();
	}

	public static void beginDraw(int mode) {
		GL11.glBegin(mode);
	}

	public static void endDraw() {
		GL11.glEnd();
	}

	public static void drawText(float x, float y, String t, int s, Color c) {
		TrueTypeFont f = GameData.fonts.get(s);
		if (f == null) {
			Font awtFont = new Font("Times New Roman", Font.BOLD, s);
			f = new TrueTypeFont(awtFont, false);
			GameData.fonts.put(s, f);
		}
		TextureImpl.bindNone();
		f.drawString(x, y, t, c);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GameData.renderCount++;
	}

	public static void drawQuad(float x, float y, int w, int h, Color color) {
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w, y + h);
		GL11.glVertex2f(x, y + h);
		GameData.renderCount++;
	}

	public static void drawBatch(int batchId) {
		GL11.glCallList(batchId);
		GameData.renderCount++;
	}
}
