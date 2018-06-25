package engine;

import org.lwjgl.opengl.GL11;

public class Renderer {

	public static void render() {
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(1,1,1);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(100, 100);
		GL11.glVertex2f(100 + 200, 100);
		GL11.glVertex2f(100 + 200, 100 + 200);
		GL11.glVertex2f(2000, 100 + 200);
		GL11.glEnd();
	}
}
