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
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import core.GLCamera;
import core.GLDisplay;
import core.GLInput;
import core.GLRenderer;
import core.GLShader;
import core.GLShaderProgram;
import core.GLSpriteType;
import utils.GLDebugger;
import utils.GLFramesPerSecond;
import utils.GLLoader;
import utils.GLLogger;

public class Game {
	GLDisplay display;
	GLFramesPerSecond fps;
	GLCamera camera;
	GLInput input;
	GLShader newShader;

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

		GLLoader.loadSprites();
	}

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
		
		float[] color = { 1, 0, 0,1 };
		newShader.sendUniform4f("vertColor", color);

		//newShader.sendTexture("myTexture", DataHub.texture.getTextureID());

		GLRenderer.RenderSprite(DataHub.spriteData.get(GLSpriteType.GRASS));
		/*
		 * GLDebugger.showBackground(0, 0, 100, 32); GLDebugger.showMessage("FPS: " +
		 * fps.getFPS(), 0, 0, 12, Color.white);
		 */
	}

	public void destroy() {
		newShader.stop();
		newShader.destroy();
		display.close();
	}

}
