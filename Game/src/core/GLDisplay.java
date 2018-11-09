package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GLDisplay {
	public int WIDTH = 800;
	public int HEIGHT = 600;

	public void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			
			Display.setResizable(true);
			
			GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

			this.setupViewPort();

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void setupViewPort()
	{
		GL11.glViewport(0,0,WIDTH,HEIGHT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	public void destroyDisplay() {
		Display.destroy();
	}

	public void postRender() {
		Display.update();
		// Display.sync(60);
	}

	public void render() {
		this.setupViewPort();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
}
