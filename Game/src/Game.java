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

	@Override
	public void Render() {
		super.Render();
		shader.Run();

		float[] colorData = { 1, 1, 1, 1 };
		shader.sendUniform4f("vertColor", colorData);

		float[] viewPosition = { camera.position.x, camera.position.y, 0 };

		shader.sendUniform2f("view", viewPosition);

		shader.sendTexture("myTexture", sprite.textureID);

		GL11.glCallList(sprite.displayLists);
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
