package core;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import utils.GLFramesPerSecond;

public class GLDisplay {

	private int WIDTH = 800;
	private int HEIGHT = 600;
	private int FPS = 60;
	private boolean FPS_LIMITER = false;
	private String TITLE = "";
	private DisplayMode DISPLAYMODE = null;
	private boolean RESIZABLE = true;

	private boolean close = false;

	public void create() {
		try {
			this.DISPLAYMODE = new DisplayMode(WIDTH, HEIGHT);
			Display.setDisplayMode(this.DISPLAYMODE);
			Display.setResizable(RESIZABLE);
			Display.setTitle(TITLE);
			Display.create();
			this.setupGL();
		} catch (LWJGLException e) {
		}
	}

	private void setupGL() {
		this.setupViewPort();

		GL11.glEnable(GL11.GL_BLEND);

		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA); 
		
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void update() {
		if (wasResized()) {
			this.setupViewPort();
		}
		if (this.close) {
			Display.destroy();
		}

		if (Display.isCloseRequested()) {
			this.close = true;
		}
	}

	public boolean wasResized() {
		return Display.wasResized();
	}

	public boolean closed() {
		return this.close;
	}

	private void setupViewPort() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

	}

	public void clean() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void sync() {
		Display.update();
		if (FPS_LIMITER) {
			Display.sync(FPS);
		}
	}

	public void close() {
		this.close = true;
	}

}
