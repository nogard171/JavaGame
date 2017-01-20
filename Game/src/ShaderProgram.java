import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class ShaderProgram
{
	int programID;
    public int getProgramID()
	{
		return programID;
	}
	int vertexShader,fragmentShader;
    
    public void loadFragmentShader(String filename)
    {
    	String frag ="";
    	try
		{
			frag = new String(Files.readAllBytes(Paths.get("resources/shaders/"+filename+".frag")));
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);		
    	GL20.glShaderSource(fragmentShader, frag);     
    	GL20.glCompileShader(fragmentShader);
    }
    public void loadVertexShader(String filename)
    {
    	String vert ="";
    	try
		{
    		vert = new String(Files.readAllBytes(Paths.get("resources/shaders/"+filename+".vert")));
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);		
    	GL20.glShaderSource(vertexShader, vert);     
    	GL20.glCompileShader(vertexShader);
    }
    public void createProgram()
    {
    	programID = GL20.glCreateProgram();
    	GL20.glAttachShader(programID, fragmentShader);
    	GL20.glAttachShader(programID, vertexShader);
    	GL20.glLinkProgram(programID);
    }
    public void sendTexture(int textureID)    
    {
    	//int loc = GL20.glGetUniformLocation(programID, name);
	    //GL20.glUniform1i(loc, 0); //Texture Unit 0 for sampler1.
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }
    public void Start()
    {
    	GL20.glUseProgram(programID);
    }
    public void Destroy()
    {
    	GL20.glDeleteProgram(programID);
    }
}
