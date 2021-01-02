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
import core.ShaderProgram;
import utils.FPS;

public class GameThread extends BaseThread {
	ShaderProgram shader;

	@Override
	public void init() {
		super.init();
		shader = new ShaderProgram("assets/shaders/screen.vert", "assets/shaders/screen.frag");
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
		shader.Run();
		float[] position = { pos.x, pos.y };
		shader.sendUniform2f("position", position);
		// GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(32, 0);
		GL11.glVertex2f(32, 32);
		GL11.glVertex2f(0, 32);

		float[] position2 = { 100, 100 };
		shader.sendUniform2f("position", position2);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(32, 0);
		GL11.glVertex2f(32, 32);
		GL11.glVertex2f(0, 32);
		GL11.glEnd();
		shader.Stop();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
