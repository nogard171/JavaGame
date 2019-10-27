package engine;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import game.Settings;

public class GLWindow {

	public void create() throws Exception {
		Display.setDisplayMode(new DisplayMode(Settings.width, Settings.height));
		Display.create();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Settings.width, Settings.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void update() throws Exception {
		Display.update();
		if (Display.isCloseRequested()) {
			System.exit(0);
		}
	}

	public void render() throws Exception {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void destroy() throws Exception {
		Display.destroy();
	}
}
