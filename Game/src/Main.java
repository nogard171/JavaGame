import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
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
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    	    	
		renderer.addQuad(renderer.loader.getQuadFromModel("tile.obj","sand.png"), "test5");
    	
    	renderer.addQuad("grass", "grass.png");
    	renderer.addQuad("dirt", "dirt.png");
    	renderer.addQuad("sand", "sand.png");
    	renderer.addQuad("water", "water.png");
    	
    	
    	int cx = 0;
    	int cy=0;
    	int cwide = 100;
    	int cwidth = 5;
    	for(int c=0;c<cwide*cwide;c++)
    	{
    		Chunk chunk = new Chunk();
    		ArrayList<Entity> entities = new ArrayList<Entity>();
	    	for(int x=0;x<cwidth;x++)
	    	{
	    		for(int y=0;y<cwidth;y++)
	        	{
	    			for(int z=0;z<1;z++)
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
							String key = x+","+y;
							entity.setName(key);
							entities.add(entity);
						}
	            	}
	        	}
	    	}
	    	chunk.entities = entities;
	    	
	    	chunk.setBounds((cx*(cwidth*32))-(cy*(cwidth*32)), -(cy*(cwidth*16))-(cx*(cwidth*16)),cwidth,cwidth);
	    	
	    	
			Entity entity = new Entity("test5");
			chunk.entities.add(entity);
			
	    	
	    	renderer.addChunk((cx)+","+(cy),chunk);
    		if(cx>=cwide-1)
    		{
    			cy++;
    			cx=0;
    		}
    		else
    		{
    			cx++;
    		}
	    }
    	
    	
    }
    @Override
    public void Update(double delta)
    {
    	super.Update(delta);
    	
    	renderer.UpdateDisplayEntities(camera);
    	//renderer.getEntity("test5").setPosition(MouseHandler.getPosition().x+camera.getX(),MouseHandler.getPosition().y+camera.getY());
    	float speed = (float) (1*delta);
    	if(Keyboard.isKeyDown(Keyboard.KEY_A))
    	{
    		camera.position.x -=speed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_D))
    	{
    		camera.position.x +=speed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_W))
    	{
    		camera.position.y +=speed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_S))
    	{
    		camera.position.y -=speed;
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
		
    }
}