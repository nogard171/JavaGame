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

public class Window
{
	private int WIDTH = 800;
	private int HEIGHT = 600;
	private int FPS = 300;
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
			displayMode = this.getDisplayMode(WIDTH, HEIGHT);
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
	public DisplayMode getDisplayMode(int width, int height)
	{
		return getDisplayMode(width,  height,Display.getDisplayMode().getFrequency(),false);
	}
	public DisplayMode getDisplayMode(int width, int height, boolean fullscreen)
	{
		return getDisplayMode(width,  height,Display.getDisplayMode().getFrequency(),fullscreen);
	}
	public DisplayMode getDisplayMode(int width, int height, int refreshRate)
	{
		return getDisplayMode(width,  height,refreshRate,false);
	}
	public DisplayMode getDisplayMode(int width, int height,int refreshRate, boolean fullscreen)
	{
		DisplayMode displayMode = null;
		DisplayMode[] modes;
		try
		{
			modes = Display.getAvailableDisplayModes();
			for (int i = 0; i < modes.length; i++)
			{
				if (modes[i].getWidth() == width && modes[i].getHeight() == height && modes[i].isFullscreenCapable()&&modes[i].getFrequency() == refreshRate)
				{
					displayMode = modes[i];
				}
			}
			Display.setFullscreen(fullscreen);
		} catch (LWJGLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(displayMode==null)
		{
			System.out.println("Error trying to load display mode, using defaults");
			displayMode = getDisplayMode(width,height);
		}
		return displayMode;
	}

	public void Init()
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

	public void Update(double delta)
	{
		if (Display.wasResized())
		{
			this.AdjusterView();
		}
		updateFPS(); // update FPS Counter
		MouseHandler.poll();
	}

	public void Render()
	{
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void Destroy()
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
		this.Init();

		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested())
		{
			double delta = getDelta();
			this.Update(delta);
			this.Render();

			Display.update();
			Display.sync(FPS);
		}
		this.Destroy();
	}
}