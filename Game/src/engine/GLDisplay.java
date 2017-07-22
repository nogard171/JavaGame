package engine;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.io.File;

import javax.swing.JOptionPane;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Utils.ErrorHandler;

public class GLDisplay {

	private int WIDTH = 800;
	private int HEIGHT = 600;
	private int FPS = 60;
	private boolean FPS_LIMITER = true;
	private String TITLE = "";
	private DisplayMode DISPLAYMODE = null;
	private boolean RESIZABLE = true;
	GLFramesPerSecond fps;
	private boolean close = false;
	private String systemEnableFile ="system/scripts/system_enables.lua";

	public void Create() {
		try {
			this.DISPLAYMODE = new DisplayMode(WIDTH, HEIGHT);
			Display.setDisplayMode(this.DISPLAYMODE);
			Display.setResizable(RESIZABLE);
			Display.setTitle(TITLE);
			Display.create();
			this.setupGL();
			this.Setup();
			while (!Display.isCloseRequested()) {
				if (close) {
					break;
				}
				this.Update();
				this.Render();
				Display.update();
				if (FPS_LIMITER) {
					Display.sync(FPS);
				}
			}
		} catch (LWJGLException e) {
			new ErrorHandler();
			ErrorHandler.LogError(e.getMessage());
		}
		this.Destroy();
	}

	private void setupGL() {
		fps = new GLFramesPerSecond();
		fps.start();
		// this sets up the viewport for rendering.
		this.SetupViewPort();
		// System.out.println("Enagle:" + GL11.GL_BLEND);

		File f = new File(systemEnableFile);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("System enables beign used from script");
			LuaValue chunk;
			Globals globals = JsePlatform.standardGlobals();
			globals.set("window", CoerceJavaToLua.coerce(this));
			chunk = globals.loadfile(this.systemEnableFile);
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void glEnable(int enableID) {
		GL11.glEnable(enableID);
	}

	public void Update() {
		GLFramesPerSecond.updateFPS();
		Display.setTitle("FPS:" + fps.fps);
		if (Display.wasResized()) {
			this.SetupViewPort();
		}
		if (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				this.close = true;
			}
		}
	}

	private void SetupViewPort() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void Render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void Destroy() {
		Display.destroy();
	}

	public void Setup() {

	}

}
