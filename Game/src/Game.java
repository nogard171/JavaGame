import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


import static org.lwjgl.opengl.GL11.*;

public class Game implements Runnable
{
    // ----------- END: Variables added
    // width and height of the display
    int	    width  = 800;
    int	    height = 600;
    // the grid boolean, used for render things as lines.
    boolean grid   = false;
    // the farest rendering distance
    int	    far	   = 100;

    int	    FOV	   = 60;
    String  ip	   = "";

    public void StartGame(String location)
    {

	Thread game = new Thread(this);
	game.start();
    }

    private void initOpenGL()
    {
	// networkClient = new Client();

	// networkClient.start();
	Mouse.setGrabbed(true);
	// TODO Auto-generated method stub
	GL11.glMatrixMode(GL11.GL_PROJECTION);

	GL11.glLoadIdentity();

	GLU.gluPerspective(FOV, (width / height), 0.1f, far);

	GL11.glMatrixMode(GL11.GL_MODELVIEW);

	glEnable(GL_COLOR_MATERIAL);

	GL11.glClearColor(1, 1, 1, 1.0f);

	GLU.gluPerspective(FOV, (width / height), 0.1f, far);

	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glEnable(GL11.GL_TEXTURE_2D);

	glEnable(GL_BLEND);

	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

	obj = new OBJLoader();
	obj.loadData("resources/models/test.obj");
    }

    OBJLoader obj	   = new OBJLoader();
    Terrain   map	   = new Terrain();

    State     state	   = State.SPECTATOR;
    int	      terrainMode  = GL11.GL_QUADS;
    Rectangle renderBounds = new Rectangle(0, 0, 50, 50);

    public void render()
    {

	GL11.glShadeModel(GL11.GL_SMOOTH);

	obj.drawOBJ(terrainMode);

	switch (state)
	{
	    case GAME:
		map.Render(terrainMode, renderBounds);
		break;
	    case SPECTATOR:
		// if grid is true
		map.Render(terrainMode, renderBounds);
		break;
	}

	gui.preRender();
	if ((-camera.position.y) < 2)
	{
	    // gui.drawWater();
	}
	gui.font.drawString(this.width / 2, 0, "Title=",
		org.newdawn.slick.Color.black);

	gui.postRender();

    }

    long       pingSpeed       = 0;

    Interfaces gui;
    int	       fps	       = 0;
    boolean    groundCollision = false;
    float      gravity	       = 0f;
    float      previousHeight  = 0;

    /*------- End Variables added for Fog Test -----------*/
    public void update()
    {
	renderBounds = new Rectangle((int) (-camera.position.x) - (far / 2),
		(int) (-camera.position.z) - (far / 2), (int) (far + 20),
		(int) (far + 20));

	Display.setTitle(
		"YAW: " + camera.getYaw() + " PITCH: " + camera.getPitch());
	// controll camera yaw from x movement fromt the mouse
	camera.setYaw(mouseDX * mouseSensitivity);

	// obj.yaw = -camera.getYaw();
	// controll camera pitch from y movement fromt the mouse
	camera.setPitch(mouseDY * mouseSensitivity);

	if (this.state == State.GAME)
	{

	    if (((-camera.position.y) - 4f) <= map.checkTile(
		    (int) camera.position.x, (int) camera.position.z))
	    {
		groundCollision = true;
		gravity = 0f;
		camera.jumps = 0;
		camera.flyUp(1);
	    }

	    if (((-camera.position.y) - 4.5f) <= map.checkTile(
		    (int) camera.position.x, (int) camera.position.z))
	    {
		groundCollision = true;
		// camera.jumping = false;
		gravity = 0f;
		camera.jumps = 0;
	    }
	    else
	    {
		groundCollision = false;
		if (gravity <= 0.3f && (-camera.position.y) < 2)
		{
		    gravity += 0.05f;
		}
		else if (gravity <= 1f && (-camera.position.y) > 2)
		{
		    gravity += 0.05f;
		}
	    }
	    camera.flyDown(gravity);
	}
	time++;

    }

