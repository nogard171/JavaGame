import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;

public class Shader {
	// Shader variables
	public int vsId = 0;
	public int fsId = 0;
	public int pId = 0;

	public void setupShaders() {
		// Load the vertex shader
		vsId = this.loadShader("resources/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsId = this.loadShader("resources/shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);

		// Create a new shader program that links both shaders
		pId = GL20.glCreateProgram();
		GL20.glAttachShader(pId, vsId);
		GL20.glAttachShader(pId, fsId);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(pId, 0, "in_Position");
		// Color information will be attribute 1
		GL20.glBindAttribLocation(pId, 1, "in_Color");
		// Textute information will be attribute 2
		GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");

		GL20.glLinkProgram(pId);
		GL20.glValidateProgram(pId);

	}

	public int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}

		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		return shaderID;
	}

	public void useShader() {
		// TODO Auto-generated method stub

	}
}
