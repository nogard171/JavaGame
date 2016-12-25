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
    Renderer renderer = new Renderer();
    Camera camera = new Camera(-400,-400);
    Random rand = new Random();
    RawQuad test = null;
    Quad quad = null;
    @Override
    public void Init()
    {
    	super.Init();
    	    	
		
    	
    	renderer.addQuad("grass", "grass.png");
    	renderer.addQuad("dirt", "dirt.png");
    	renderer.addQuad("sand", "sand.png");
    	renderer.addQuad("water", "water.png");
    	
    	for(int x=0;x<10;x++)
    	{
    		for(int y=0;y<10;y++)
        	{
    			for(int z=0;z<10;z++)
            	{
					int ran = rand.nextInt(10 - 1 + 1) + 1;
					String type ="grass";
					if(ran%2==0)
					{
						type = "dirt";	
					}
					if(z==0||ran%2==0)
					{
						Entity entity = new Entity(type);
						entity.setPosition((x*32)-(y*32), -(y*16)-(x*16)+(z*8));
						renderer.addEntity(x+","+y+","+z,entity);
					}
            	}
        	}
    	}
    	renderer.addEntity("test", new Entity("dirt"));
    	renderer.getEntity("test").setPosition(new Vector2f(0,0));
    	
    	if(test==null)
		{
			quad = renderer.loader.getQuadFromModel("tile.obj");
			renderer.addQuad(quad, "test5");
			Entity entity = new Entity("test5");
			renderer.addEntity("test5",entity);
		}
    	
    }
    float rot = 0;
    @Override
    public void Render(){
    	
    	super.Render();		
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.getX(),-camera.getY(),0);		
    	renderer.Render();
    	GL11.glPopMatrix();
		renderer.getEntity("test5").setPosition(MouseHandler.getPosition().x+camera.getX(),MouseHandler.getPosition().y+camera.getY());
    }
}