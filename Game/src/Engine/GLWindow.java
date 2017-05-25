package Engine;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GLWindow {

	private int WIDTH = 800;
	private int HEIGHT = 600;
	private int FPS = 120;
	private String TITLE = "";
	private DisplayMode DISPLAYMODE = null;
	private boolean RESIZABLE = true;

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
				this.Update();
				this.Render();

				Display.update();
				Display.sync(FPS);
			}
			this.Destroy();

		} catch (LWJGLException e) {
			this.Destroy();
		}
	}

	GLFramesPerSecond fps;

	private void setupGL() {
		fps = new GLFramesPerSecond();
		fps.startFPS();
		// this sets up the viewport for rendering.
		this.SetupViewPort();

		// put lua scripting here for dynamic enable.
		// this enables blending
		GL11.glEnable(GL11.GL_BLEND);

		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void Update() {
		GLFramesPerSecond.updateFPS();
		Display.setTitle("FPS:" + fps.fps);
		// this detects resizing to allow for the viewport to be
		// re-setup.
		if (Display.wasResized()) {
			this.SetupViewPort();
		}
	}

	private void SetupViewPort() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void Render() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

	}

	private void Destroy() {
		Display.destroy();
	}

	public void Setup() {

	}

}