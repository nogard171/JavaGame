import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
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
import java.util.Random;

public class Main extends Window
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
		view = new View();
		renderer.Init();
		int size = 100;
		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				Entity entity = new Entity();
				entity.setPosition(new Vector2f(x*32, y*32));
				renderer.addEntity(entity);
			}
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}
	View view;
	
	@Override
	public void Resize()
	{
		view.setViewWidth(super.getWidth()/32);
		view.setViewHeight(super.getHeight()/32);
	}

	@Override
	public void Update()
	{
		super.Update();
		int x = Mouse.getX(); // will return the X coordinate on the Display.
		int y = Mouse.getY(); // will return the Y coordinate on the Display.
		float xSpeed =0;
		float ySpeed= 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			xSpeed = 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			xSpeed = -1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			ySpeed = -1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			ySpeed = 1;
		}
		view.moveView(new Vector2f(xSpeed,ySpeed));
	}

	Renderer renderer = new Renderer();

	@Override
	public void Render()
	{
		super.Render();
		renderer.Render(view);
		Display.setTitle("FPS:"+super.fpsCounter.getFPS());
	}

	@Override
	public void Destroy()
	{
		// GL11.glDeleteLists(quad.getDisplayID(), GL_COMPILE);
	}
}