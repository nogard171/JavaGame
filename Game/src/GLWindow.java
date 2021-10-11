import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class GLWindow {
	// width and height of the display
	static int WIDTH = 800;
	static int HEIGHT = 600;
	int MAXFPS = 120;
	boolean FPSLIMITER = false;
	// the farest rendering distance
	int FARVIEW = 50;
	int FOV = 45;

	private static long lastFrame;
	private long lastFPS;

	Camera camera = new Camera();
	boolean MOUSEFOCUS = true;

	KeyboardInput keyboard = new KeyboardInput();

	int fps = 0;
	int currentFPS = 0;

	public void UpdateFPS() {
		if (getTime() - lastFPS > 1000) {
			// Display.setTitle("FPS: " + fps);
			currentFPS = fps;
			fps = 0; // reset the FPS counter
			lastFPS += 1000; // add one second

		}
		fps++;
	}

	public void CreateWindow() {
		this.createGLWindow();
		SetupOpenGL();
		this.Setup();
		this.SetupControllers();
		this.loop();
	}

	// Under the class definition
	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private static double getDelta() {
		long currentTime = getTime();
		double delta = (double) currentTime - (double) lastFrame;
		lastFrame = getTime();
		return delta;
	}

	private void SetupOpenGL() {
		Mouse.setGrabbed(true);
		// TODO Auto-generated method stub
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective((float) FOV, ((float) WIDTH / (float) HEIGHT), 0.1f, FARVIEW);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glEnable(GL11.GL_TEXTURE_2D);

		// glEnable(GL_COLOR_MATERIAL);
		// glEnable(GL_BLEND);
		// glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		glEnable(GL_CULL_FACE);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

		camera.create();
	}

	public void Update(double delta) {
		keyboard.poll();
		camera.acceptInput((float) delta);
		camera.apply();
	}

	public void SetupControllers() {
		Mouse.setGrabbed(this.MOUSEFOCUS);
	}

	public void Render() {
		keyboard.end();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

	}

	private void createGLWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("A fresh display!");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	public void Setup() {

	}

	public void loop() {
		lastFPS = getTime();
		while (!Display.isCloseRequested()) {
			// set the modelview matrix back to the identit
			GL11.glLoadIdentity();
			double delta = getDelta();
			UpdateFPS();
			this.Update(delta);
			this.Render();
			System.out.println("FPS:" + currentFPS);
			Display.update();
			if (this.FPSLIMITER) {
				Display.sync(MAXFPS);
			}
		}
		this.Destroy();
	}

	public void Destroy() {
		Display.destroy();
		System.exit(0);
	}
}