    boolean		right	 = false;
    boolean		left	 = false;
    boolean		up	 = false;
    boolean		down	 = false;
    boolean		forward	 = false;
    boolean		backward = false;
    int			time	 = 0;
    private static long	lastFrame;

    private static long getTime()
    {
	return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static double getDelta()
    {
	long currentTime = getTime();
	double delta = (double) currentTime - (double) lastFrame;
	lastFrame = getTime();
	return delta;
    }

    float    dx		      = 0.0f;
    float    dy		      = 0.0f;
    float    dt		      = 0.0f;			       // length of
							       // frame
    float    lastTime	      = 0.0f;			       // when the last
							       // frame
							       // was

    float    mouseSensitivity = .5f;
    float    movementSpeed    = 10.0f;			       // move 10 units
							       // per
							       // second
    Camera   camera	      = new Camera(5, -10, 5, 306, 50);
    boolean  mouseFocus	      = true;
    int	     jumpLimit	      = 2;
    Vector3f lastGoodPosition = new Vector3f(0, 0, 0);
    int	     mouseDX	      = 0;
    int	     mouseDY	      = 0;

    public void pollInput(double delta)
    {
	// distance in mouse movement from the last getDX() call.
	mouseDX = Mouse.getDX();
	// distance in mouse movement from the last getDY() call.
	mouseDY = Mouse.getDY();
	// hide the mouse
	Mouse.setGrabbed(mouseFocus);

	// camera.position = new Vector3f(-obj.x,obj.y-10,-obj.z+10f);

	float speed = (float) (delta / 50);

	if ((-camera.position.y) <= 2 && state == State.GAME)
	{
	    speed = speed / 4;
	}

	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && state == State.GAME) // strafe
									    // right
	{
	    speed = speed / 4;
	    camera.height = 1f;
	    down = true;
	}
	else
	{
	    camera.height = 0;
	    down = false;
	}

	// System.out.println(speed);
	if (Keyboard.isKeyDown(Keyboard.KEY_W) && state == State.GAME) // move
								       // forward
	{
	    camera.walkForward(speed);
	    // obj.x+=speed;
	    // obj.x -= speed * (float) Math.sin(Math.toRadians((obj.yaw)));
	    // obj.z -= speed * (float) Math.cos(Math.toRadians((obj.yaw)));
	}
	else if (Keyboard.isKeyDown(Keyboard.KEY_W) && state == State.SPECTATOR) // move
	// forward
	{
	    camera.walkToward(speed / 2);
	    // obj.x+=speed;
	    // obj.x -= speed * (float) Math.sin(Math.toRadians((obj.yaw)));
	    // obj.z -= speed * (float) Math.cos(Math.toRadians((obj.yaw)));
	}
	if (Keyboard.isKeyDown(Keyboard.KEY_S) && state == State.GAME) // move
								       // forward
	{
	    camera.walkBackwards(speed);
	    // obj.x-=speed;
	    // obj.x += speed * (float) Math.sin(Math.toRadians((obj.yaw)));
	    // obj.z += speed * (float) Math.cos(Math.toRadians((obj.yaw)));
	}

	else if (Keyboard.isKeyDown(Keyboard.KEY_S) && state == State.SPECTATOR) // move
	// forward
	{
	    camera.walkAway(speed / 2);
	    // obj.x+=speed;
	    // obj.x -= speed * (float) Math.sin(Math.toRadians((obj.yaw)));
	    // obj.z -= speed * (float) Math.cos(Math.toRadians((obj.yaw)));
	}
	if (Keyboard.isKeyDown(Keyboard.KEY_A)) // strafe
						// left
	{
	    camera.strafeLeft(speed);
	    // obj.x += speed * (float) Math.sin(Math.toRadians(obj.yaw - 90));
	    // obj.z += speed * (float) Math.cos(Math.toRadians(obj.yaw - 90));
	}
	if (Keyboard.isKeyDown(Keyboard.KEY_D)) // strafe
						// right
	{
	    camera.strafeRight(speed);
	    // obj.x -= speed * (float) Math.sin(Math.toRadians(obj.yaw - 90));
	    // obj.z -= speed * (float) Math.cos(Math.toRadians(obj.yaw - 90));
	}
	if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && state == State.GAME
		&& (-camera.position.y) < 2) // strafe
	{
	    if (gravity >= -0.5f)
	    {
		gravity -= 0.06f;
	    }
	    else
	    {
		gravity -= 0.025f;
	    }
	}

