import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.Bound;
import entities.GLBound;
import entities.GLMap;
import entities.GLObject;
import entities.GLSprite;
import utils.GLLoader;

import static org.lwjgl.opengl.GL11.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class Main extends GLWindow{



    public static void main(String args[]){
        Main game = new Main();
        game.Start();
    }
   
   
    GLBound bound = new GLBound(32,32);
    
    HashMap<String, GLSprite> sprites = new HashMap<String, GLSprite>();
    @Override
    public void onInit()
    {    	
    	GLSprite guy = new GLSprite(32,64);
    	new GLLoader().loadGLSprite(guy,"res/img/guy.png");
    	sprites.put("guy",guy);
    	GLSprite grass = new GLSprite(32,32);
    	new GLLoader().loadGLSprite(grass,"res/img/grass.png");
    	sprites.put("grass",grass);

    	GLSprite dirt = new GLSprite(32,32);
    	new GLLoader().loadGLSprite(dirt,"res/img/dirt.png");
    	sprites.put("dirt",dirt);
    	
    	
    	new GLLoader().loadBound(bound);
    	
    	person.setHasBound(true);
    	
    	map = new GLMap();
    }
    @Override
    public void onUpdate()
    {
    	super.onUpdate();
    	int stepSpeed= 1;
    	
    	if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    	{
    		stepSpeed=2;
    	}
    	else
    	{
    		stepSpeed = 1;
    	}
    	
    	if(Keyboard.isKeyDown(Keyboard.KEY_A))
    	{
    		step = 3;
    		x-=stepSpeed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_D))
    	{
    		step = 2;
    		x+=stepSpeed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_W))
    	{
    		step = 1;
    		y+=stepSpeed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_S))
    	{
    		step = 0;
    		y-=stepSpeed;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_A)||Keyboard.isKeyDown(Keyboard.KEY_D)||Keyboard.isKeyDown(Keyboard.KEY_W)||Keyboard.isKeyDown(Keyboard.KEY_S))
    	{
    		walk+=0.05f*stepSpeed;
    	}
    	else
    	{
    		walk = 0;
    	}

    	if(walk>=4)
    	{
    		walk = 0;
    	}
    	person.setDisplayListCoords(new Vector2f(walk,step));
    	person.setPosition(x,y);
    	
    	if(person.getBounds().intersects(new Bound(100,100,32,32)))
    	{
    		person.setHasBound(false);
    	}
    	else
    	{
    		person.setHasBound(true);
    	}
    	
    }
    int step = 3;
    float walk = 3;
    int x =0;
    int y=0;
    GLObject person = new GLObject();
    GLMap map = new GLMap();
    @Override
    public void onRender()
    { 	
    	//g.drawSubImage("resources/guy.png",new Vector4f(0.25f*(int)walk,0.25f*step,0.25f,0.25f), x, y, 32, 64);
    	//g.renderTexturedQuad("resources/guy.png", new Bound(x,y,32,64), new Point((int)walk,step));
    	
    	GLObject[][][] mapData = map.getData();
    	for (int x = 0; x < map.getMapSize().getWidth(); x++)
		{
			for (int y = 0; y < map.getMapSize().getHeight(); y++)
			{
				GLObject object = mapData[x][y][0];
				GL11.glPushMatrix();
		    	GL11.glTranslatef(object.getBounds().getX(), object.getBounds().getY(), 0);
		    	GL11.glCallList(sprites.get("grass").getDisplayID());		    	
		    	GL11.glPopMatrix();
			}
		}
    	GL11.glPushMatrix();
    	GL11.glTranslatef(100,100, 0);
    	GL11.glCallList(sprites.get("dirt").getDisplayID());
    	GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(person.getBounds().getX(), person.getBounds().getY(), 0);
    	if(person.getHasBound())
    	{
    		GL11.glCallList(bound.getDisplayID());
    	}
    	GL11.glCallList(sprites.get("guy").getDisplayID((int)person.getDisplayListCoords().getX(),(int)person.getDisplayListCoords().getY()));
    	GL11.glPopMatrix();

    }
}