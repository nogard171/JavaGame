package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import utils.GLLogger;

public class GLShader {
	private int programID = -1;
	private int vertexShader = -1;
	private int fragmentShader = -1;

	public GLShader(int newProgramID, int newVertexShader, int newFragmentShader) {
		this.programID = newProgramID;
		this.vertexShader = newVertexShader;
		this.fragmentShader = newFragmentShader;
	}

	public void run() {
		if (programID != -1) {
			GL20.glUseProgram(programID);
		} else {
			GLLogger.write("Failed to use Shader");
		}
	}

	public void sendTexture(String name, int textureID) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		this.sendUniform1f(name, 0);
	}

	public void sendUniform4f(String name, float[] data) {
		int loc = GL20.glGetUniformLocation(programID, name);
		GL20.glUniform4f(loc, data[0], data[1], data[2], data[3]);
	}

	public void sendUniform3f(String name, float[] data) {
		int loc = GL20.glGetUniformLocation(programID, name);
		GL20.glUniform3f(loc, data[0], data[1], data[2]);
	}

	public void sendUniform2f(String name, float[] data) {
		int loc = GL20.glGetUniformLocation(programID, name);
		GL20.glUniform2f(loc, data[0], data[1]);
	}

	public void sendUniform1f(String name, float data) {
		int loc = GL20.glGetUniformLocation(programID, name);
		GL20.glUniform1f(loc, data);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void destroy() {
		GL20.glDetachShader(programID, fragmentShader);
		GL20.glDetachShader(programID, vertexShader);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteProgram(programID);
	}
}