	if (Keyboard.isKeyDown(Keyboard.KEY_Z)) // strafe right
	{
	    FOV = 10;
	}
	else
	{
	    FOV = 60;
	}

	if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) // strafe
						    // right
	{
	    // camera.flyUp(speed);
	}

	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) // strafe
	{
	    // camera.flyDown(speed);
	}

	while (Keyboard.next())
	{
	    if (grid)
	    {
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) // strafe right
		{
		    terrainMode = GL11.GL_POINTS;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) // strafe right
		{
		    terrainMode = GL11.GL_LINE_STRIP;
		}
	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_F1)) // strafe right
	    {

	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && state == State.GAME
		    && camera.jumps < 1 && (-camera.position.y) >= 2) // strafe
								      // right
	    {
		camera.jump(2);
		gravity -= 0.75f;

	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
		    && state == State.SPECTATOR) // show grid
	    {
		grid = !grid;

		if (grid)
		{
		    terrainMode = GL11.GL_LINE_STRIP;
		}
		else
		{
		    terrainMode = GL11.GL_QUADS;
		}
	    }

	    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) // strafe right
	    {
		System.exit(0);
	    }

	}
	// networkClient.player.x = camera.position.x;
	// networkClient.player.y = camera.position.y;
	// networkClient.player.z = camera.position.z;
	// camera.grid = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	// set the modelview matrix back to the identity
	GL11.glLoadIdentity();

	// look through the camera before you draw anything
	camera.lookThrough();
    }

    private long lastFPS;

    public static void main(final String[] argv)
    {
	// System.setProperty("org.lwjgl.librarypath",new
	// File("natives").getAbsolutePath());
	// System.out.println("running");
	final Game displayExample = new Game();

	SwingUtilities.invokeLater(new Runnable()
	{
	    @Override
	    public void run()
	    {
		displayExample.StartGame("null");
	    }
	});

    }

    public void start()
    {
	// some startup code
	lastFPS = getTime(); // set lastFPS to current Time
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS()
    {
	if (getTime() - lastFPS > 1000)
	{
	    // Display.setTitle("FPS: " + fps);
	    fps = 0; // reset the FPS counter
	    lastFPS += 1000; // add one second
	}
	fps++;
    }

    boolean closing = false;

    @Override
    public void run()
    {

	try
	{
	    Display.setDisplayMode(new DisplayMode(width, height));
	    Display.setTitle("A fresh display!");
	    Display.create();
	}
	catch (LWJGLException e)
	{
	    e.printStackTrace();
	    Display.destroy();
	    System.exit(1);
	}

	initOpenGL();
	// pingSpeed = new Connector().pingHost("localhost", 80);
	// In initialization code
	getDelta(); // call once before loop to initialise lastFrame
	lastFPS = getTime(); // call before loop to initialise fps timer
	gui = new Interfaces();
	// Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	// font = new TrueTypeFont(awtFont, false);
	// camera.font = font;
	// state = State.GAME;
	while (!Display.isCloseRequested())
	{
	    // In game loop
	    double delta = getDelta();
	    pollInput(delta);
	    // System.out.println(FOV);
	    render();
	    updateFPS();
	    update();

	    Display.update();
	    Display.sync(60);
	    if (closing)
	    {
		break;
	    }
	}
	// System.out.println("DELETE FROM `Clients` WHERE `IP`='" + ip + "'");
	Display.destroy();
	System.exit(0);
    }

}