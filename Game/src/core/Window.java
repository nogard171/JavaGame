package core;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class Window {
	// width and height of the display
	private int WIDTH = 1024;
	private int HEIGHT = 780;
	private int MAXFPS = 120;
	private boolean FPSLIMITER = true;
	// the farest rendering distance
	private int FARVIEW = 5000;
	private int FOV = 45;

	public void create() {
		this.createWindow();
	}

	private void createWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("A fresh display!");
			// future addition
			// Display.setResizable(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	public void setupOpenGL() {
		this.setupPerspective();

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	private void setupPerspective() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective((float) FOV, ((float) WIDTH / (float) HEIGHT), 0.1f, FARVIEW);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void update() {
		GL11.glLoadIdentity();
		if (Display.wasResized()) {
			this.WIDTH = Display.getWidth();
			this.HEIGHT = Display.getHeight();
			// future resizing code.
		}
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

	}

	public void sync() {
		Display.update();
		if (this.FPSLIMITER) {
			Display.sync(MAXFPS);
		}
		if (Display.isCloseRequested()) {
			System.out.println("Closing Window");
			System.exit(0);
		}
	}

	public void destroy() {
		Display.destroy();
	}
}
