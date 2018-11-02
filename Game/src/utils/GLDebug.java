package utils;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import classes.GLPosition;
import classes.GLQuadData;

public class GLDebug {
	public static GLQuadData quadData = new GLQuadData();
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

		GLHandler.SetRenderColor(0, 0, 0, 0.5f);
		quadData.setName("debug");
		GLPosition[] vectors = { new GLPosition(x, y), new GLPosition(x + width, y),
				new GLPosition(x + width, y + height), new GLPosition(x, y + height) };
		quadData.SetVectors(vectors);

		byte[] indices = { 0, 1, 2, 3 };
		quadData.SetIndices(indices);

		GLHandler.RenderQuad(quadData);

	}

	public static void RenderString(float x, float y, String text) {

		if (!isSetup) {
			Setup();
			isSetup = true;
		}

		GLHandler.RenderString(x, y, text, Color.white);

	}

	public static void RenderString(GLPosition position, String text) {
		RenderString(position.GetX(), position.GetY(), text);
	}
}
