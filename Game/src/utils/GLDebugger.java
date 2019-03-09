package utils;

import org.newdawn.slick.Color;
import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

public class GLDebugger {
	private static TrueTypeFont font = null;

	public static void showMessage(String message, int x, int y) {
		showMessage(message, x, y, 12, Color.black);
	}

	public static void showMessage(String message, int x, int y, int fontSize) {
		showMessage(message, x, y, fontSize, Color.black);
	}

	public static void showBackground(int x, int y, int width, int height) {
		GL11.glColor4f(0, 0, 0, 0.5f);
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glVertex2i(x, y);
		GL11.glVertex2i(x + width, y);
		GL11.glVertex2i(x + width, y + height);

		GL11.glVertex2i(x + width, y + height);
		GL11.glVertex2i(x, y + height);
		GL11.glVertex2i(x, y);

		GL11.glEnd();
	}

	public static void showMessage(String message, int x, int y, int fontSize, Color fontColor) {

		if (font == null) {
			Font awtFont = new Font("Times New Roman", Font.PLAIN, fontSize);
			font = new TrueTypeFont(awtFont, false);
		}

		if (font != null) {
			font.drawString(x, y, message, fontColor);
		}
		TextureImpl.bindNone();
	}
}
