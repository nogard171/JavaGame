package game;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import classes.GLObject;
import classes.GLPosition;
import classes.GLSize;
import classes.GLSprite;
import classes.GLVelocity;
import engine.GLCamera;
import engine.GLDisplay;
import engine.GLShader;
import utils.GLLoader;

public class Game extends GLDisplay {

	GLShader shader;
	GLCamera camera;

	@Override
	public void Setup() {
		super.Setup();
		camera = new GLCamera(800, 600);
		shader = new GLShader("basic.vert", "basic.frag");

		sprite = new GLLoader().getSprite("resources/textures/bg.png");

		new GLLoader().loadSprites();
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				for (int z = 48; z < 50; z++) {
					float newX = (x * 32) - (y * 32);
					float newY = (y * 16) + (x * 16) - (z * 32);

					newX *= 1.1f;
					newY *= 1.1f;

					GLObject obj = new GLObject();
					obj.position = new GLPosition(newX, newY);
					obj.type = "GRASS";

					objects.add(obj);
				}
			}
		}
	}

	GLObject obj = new GLObject();

	GLSprite sprite;

	@Override
	public void UpdateWindow(int width, int height) {
		camera.size = new GLSize(width, height);
	}

	@Override
	public void Update(float delta) {
		super.Update(delta);
		float speed = (delta + 1) * 0.5f;

		float xSpeed = 0;
		float ySpeed = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = -speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			ySpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			ySpeed = -speed;
		}
		camera.Move(new GLVelocity(xSpeed, ySpeed));
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					super.close = true;
				}
			}
		}
	}

	ArrayList<GLObject> objects = new ArrayList<GLObject>();
	boolean rendered = false;
	int displayList = -1;

	@Override
	public void Render() {
		super.Render();
		shader.Run();

		float[] colorData = { 1, 1, 1, 1 };
		shader.sendUniform4f("vertColor", colorData);

		float[] viewPosition = { camera.position.x, camera.position.y, 0 };

		shader.sendUniform2f("view", viewPosition);
		/*
		 * for (int x = 0; x < 50; x++) { for (int y = 0; y < 50; y++) { for (int z =
		 * 48; z < 50; z++) { float newX = (x * 32) - (y * 32); float newY = (y * 16) +
		 * (x * 16) - (z * 32);
		 * 
		 * newX *= 1.1f; newY *= 1.1f;
		 * 
		 * float[] position = { newX, newY };
		 * 
		 * shader.sendUniform2f("position", position); shader.sendTexture("myTexture",
		 * new GLData().sprites.get("GRASS").textureID); GL11.glCallList(new
		 * GLData().sprites.get("GRASS").displayLists); } } }
		 */
		if (!rendered) {
			displayList = GL11.glGenLists(1);

			GL11.glNewList(displayList, GL11.GL_COMPILE);
			for (GLObject obj : objects) {
				float[] position = { obj.position.getX(), obj.position.getY() };

				GLSprite sprite = new GLData().sprites.get(obj.type);

				shader.sendUniform2f("position", position);
				shader.sendTexture("myTexture", sprite.textureID);
				GL11.glCallList(sprite.displayLists);
			}
			GL11.glEndList();
			rendered = true;
		}
		else
		{
			GL11.glCallList(displayList);
		}
		float[] zeroPosition = { 0, 0, 0 };

		// shader.sendUniform2f("view", zeroPosition);

		float[] position = { 0, 0 };

		// shader.sendUniform2f("position", position);

		// shader.sendTexture("myTexture", new GLData().sprites.get("Q").textureID);
		// GL11.glCallList(new GLData().sprites.get("Q").displayLists);

	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
