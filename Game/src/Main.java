import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine.Bound;
import engine.Direction;
import engine.Object;
import engine.Sprite;
import engine.Window;
import utils.Loader;

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

public class Main extends Window{



    public static void main(String args[]){
        Main game = new Main();
        game.Start();
    }
   
   
    
    HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    @Override
    public void onInit()
    {    	
    	Sprite guy = new Sprite(32,64);
    	new Loader().loadSprite(guy,"res/img/guy.png");
    	sprites.put("guy",guy);
    	Sprite grass = new Sprite(32,32);
    	new Loader().loadSprite(grass,"res/img/grass.png");
    	sprites.put("grass",grass);

    	Sprite dirt = new Sprite(32,32);
    	new Loader().loadSprite(dirt,"res/img/dirt.png");
    	sprites.put("dirt",dirt);
    	
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
    	
    	if(Keyboard.isKeyDown(Keyboard.KEY_A)&&!person.getCollisionByDirection(Direction.WEST))
    	{
    		person.setFacing(Direction.WEST);
    		step = 3;
    		x-=stepSpeed;
    	}
    	else if(Keyboard.isKeyDown(Keyboard.KEY_D)&&!person.getCollisionByDirection(Direction.EAST))
    	{
    		person.setFacing(Direction.EAST);
    		step = 2;
    		x+=stepSpeed;
    	}
    	else if(Keyboard.isKeyDown(Keyboard.KEY_W)&&!person.getCollisionByDirection(Direction.NORTH))
    	{
    		person.setFacing(Direction.NORTH);
    		step = 1;
    		y+=stepSpeed;
    	}
    	else if(Keyboard.isKeyDown(Keyboard.KEY_S)&&!person.getCollisionByDirection(Direction.SOUTH))
    	{
    		person.setFacing(Direction.SOUTH);
    		step = 0;
    		y-=stepSpeed;
    	}
    	if((Keyboard.isKeyDown(Keyboard.KEY_A)||Keyboard.isKeyDown(Keyboard.KEY_D)||Keyboard.isKeyDown(Keyboard.KEY_W)||Keyboard.isKeyDown(Keyboard.KEY_S))&&!person.getColliding())
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
    		person.setCollisionByDirection(person.getFacing(),true);
    		color = new Color(1,0,0);
    	}
    	else
    	{
    		
    		person.clearCollision();
    		color = new Color(1,1,1);
    	}
    	
    }
    int step = 3;
    float walk = 3;
    int x =0;
    int y=0;
    Object person = new Object();
    Color color = new Color(0,0,0);
    @Override
    public void onRender()
    { 	
    	//g.drawSubImage("resources/guy.png",new Vector4f(0.25f*(int)walk,0.25f*step,0.25f,0.25f), x, y, 32, 64);
    	//g.renderTexturedQuad("resources/guy.png", new Bound(x,y,32,64), new Point((int)walk,step));
    	
    	/*GLObject[][][] mapData = map.getData();
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
		}*/
    	GL11.glPushMatrix();
    	GL11.glTranslatef(100,100, 0);
    	GL11.glCallList(sprites.get("dirt").getDisplayID());
    	GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(person.getBounds().getX(), person.getBounds().getY(), 0);
    	GL11.glColor3f(color.getRed(), color.getGreen(),color.getBlue());
    	GL11.glCallList(sprites.get("guy").getDisplayID((int)person.getDisplayListCoords().getX(),(int)person.getDisplayListCoords().getY()));
    	GL11.glColor3f(1,1,1);
    	GL11.glPopMatrix();

    }
}