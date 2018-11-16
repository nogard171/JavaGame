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

	GLFramesPerSecond fps;
	protected boolean close = false;

	Rectangle view = new Rectangle(0, 0, 400, 400);

	public void Create() {
		try {
			this.DISPLAYMODE = new DisplayMode(WIDTH, HEIGHT);
			Display.setDisplayMode(this.DISPLAYMODE);
			Display.setResizable(RESIZABLE);
			Display.setTitle(TITLE);
			Display.create();
			this.setupGL();
			this.Setup();
			while (!Display.isCloseRequested()) {
				if (close) {
					break;
				}
				UpdateWindow(Display.getWidth(), Display.getHeight());

				this.Update(fps.getDelta());
				this.Render();
				Display.update();
				if (FPS_LIMITER) {
					Display.sync(FPS);
				}
			}
		} catch (LWJGLException e) {
		}
		this.Destroy();
	}

	private void setupGL() {
		fps = new GLFramesPerSecond();
		fps.start();
		this.SetupViewPort();
		fps.getDelta();

		GL11.glEnable(GL11.GL_BLEND);
		// GL11.glShadeModel(GL11.GL_SMOOTH);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void write(String value) {
		System.out.println(value);
	}

	public void glBlendFunc(int val1, int val2) {
		GL11.glBlendFunc(val1, val2);
	}

	public void glShadeModel(int enableID) {
		GL11.glShadeModel(enableID);
	}

	public void glEnable(int enableID) {
		GL11.glEnable(enableID);
	}

	public void Update(float delta) {
		GLFramesPerSecond.updateFPS();
		Display.setTitle("FPS:" + fps.fps);
		if (Display.wasResized()) {
			this.SetupViewPort();
		}
	}

	private void SetupViewPort() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

	}

	public void UpdateWindow(int width, int height) {

	}

	public void Render() {

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void Destroy() {
		Display.destroy();
	}

	public void Setup() {

	}

}
