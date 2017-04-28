import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class GLWindow
{
	// display things
	DisplayMode _initDisplay = null;
	int _defaultWidth = 800;
	int _defaultHeight = 600;
	int _width = 800;
	int _height = 600;
	int _fps = 120;
	boolean resizable = true;

	// inputs
	MouseInput mouse = null;
	KeyboardInput keyboard = null;

	public void start()
	{
		this.onLoadConfiguration();

		this.setupGLWindow();

		this.onInitilization();

		keyboard = new KeyboardInput();
		mouse = new MouseInput();

		while (!Display.isCloseRequested())
		{
			if (Display.wasResized())
			{
				this.onResized();
			}

			this.onUpdate();
			this.onRender();
			Display.update();
			Display.sync(this._fps);
		}
		this.onDestroy();
	}

	public void onLoadConfiguration()
	{
		try
		{
		      Properties p = new Properties();
		      p.load(new FileInputStream("system/config.ini"));
		      this._defaultWidth = Integer.parseInt(p.getProperty("default_width"));
		      this._defaultHeight=Integer.parseInt(p.getProperty("default_height"));
		      this._fps = Integer.parseInt(p.getProperty("default_fps"));
		}
	    catch (Exception e) 
		{
	      System.out.println(e);
	    }
	}

	public void onInitilization()
	{

	}

	public void setupGLWindow()
	{
		// set the initial display, so the game can revert back to original
		// display.
		this._initDisplay = Display.getDisplayMode();
		try
		{
			// set the display to the display width and display height
			this._initDisplay = new DisplayMode(this._defaultWidth, this._defaultHeight);

			Display.setDisplayMode(this._initDisplay);
			// set the display resizable if the resizable is true
			Display.setResizable(resizable);
			// create the display.
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		// init opengl
		this.onInitilizeGL();

		this.getDelta();
		this.lastFPS = this.getTime();
	}

	public void onInitilizeGL()
	{
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this._width, 0, this._height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

		this.onEnableGL();

	}

	private void onEnableGL()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void onResized()
	{
		this._height = Display.getHeight();
		this._width = Display.getWidth();

		GL11.glViewport(0, 0, this._width, this._height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this._width, 0, this._height, 1, -1);
	}

	public void onUpdate()
	{
		int delta = getDelta();

		Display.setTitle("FPS:" + this._fps);
		this.keyboard.startPoll();
		this.mouse.poll(this._height);
		this.updateFPS(); // update FPS Counter
	}

	public void onRender()
	{
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void onDestroy()
	{
		Display.destroy();
		System.exit(0);
	}

	// migrate this to a rendering sort of class

	public void renderQuad(GLQuad quad)
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		if(quad.getTextureID()!=-1)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, quad.getTextureID());
		}
		GL11.glCallList(quad.getDisplayID());
		GL11.glEnd();
	}

	// migrate this to another class

	// fps things
	long lastFrame;
	int fps;
	long lastFPS;

	public int getDelta()
	{
		long time = this.getTime();
		int delta = (int) (time - this.lastFrame);
		this.lastFrame = time;
		return delta;
	}

	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void updateFPS()
	{
		if (this.getTime() - this.lastFPS > 1000)
		{
			// Display.setTitle("FPS: " + fps);
			this.fps = 0;
			this.lastFPS += 1000;
		}
		this.fps++;
	}

}
