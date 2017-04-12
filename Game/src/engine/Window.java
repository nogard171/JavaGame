package engine;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import utils.MouseHandler;

public class Window
{
	private int WIDTH = 800;
	private int HEIGHT = 600;
	private int FPS = 120;
	private String TITLE ="";
	private DisplayMode displayMode = null;

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	public void CreateDisplay()
	{
		try
		{
			displayMode = new DisplayMode(WIDTH, HEIGHT);
			Display.setDisplayMode(displayMode);
			Display.setResizable(true);
			Display.setTitle(TITLE);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void onInit()
	{

	}

	public void AdjusterView()
	{
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void onUpdate()
	{
		if (Display.wasResized())
		{
			this.AdjusterView();
		}
		updateFPS(); // update FPS Counter
		MouseHandler.poll();
	}

	public void onRender()
	{
		
	}

	public void onDestroy()
	{
		Display.destroy();
	}

	public void SetupOpenGL()
	{
		// init OpenGL
		this.AdjusterView();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		this.EnableGL();
	}

	public void EnableGL()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	public void Start()
	{
		this.CreateDisplay();
		this.SetupOpenGL();
		this.onInit();

		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested())
		{
			this.onUpdate();
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			this.onRender();

			Display.update();
			Display.sync(FPS);
		}
		this.onDestroy();
	}
}