
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

import core.GLCamera;
import core.GLDisplay;
import core.GLInput;
import core.GLShader;

public class Game extends GLDisplay {

	// GLShader shader;
	GLCamera camera;
	GLInput input;

	@Override
	public void Setup() {
		super.Setup();
		input = new GLInput();
		camera = new GLCamera(800, 600);
		GLShader newShader = new GLShader("basic.vert", "basic.frag");

	}

	@Override
	public void UpdateWindow(int width, int height) {

	}

	@Override
	public void Update(float delta) {
		super.Update(delta);
		input.update();
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

	}

	@Override
	public void Destroy() {
		super.Destroy();
	}

}
