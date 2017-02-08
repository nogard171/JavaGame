import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

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

public class GLWindow
{
	// width and height of the display
	static int WIDTH = 800;
	static int HEIGHT = 600;
	int MAXFPS = 120;
	// the farest rendering distance
	int FARVIEW = 50;
	int FOV = 45;

	private static long lastFrame;
	private long lastFPS;

	float MOUSESENSITIVITY = .5f;
	float MOVEMENTSPEED = 10.0f;
	Camera camera = new Camera(0,0,0);
	boolean MOUSEFOCUS = true;
	int MOUSEDX = 0;
	int MOUSEDY = 0;

	boolean DevControlledCamera = false;

	KeyboardInput keyboard = new KeyboardInput();
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	int fps = 0;

	public void UpdateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0; // reset the FPS counter
			lastFPS += 1000; // add one second
		}
		fps++;
	}

	public void CreateWindow()
	{
		this.createGLWindow();
		SetupOpenGL();
		this.Setup();
		this.SetupControllers();
		this.loop();
	}

	private static long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public double getDelta()
	{
		long currentTime = getTime();
		double delta = (double) currentTime - (double) lastFrame;
		lastFrame = getTime();
		return delta;
	}

	private void SetupOpenGL()
	{
		Mouse.setGrabbed(true);
		// TODO Auto-generated method stub
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective((float)FOV, ((float)WIDTH / (float)HEIGHT), 0.1f, FARVIEW);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//glEnable(GL_COLOR_MATERIAL);
		//glEnable(GL_BLEND);
		 //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		//glEnable(GL_CULL_FACE);
		
		

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void Update(double delta)
	{
		keyboard.poll();
		// distance in mouse movement from the last getDX() call.
		this.MOUSEDX = Mouse.getDX();
		// distance in mouse movement from the last getDY() call.
		this.MOUSEDY = Mouse.getDY();
		if (!DevControlledCamera)
		{
			// hide the mouse
			// controll camera yaw from x movement fromt the mouse
			camera.setYaw(this.MOUSEDX * this.MOUSESENSITIVITY);
			// controll camera pitch from y movement fromt the mouse
			camera.setPitch(this.MOUSEDY * this.MOUSESENSITIVITY);

			float speed = (float) (delta / 100);

			// System.out.println("Angle:" + (angle));
			float x_Speed = 0f;
			float z_Speed = 0;
			float y_Speed = 0f;

			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				x_Speed = speed;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_S)) // move
			{
				x_Speed = -speed;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				z_Speed = speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				z_Speed = -speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				y_Speed = speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{
				y_Speed = -speed;
			}

			this.camera.strafe(z_Speed);
			this.camera.walk(x_Speed);
			this.camera.fly(y_Speed);

			camera.lookAtVector3f(new Vector3f(1,0,1));
			//camera.lookThrough();
		}
	}

	public void SetupControllers()
	{
		Mouse.setGrabbed(this.MOUSEFOCUS);
	}

	public void Render()
	{
		keyboard.end();
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

	}

	public void loop()
	{
		lastFPS = getTime();
		while (!Display.isCloseRequested())
		{
			// set the modelview matrix back to the identit
			GL11.glLoadIdentity();
			double delta = getDelta();
			UpdateFPS();
			this.Update(delta);
			this.Render();
			Display.update();
			Display.sync(MAXFPS);
		}
		this.Destroy();
	}

	public void Destroy()
	{
		Display.destroy();
		System.exit(0);
	}
}
