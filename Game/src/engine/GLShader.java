package engine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class GLShader {
	int programID;	

    public int getProgramID()
	{
		return programID;
	}
    public int loadShader(String filename, int shaderType)
    {
    	String shaderContents = new FileLoader().getContent(filename);
    	
    	int shader = GL20.glCreateShader(shaderType);		
    	GL20.glShaderSource(shader, shaderContents);     
    	GL20.glCompileShader(shader);
    	return shader;
    }
    public void sendUniform1f(String name, float data)
    {
    	int loc = GL20.glGetUniformLocation(programID, name);
	    GL20.glUniform1f(loc, data); 
    }
    public void createProgram()
    {
    	int fragmentShader = loadShader("resources/shaders/tile.frag",GL20.GL_FRAGMENT_SHADER);
    	int vertexShader = loadShader("resources/shaders/tile.vert",GL20.GL_VERTEX_SHADER);
    	
    	this.programID = GL20.glCreateProgram();
    	GL20.glAttachShader(programID, fragmentShader);
    	GL20.glAttachShader(programID, vertexShader);
    	GL20.glLinkProgram(programID);
    }

    public void Start()
    {
    	GL20.glUseProgram(programID);
    }
    public void Destroy()
    {
    	GL20.glDeleteProgram(programID);
    }
	public void sendUniform3f(String name, float[] data) {
    	int loc = GL20.glGetUniformLocation(programID, name);
	    GL20.glUniform3f(loc, data[0], data[1], data[2]);
	}
}
