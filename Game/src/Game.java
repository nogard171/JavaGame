
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
import core.GLShaderProgram;
import utils.GLFramesPerSecond;
import utils.GLLogger;

public class Game {
	GLDisplay display;
	GLFramesPerSecond fps;
	GLCamera camera;
	GLInput input;

	public void start() {
		this.setup();
		while (!display.closed()) {
			fps.update();
			float delta = fps.getDelta();
			this.update(delta);
			display.clean();
			this.render();
			display.sync();
		}
		this.destroy();
	}

	public void setup() {
		display = new GLDisplay();
		display.create();

		fps = new GLFramesPerSecond();
		fps.start();

		input = new GLInput();
		camera = new GLCamera(800, 600);
		newShader = GLShaderProgram.loadShader("basic.vert", "basic.frag");

	}

	GLShader newShader;

	public void UpdateWindow(int width, int height) {

	}

	public void update(float delta) {
		display.update();
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
					display.close();
				}
			}
		}
	}

	public void render() {
		newShader.run();

		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(32, 0);
		GL11.glVertex2i(32, 32);

		GL11.glEnd();
	}

	public void destroy() {
		newShader.stop();
		newShader.destroy();
		display.close();
	}

}
