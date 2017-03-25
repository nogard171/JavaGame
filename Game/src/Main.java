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

    @Override
    public void onInit()
    {    	
    	super.onInit();
    }
    @Override
    public void onUpdate()
    {
    	super.onUpdate();
    }
  
    @Override
    public void onRender(GLGraphics g)
    {
    	super.onRender(g);
    	g.setColor(255,0,0);
    	g.drawRect(0, 0, 32, 32);
    	for(int x=0;x<10;x++)
    	{
    		for(int z=0;z<10;z++)
        	{
    			g.drawImage("resources/grass.png", x*32, z*32, 32, 32);
        	}
    	}
    	
    	
    }
}