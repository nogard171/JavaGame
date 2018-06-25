package game;

import org.lwjgl.opengl.GL11;

import engine.Renderer;

public class GameRenderer {
	public void render() {
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(1, 1, 1);

		//Renderer.renderChunk(20,20,1);
	}
}
