import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;

public class Window
{
	private int WIDTH = 1024;
	private int HEIGHT = 768;
	private boolean FULLSCREEN = true;
	private int FPS = 120;
	private String TITLE = "";

	private DisplayMode displayMode = null;

	FPSCounter fpsCounter = null;
	boolean wireframe = false;

	public void Init()
	{
		fpsCounter = new FPSCounter();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// Enable alpha transparency
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void Update()
	{

	}

	public void Render()
	{
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		if (wireframe)
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
	}

	public void SetupOpenGL()
	{
		glViewport(0,0,this.getWidth(),this.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this.WIDTH, 0, this.HEIGHT, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void ShowDisplay()
	{
		this.SetupWindow();
		this.SetupOpenGL();
		this.Init();
		while (!Display.isCloseRequested())
		{
			this.CheckResize();
			fpsCounter.updateFPS();
			this.Update();
			GL11.glLoadIdentity();
			this.Render();

			Display.update();
			Display.sync(this.FPS);
		}
		//this.Destroy();
	}

	private void CheckResize()
	{
		if (Display.getWidth()!=this.WIDTH||Display.getHeight()!=this.HEIGHT)
		{
			this.WIDTH= Display.getWidth();
			this.HEIGHT= Display.getHeight();
			this.SetupOpenGL();
			this.Resize();
		}
	}

	public int getWidth()
	{
		return WIDTH;
	}

	public void setWidth(int wIDTH)
	{
		WIDTH = wIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}

	public void setHeight(int hEIGHT)
	{
		HEIGHT = hEIGHT;
	}

	public void Resize()
	{
	}

	private void SetupWindow()
	{
		try
		{
			this.displayMode = new DisplayMode(this.WIDTH, this.HEIGHT);
			Display.setDisplayMode(this.displayMode);
			Display.setFullscreen(this.FULLSCREEN);
			Display.setResizable(true);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void Destroy()
	{
		Display.destroy();
	}
}