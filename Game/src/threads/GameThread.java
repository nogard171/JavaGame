package threads;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import core.Input;

public class GameThread extends BaseThread {
	@Override
	public void init() {
		super.init();
	}

	@Override
	public void update() {
		super.update();
		
	}

	@Override
	public void render() {
		super.render();
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(32, 0);
		GL11.glVertex2i(32, 32);
		GL11.glVertex2i(0, 32);
		GL11.glEnd();
		
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
