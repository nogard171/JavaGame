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

import core.Input;
import utils.FPS;

public class GameThread extends BaseThread {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void update() {
		super.update();
		if (Input.isMousePressed(0)) {
			System.out.println("Mouse Pressed");
		}
		float speed = FPS.getDelta() * 0.5f;
		if (Input.isKeyDown(Keyboard.KEY_A)) {
			pos.x -= speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_W)) {
			pos.y -= speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_S)) {
			pos.y += speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_D)) {
			pos.x += speed;
		}
	}

	Vector2f pos = new Vector2f(100, 100);

	@Override
	public void render() {
		super.render();
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(pos.x, pos.y);
		GL11.glVertex2f(32 + pos.x, pos.y);
		GL11.glVertex2f(32 + pos.x, pos.y + 32);
		GL11.glVertex2f(pos.x, pos.y + 32);

		GL11.glColor3f(0, 1, 0);
		GL11.glVertex2f(100, 100);
		GL11.glVertex2f(32 + 100, 100);
		GL11.glVertex2f(32 + 100, 32 + 100);
		GL11.glVertex2f(100, 32 + 100);
		GL11.glEnd();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
