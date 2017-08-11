import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GLWindow{
	private int width = 800;
	private int height = 600;
	private int targetFPS = 120;
	private String title = "";
	private DisplayMode displayMode = null;
	private boolean isResizable = true;
	FPSHandler fps = new FPSHandler();

	private void createDisplay() throws LWJGLException {

		this.displayMode = new DisplayMode(this.width, this.height);
		Display.setDisplayMode(displayMode);
		Display.setResizable(this.isResizable);
		Display.setTitle(this.title);
		Display.create();

	}

	public void init() {
		fps.getDelta();
		fps.lastFPS = fps.getTime();
	}

	private void adjusterView() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void update() {
		if (Display.wasResized()) {
			this.adjusterView();
		}
		fps.update();
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void destroy() {
		Display.destroy();
	}

	private void setupOpenGL() {
		this.adjusterView();
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		this.EnableGL();
	}

	private void EnableGL() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void start() {
		try {
			this.createDisplay();
			this.setupOpenGL();
			this.init();

			while (!Display.isCloseRequested()) {
				this.update();
				this.render();

				Display.update();
				Display.sync(this.targetFPS);
			}
			this.destroy();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}
