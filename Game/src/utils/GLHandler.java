package utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

import engine.GLDisplay;

public class GLHandler {
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

	public static boolean DisplayResized() {
		return Display.wasResized();
	}

	public static void ClearScreen() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static boolean IsDisplayClosed() {
		return Display.isCloseRequested();
	}

	public static void UpdateDisplay(GLDisplay display) {
		Display.update();
		Display.sync(display.GetTargetFPS());
	}

	public static void ResizeDisplay(GLDisplay display) {
		display.SetHeight(Display.getHeight());
		display.SetWidth(Display.getWidth());

		glViewport(0, 0, (int) display.GetWidth(), (int) display.GetHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, (int) display.GetWidth(), 0, (int) display.GetHeight(), -1, 1);
	}

	public static void SetupDisplayViewport(GLDisplay display) {
		ResizeDisplay(display);

		glEnable(GL_BLEND);

	}

	public static void SetViewportBackgroundColor() {
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public static void SetViewportBackgroundColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
}
