package threads;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Position;
import core.GameData;
import core.Input;
import utils.FPS;
import utils.Renderer;

public class GameThread extends BaseThread {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void update() {
		super.update();
		float speed = FPS.getDelta() * 0.15f;
		Vector2f velocity = new Vector2f(0, 0);
		if (Input.isKeyDown(Keyboard.KEY_A)) {
			velocity.x = -speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_W)) {
			velocity.y = -speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_S)) {
			velocity.y = speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_D)) {
			velocity.x = speed;
		}
		pos.move(velocity);
	}

	Position pos = new Position(100, 100);

	@Override
	public void render() {
		super.render();
		GL11.glColor3f(1, 0, 0);

		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.drawQuad(pos.x, pos.y, 32, 32, Color.red);
		Renderer.drawQuad(100, 100, 32, 32, Color.green);
		Renderer.endDraw();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
