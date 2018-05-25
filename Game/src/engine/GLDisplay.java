package engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class GLDisplay
{
	// width and height of the display
	static int WIDTH = 800;
	static int HEIGHT = 600;
	int MAXFPS = 120;
	boolean FPSLIMITER = false;
	// the farest rendering distance
	int FARVIEW = 30;
	int FOV = 45;


	public void CreateWindow()
	{
		this.createGLWindow();
		SetupOpenGL();
		this.Setup();
		this.loop();
	}

	
	
	private void SetupOpenGL()
	{
		//
		// TODO Auto-generated method stub
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective((float)FOV, ((float)WIDTH / (float)HEIGHT), 0.1f, FARVIEW);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//glEnable(GL_CULL_FACE);
		
		

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void Update()
	{
		
	}

	public void SetupControllers()
	{
		
	}

	public void Render()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

	}

	
	private void createGLWindow()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("A fresh display!");
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	public void Setup()
	{
		Mouse.setGrabbed(true);
	}

	public void loop()
	{
		while (!Display.isCloseRequested())
		{
			GL11.glLoadIdentity();
			this.Update();
			this.Render();
			Display.update();
			if(this.FPSLIMITER)
			{
				Display.sync(MAXFPS);
			}
		}
		this.Destroy();
	}

	public void Destroy()
	{
		Mouse.setGrabbed(false);
		
		Display.destroy();
		System.exit(0);
	}
}
