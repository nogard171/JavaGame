package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import engine.GLInput;
import engine.GLWindow;

public class Game {

	GLWindow win;

	public void start() throws Exception {
		this.setup();
		boolean windowClosed = false;
		while (!windowClosed) {
			this.update();
			this.render();
		}

		this.destroy();
	}

	private void setup() throws Exception {
		this.win = new GLWindow();
		this.win.create();
	}

	private void update() throws Exception {
		this.win.update();
		GLInput.poll();

		Display.setTitle("Mouse: " + GLInput.getMousePosition().getX() + "," + GLInput.getMousePosition().getY());
	}

	private void render() throws Exception {
		this.win.render();

		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(32, 0);
		GL11.glVertex2i(32, 32);
		GL11.glVertex2i(0, 32);

		GL11.glEnd();
	}

	private void destroy() throws Exception {
		this.win.destroy();
	}

}
