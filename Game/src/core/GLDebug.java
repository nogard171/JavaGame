package utils;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

public class GLDebug {
	private static boolean isSetup = false;
	private static boolean enabled = true;

	public static void Setup() {

	}

	public static boolean IsEnabled() {
		return enabled;
	}

	public static void enabled(boolean newEnabled) {
		enabled = newEnabled;
	}

	public static void RenderBackground(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(0, 0, 0, 0.5f);

		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);

		GL11.glEnd();
	}

	public static void RenderString(float x, float y, String text) {

	}

	public static void RenderString(Vector2f position, String text) {
		RenderString(position.GetX(), position.GetY(), text);
	}
}
