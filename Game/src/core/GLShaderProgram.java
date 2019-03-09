package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL20;

public class GLShaderProgram {

	public static GLShader loadShader(String vertFile, String fragFile) {

		int programID = -1;
		int vertexShader = -1;
		int fragmentShader = -1;
		String vertexShaderFile = vertFile;
		String fragmentShaderFile = fragFile;

		if (vertexShader == -1 && vertexShaderFile != "") {
			vertexShader = getShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
		}
		if (fragmentShader == -1 && fragmentShaderFile != "") {
			fragmentShader = getShader(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
		}
		if (programID == -1) {
			programID = GL20.glCreateProgram();
			GL20.glAttachShader(programID, fragmentShader);
			GL20.glAttachShader(programID, vertexShader);
			GL20.glLinkProgram(programID);
		}
		return new GLShader(programID, vertexShader, fragmentShader);
	}

	private static int getShader(String filename, int shader) {
		int newShaderId;
		String shaderFile = "";
		try {
			shaderFile = new String(Files.readAllBytes(Paths.get( filename)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		newShaderId = GL20.glCreateShader(shader);// GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(newShaderId, shaderFile);
		GL20.glCompileShader(newShaderId);
		return newShaderId;
	}
}
