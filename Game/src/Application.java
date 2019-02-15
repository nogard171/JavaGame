
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class Application extends GLWindow {
	// the grid boolean, used for render things as lines.

	public static void main(final String[] argv) {
		final Application app = new Application();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				app.CreateWindow();
			}
		});
	}

	Texture texture;
	ShaderProgram shader = new ShaderProgram();
	float step = 0;

	@Override
	public void Setup() {

		super.camera.setPos(new Vector3f(0, 18, 0));

		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tileset.png"));
			step = ((float) 32 / (float) texture.getImageWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
		shader.createProgram();
		// int tempCount = 2;
		int yCount = 1;
		int xCount = 2;
		int zCount = 2;
		for (int y = 0; y < yCount; y++) {
			for (int x = 0; x < xCount; x++) {
				for (int z = 0; z < zCount; z++) {
					chunks.put(x + "," + y + "," + z, new Chunk(x * 16, y * 16, z * 16));
				}
			}
		}

	}

	public static HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();

	int chunkRange = 3;

	@Override
	public void Render() {
		super.Render();
		shader.Start();


		shader.sendUniform1f("myTexture", texture.getTextureID());

		int camX = (int) (Math.round(super.camera.getX()) / 16);
		int camY = (int) (Math.round(super.camera.getY()) / 16);
		int camZ = (int) (Math.round(super.camera.getZ()) / 16);
		for (int x = camX - chunkRange; x < camX + chunkRange; x++) {
			for (int y = camY - chunkRange; y < camY + chunkRange; y++) {
				for (int z = camZ - chunkRange; z < camZ + chunkRange; z++) {
					Chunk chunk = chunks.get(x + "," + y + "," + z);
					if (chunk != null) {
						chunk.renderChunk(step);
					}
				}
			}
		}
	}

	static int gameMode = 0;
	static float gravity = 0;
	public static float moveSpeed = 5f;

	@Override
	public void Update(double delta) {
		super.Update(delta);

		if (keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			System.exit(0);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			gameMode = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			gameMode = 1;
		}

		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyFast = Keyboard.isKeyDown(Keyboard.KEY_Q);
		boolean keySlow = Keyboard.isKeyDown(Keyboard.KEY_E);
		boolean keyFlyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean keyFlyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

		float speed;

		if (keyFast) {
			speed = moveSpeed * 5;
		} else if (keySlow) {
			speed = moveSpeed / 2;
		} else {
			speed = moveSpeed;
		}

		speed /= 1500;
		speed *= delta;

		if (keyDown) {
			super.camera.move(speed);
		}
		if (keyUp) {
			super.camera.move(-speed);
		}
		if (keyLeft) {
			super.camera.strafe(speed);
		}
		if (keyRight) {
			super.camera.strafe(-speed);
		}
		if (keyFlyUp) {
			super.camera.fly(speed);
		}
		if (keyFlyDown) {
			super.camera.fly(-speed);
		}
	}
}