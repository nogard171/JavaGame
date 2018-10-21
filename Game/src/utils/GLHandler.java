package utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import classes.GLPosition;
import classes.GLQuadData;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.util.HashMap;

import engine.GLDisplay;

public class GLHandler {
	private static HashMap<Integer, TrueTypeFont> fonts = new HashMap<Integer, TrueTypeFont>();
	private static HashMap<String, Integer> quads = new HashMap<String, Integer>();
	private static HashMap<String, Integer> shaders = new HashMap<String, Integer>();

	public static void CreateDisplay(GLDisplay display) {
		try {
			Display.setDisplayMode(new DisplayMode((int) display.GetWidth(), (int) display.GetHeight()));
			// set the display resizable if the resizable is true
			Display.setResizable(display.GetResizable());
			// create the display.
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static float GetDisplayWidth() {
		return Display.getWidth();
	}

	public static float GetDisplayHeight() {
		return Display.getHeight();
	}

	public static void RenderString(float x, float y, String text, Color color) {
		RenderString(x, y, text, color, 12);
	}

	public static void RenderString(float x, float y, String text, Color color, int fontSize) {
		TrueTypeFont font = fonts.get(fontSize);
		if (font == null) {
			Font awtFont = new Font("Times New Roman", Font.PLAIN, fontSize);
			font = new TrueTypeFont(awtFont, false);
			fonts.put(fontSize, font);
		}
		font.drawString(x, y, text, color);
		TextureImpl.bindNone();
	}

	public static void SetDisplayTitle(String newTitle) {
		Display.setTitle(newTitle);
	}

	public static boolean DisplayResized() {

		return Display.wasResized();
	}

	public static void ClearScreen() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static boolean IsDisplayClosed() {
		return Display.isCloseRequested();
	}

	public static void UpdateDisplay(GLDisplay display) {
		Display.update();
		int targetFPS = display.GetTargetFPS();
		if (targetFPS > 0) {
			Display.sync(targetFPS);
		}
	}

	public static void ResizeDisplay(GLDisplay display) {
		display.SetHeight(Display.getHeight());
		display.SetWidth(Display.getWidth());

		glViewport(0, 0, (int) display.GetWidth(), (int) display.GetHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, (int) display.GetWidth(), (int) display.GetHeight(), 0, -1, 1);
	}

	public static void SetupDisplayViewport(GLDisplay display) {
		ResizeDisplay(display);
	}

	public static void SetViewportBackgroundColor() {
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public static void SetViewportBackgroundColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}

	public static void SetRenderColor(float r, float g, float b, float a) {
		glColor4f(r, g, b, a);
	}

	public static void SetRenderColor(float r, float g, float b) {
		SetRenderColor(r, g, b, 1);
	}

	public static void RenderQuad(GLQuadData quadData) {
		RenderQuadData(quadData, GL_QUADS);
	}

	public static void RenderQuadData(GLQuadData quadData, int mode) {
		/*
		 * if (quads.size() == 0 || !quads.containsKey(quadData)) { int dl =
		 * GL11.glGenLists(1); GL11.glNewList(dl, GL11.GL_COMPILE);
		 * RenderRawQuadData(quadData, mode); GL11.glEndList(); quads.put(quadData, dl);
		 * } int dl = quads.get(quadData); glCallList(dl);
		 */
		RenderRawQuadData(quadData, mode);
	}

	private static void RenderRawQuadData(GLQuadData quadData, int mode) {
		glBegin(mode);
		for (byte indice : quadData.GetIndices()) {
			GLPosition vector = quadData.GetVectors(indice);
			glVertex2f(vector.GetX(), vector.GetY());
		}
		glEnd();
	}

	public static void UseShader() {

	}
}
