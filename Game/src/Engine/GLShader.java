package Engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class GLShader extends GLComponent {
	private int programID=-1;
	private int vertexShader =-1;
	private int fragmentShader =-1;
	
	public GLShader()
	{
		this.setName("shader");
	}

	private int getShader(String filename, int shader) {
		int newShaderId;
		String shaderFile = "";
		try {
			shaderFile = new String(Files.readAllBytes(Paths.get("resources/shaders/" + filename)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newShaderId = GL20.glCreateShader(shader);// GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(newShaderId, shaderFile);
		GL20.glCompileShader(newShaderId);
		return newShaderId;
	}

    public void sendUniform4f(String name, float[] data)    
    {
    	int loc = GL20.glGetUniformLocation(programID, name);
	    GL20.glUniform4f(loc, data[0],data[1],data[2],data[3]);
    }
    
	public void Run() {
		if(vertexShader==-1l)
		{
			vertexShader = this.getShader("screen.vert",  GL20.GL_VERTEX_SHADER);
		}
		if(fragmentShader==-1l)
		{
			fragmentShader = this.getShader("screen.frag",  GL20.GL_FRAGMENT_SHADER);
		}
		
		if(programID==-1)
		{
			programID = GL20.glCreateProgram();
	    	GL20.glAttachShader(programID, fragmentShader);
	    	GL20.glAttachShader(programID, vertexShader);
	    	GL20.glLinkProgram(programID);
		}
		
		GL20.glUseProgram(programID);
	}

	public void Destroy() {
		GL20.glDetachShader(programID, fragmentShader);
		GL20.glDetachShader(programID, vertexShader);
		
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteShader(vertexShader);
		
		GL20.glDeleteProgram(programID);
	}
}