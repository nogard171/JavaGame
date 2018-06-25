package engine;

import org.lwjgl.opengl.GL11;

public class Renderer {

	public static void render() {

	}

	public static void renderChunk(int w, int h, int d) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// for (int z = 0; z < d; z++) {
				
				GL11.glVertex2f((x * 32), (y * 32));
				GL11.glVertex2f(x * 32, (y * 32) + 32);
				GL11.glVertex2f((x * 32) + 32, (y * 32) + 32);
				
				// }
			}
		}
		GL11.glEnd();
	}
}
