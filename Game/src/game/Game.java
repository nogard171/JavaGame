package game;

import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import core.GLCamera;
import core.GLChunk;
import core.GLDisplay;
import core.GLInput;
import core.GLShader;
import core.GLShaderProgram;
import core.GLSpriteType;
import core.GLVelocity;
import utils.GLDebugger;
import utils.GLFramesPerSecond;
import utils.GLLoader;
import utils.GLLogger;
import utils.GLRenderer;

public class Game {
	GLDisplay display;
	GLFramesPerSecond fps;
	GLCamera camera;
	GLInput input;

	public static GLShader shader;

	public void start() {
		this.setup();
		while (!display.closed()) {
			fps.update();
			float delta = fps.getDelta();
			display.clean();
			this.update(delta);
			this.render();
			display.sync();
		}
		this.destroy();
	}

	GLChunk chunk;

	public void setup() {
		display = new GLDisplay();
		display.create();

		fps = new GLFramesPerSecond();
		fps.start();

		input = new GLInput();
		camera = new GLCamera();

		shader = GLShaderProgram.loadShader("resources/shaders/basic.vert", "resources/shaders/basic.frag");

		GLLoader.loadSprites();

		chunk = new GLChunk();

		chunk.create();
		chunk.generate();
		chunk.build();
	}

	public void UpdateWindow(int width, int height) {

	}

	public void update(float delta) {
		display.update();

		if (Keyboard.isCreated()) {

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
			camera.Move(new GLVelocity(xSpeed, ySpeed));
			camera.update();

			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						display.close();
					}
				}
			}
		}
	}

	public void render() {
		shader.run();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, DataHub.texture.getTextureID());

		float[] cameraPosition = { camera.getPosition().getX(), camera.getPosition().getY() };
		Game.shader.sendUniform2f("cameraPosition", cameraPosition);

		float[] color = { 1, 1, 1 };
		Game.shader.sendUniform3f("color", color);

		shader.sendTexture("myTexture", DataHub.texture.getTextureID());

		chunk.render();

		shader.stop();

		GLDebugger.showBackground(0, 0, 100, 32);
		GLDebugger.showMessage("FPS: " + fps.getFPS(), 0, 0, 12, Color.white);

	}

	public void destroy() {
		DataHub.texture.release();
		Keyboard.destroy();
		display.close();
	}

}
