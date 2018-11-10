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
import org.lwjgl.util.vector.Vector2f;

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

	Vector2f camera = new Vector2f(0, 0);

	public void update() {
		float speed = 0.5f * fps.getDelta();
		chunk.update(camera);
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.x += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.x -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.y += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.y -= speed;
		}
	}

	@Override
	public void render() {
		super.render();
		GL11.glPushMatrix();
		GL11.glTranslatef(camera.x, camera.y, 0);
		chunk.render();
		GL11.glPopMatrix();
	}

	public static void main(String[] args) {
		new Main().run();
	}
}