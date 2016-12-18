import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main extends Window{



    public static void main(String args[]){
        Main game = new Main();
        game.Start();
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
    ShaderProgram shader = new ShaderProgram();
    Renderer renderer = new Renderer();

    @Override
    public void Init()
    {
    	super.Init();
    	
    	shader.loadFragmentShader("screen");
    	shader.loadVertexShader("screen");
    	shader.createProgram();
    	
    	renderer.setShader(shader);

    	
    	renderer.addQuad("grass", "grass.png");
    	renderer.addQuad("dirt", "dirt.png");
    	renderer.addQuad("sand", "sand.png");
    	renderer.addQuad("water", "water.png");
    	
    	for(int x = 0;x<30;x++)
    	{
    		for(int y = 0;y<30;y++)
        	{
    			Entity entity =  new Entity("grass");
    			entity.setPosition(x*32,y*32);
    			renderer.addEntity(x+","+y,entity);
        	}
    	}
    	
    	
    	
    	renderer.addEntity("test2", new Entity("dirt"));
    	renderer.addEntity("test", new Entity("water"));
    	renderer.getEntity("test").setPosition(new Vector2f(100,100));
    	renderer.getEntity("test2").setPosition(new Vector2f(32,32));
    	renderer.getEntity("test2").setOrigin(0.5f,0.5f);
    	
    }
    float rot = 0;
    @Override
    public void Render(){
    	super.Render();		
		
		
    	renderer.Render();
		
		renderer.getEntity("test2").setRotX(rot);
		renderer.getEntity("test").setPosition(MouseHandler.getPosition());
		rot+=0.5f;
    }
}