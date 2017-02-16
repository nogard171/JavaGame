import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
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
import java.util.HashMap;
import java.util.Random;

public class Main extends GLWindow
{

	public static void main(String args[])
	{
		Main game = new Main();
		game.ShowDisplay();
	}

	@Override
	public void Init()
	{
		super.Init();
		
		shader.loadFragmentShader("screen");
    	shader.loadVertexShader("screen");
    	shader.createProgram();
    	
		try
		{
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/tileset.png"));
			// load texture from PNG file
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		
		tiles.put(Type.GRASS, new Loader().loadQuadByType(Type.GRASS));
		tiles.put(Type.DIRT, new Loader().loadQuadByType(Type.DIRT));
		tiles.put(Type.SAND, new Loader().loadQuadByType(Type.SAND));
		
	}
	Texture texture = null;
	@Override
	public void Resize()
	{

	}

	@Override
	public void Update()
	{
		super.Update();
		int x = Mouse.getX(); // will return the X coordinate on the Display.
		int y = Mouse.getY(); // will return the Y coordinate on the Display.

	}
	ShaderProgram shader = new ShaderProgram();
	Viewport view = new Viewport();
	HashMap<Type,Quad> tiles = new HashMap<Type, Quad>();
	@Override
	public void Render()
	{
		super.Render();
		shader.Start();
		Display.setTitle("FPS:" + super.fpsCounter.getFPS());

		shader.sendTexture("myTexture", texture.getTextureID());
		
		GL11.glBegin(view.getMode());
		glCallList(tiles.get(Type.GRASS).getDisplayList());
		GL11.glEnd();

	}

	@Override
	public void Destroy()
	{
		super.Destroy();
	}
}