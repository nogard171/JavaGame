import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import core.GLChunk;
import core.GLDisplay;
import core.GLFPS;
import core.GLQuad;

public class Main extends GLDisplay {
	GLFPS fps = new GLFPS();

	GLChunk chunk;

	public void run() {
		this.createDisplay();
		chunk = new GLChunk();

		while (!Display.isCloseRequested()) {
			fps.updateFPS();
			this.update();

			this.render();

			this.postRender();
		}
		this.destroyDisplay();
	}

	public void update() {

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {

		}
	}

	@Override
	public void render() {
		super.render();
		chunk.render();
	}

	public static void main(String[] args) {
		new Main().run();
	}
}