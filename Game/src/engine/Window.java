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

import data.DataHub;

public class Window {
	public int _defaultWidth = 800;
	public int _defaultHeight = 600;
	public int _width = 800;
	public int _height = 600;
	public int _fps = 300;
	public boolean _fullscreen = false;
	public boolean _resizable = true;
	public boolean _vsync = false;

	public void run() throws LWJGLException {
		this.init();
	}

	public void create() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(this._defaultWidth, this._defaultHeight));
		Display.create();

	}

	public boolean isActive() {
		return Display.isCloseRequested();
	}

	private void init() throws LWJGLException {
		Display.setResizable(this._resizable);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this._defaultWidth, 0, this._defaultHeight, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	private void resized() throws LWJGLException {
		if (this._defaultWidth != Display.getWidth() || this._defaultHeight != Display.getHeight()) {

			this._defaultWidth = Display.getWidth();
			this._defaultHeight = Display.getHeight();

			GL11.glViewport(0, 0, this._defaultWidth, this._defaultHeight);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, this._defaultWidth, 0, this._defaultHeight, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		}
	}

	public void update() throws LWJGLException {
		if (Display.wasResized()) {
			this.resized();
		}
	}

	public void render() throws LWJGLException {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void sync() throws LWJGLException {
		Display.update();
		if (this._vsync) {
			Display.sync(this._fps);
		}
	}

	public void destroy() throws LWJGLException {
		Display.destroy();
		System.exit(0);
	}
}
