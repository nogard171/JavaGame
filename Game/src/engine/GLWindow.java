package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class GLWindow {
	private int defaultWidth = 800;
	private int defaultHeight = 600;
	public static int width = 800;
	public static int height = 600;
	private int fps = 300;
	private boolean limitFPS = false;
	private boolean fullscreen = false;
	private boolean resizable = true;
	private boolean vsync = false;

	public void run() throws LWJGLException {
		this.init();
	}

	public void create() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(this.defaultWidth, this.defaultHeight));
		Display.create();

	}

	public boolean isActive() {
		return Display.isCloseRequested();
	}

	private void init() throws LWJGLException {
		Display.setResizable(this.resizable);

		Display.setVSyncEnabled(this.vsync);

		GL11.glViewport(0, 0, this.width, this.height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this.defaultWidth, this.defaultHeight,0 , 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	private void resized() throws LWJGLException {
		if (this.defaultWidth != Display.getWidth() || this.defaultHeight != Display.getHeight()) {

			this.width = Display.getWidth();
			this.height = Display.getHeight();

			GL11.glViewport(0, 0, this.width, this.height);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, this.width,this.height, 0,  1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		}
	}

	public void update() throws LWJGLException {
		if (Display.wasResized()) {
			this.resized();
		}
	}

	public void render() throws LWJGLException {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void sync() throws LWJGLException {
		Display.update();
		if (this.limitFPS) {
			Display.sync(this.fps);
		}
	}

	public void destroy() throws LWJGLException {
		Display.destroy();
		System.exit(0);
	}
}
