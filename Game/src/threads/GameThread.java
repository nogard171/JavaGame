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
	private int program = 0;
	boolean useShader = false;

	@Override
	public void init() {
		super.init();
		int vertShader = 0, fragShader = 0;

		try {
			vertShader = createShader("assets/shaders/screen.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("assets/shaders/screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		} catch (Exception exc) {
			exc.printStackTrace();
			return;
		} finally {
			if (vertShader == 0 || fragShader == 0)
				return;
		}

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if (program == 0)
			return;

		/*
		 * if the vertex and fragment shaders setup sucessfully, attach them to the
		 * shader program, link the sahder program (into the GL context I suppose), and
		 * validate
		 */
		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);

		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program,
				ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program,
				ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}
		useShader = true;

	}

	private int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader,
					ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj,
				ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {
			try {
				in.close();
			} catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				throw exception;
		}

		return source.toString();
	}

	public void setUniformVariables(int programID, Vector2f position) {

		int loc2 = GL20.glGetUniformLocation(programID, "playerPos");
		GL20.glUniform2f(loc2, position.x, position.y);
		// You read the previous comment right?

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
		if (useShader) {
			ARBShaderObjects.glUseProgramObjectARB(program);
		}
		setUniformVariables(program, pos);
		// GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(32, 0);
		GL11.glVertex2f(32, 32 );
		GL11.glVertex2f(0, 32);

		setUniformVariables(program, new Vector2f(1, 1));
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(32, 0);
		GL11.glVertex2f(32, 32 );
		GL11.glVertex2f(0, 32);
		GL11.glEnd();

		if (useShader) {
			ARBShaderObjects.glUseProgramObjectARB(0);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
