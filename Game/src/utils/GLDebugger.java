package utils;

import org.newdawn.slick.Color;
import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

public class GLDebugger {
	private static TrueTypeFont font = null;

	public static void showMessage(String message, int x, int y) {
		showMessage(message, x, y, 12, Color.black);
	}

	public static void showMessage(String message, int x, int y, int fontSize) {
		showMessage(message, x, y, fontSize, Color.black);
	}

	public static void showMessage(String message, int x, int y, int fontSize, Color fontColor) {

		//GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_TEXTURE_2D);

		if (font == null) {
			Font awtFont = new Font("Times New Roman", Font.PLAIN, fontSize);
			font = new TrueTypeFont(awtFont, false);
		}
		GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glVertex2i(x, y);
		GL11.glVertex2i(x + 32, y);
		GL11.glVertex2i(x + 32, y + 32);

		GL11.glEnd();

		if (font != null) {
			font.drawString(x, y, message, fontColor);
		}

		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glDisable(GL11.GL_BLEND);
	}
}
