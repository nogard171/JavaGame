package game;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import core.GLAnimatedObject;
import core.GLChunk;
import core.GLDisplay;
import core.GLFPS;
import core.GLLoader;
import core.GLSize;
import core.GLSpriteData;
import core.GLType;

public class Main extends GLDisplay {
	GLFPS fps = new GLFPS();

	public static HashMap<String, GLSpriteData> sprites = new HashMap<String, GLSpriteData>();
	HashMap<String, GLChunk> chunks = new HashMap<String, GLChunk>();
	// ArrayList<GLChunk> chunks = new ArrayList<GLChunk>();
	int currentLevel = 0;
	// GLAnimatedObject aniObj;
	public static Texture texture;

	public void run() {
		this.createDisplay();

		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tileset.png"));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				GLChunk chunk = new GLChunk(x, y);
				System.out.println(x + "," + y);
				chunks.put(x + "," + y, chunk);
			}
		}

		GLLoader.loadSprites("resources/data/sprites.xml");

		// aniObj = new GLAnimatedObject(GLType.GRASS);

		// GLChunk chunk = new GLChunk(0, 0, 0);
		// chunks.add(chunk);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
		// range
		// Note: GL_CLAMP_TO_EDGE is part of GL12
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
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

		for (Object obj : chunks.values()) {
			GLChunk chunk = (GLChunk) obj;
			if (chunk != null) {
				chunk.update();

			}
		}

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
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				GLChunk chunk = chunks.get(x + "," + y);

				if (chunk != null) {
					chunk.render();
				}
			}
		}

		GL11.glPopMatrix();

	}

	public static void main(String[] args) {
		try {
			new Main().run();
		} catch (Exception e) {
			System.out.println("Error2: " + e.getLocalizedMessage());
		}
	}
}