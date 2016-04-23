import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Window {
	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	/** is VSync Enabled */
	boolean vsync;

	DisplayMode initDisplay = null;
	int displayWidth = 800;
	int displayHeight = 600;
	int displayFPS = 60;
	boolean fullscreen = false;

	public void start() {
		Init();
		Keyboard();
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			Update(delta);
			Render();
			Display.update();
			Display.sync(displayFPS); // cap fps to 60fps
		}
		Display.destroy();
		System.exit(0);
	}

	int keyCount = 256;
	KeyState[] keys = {};
	int[] keyPressed = {};
	public enum KeyState {

		RELEASED, // Not down

		PRESSED, // Down, but not the first time

		ONCE // Down for the first time

	}

	public void Keyboard() {
		keys = new KeyState[keyCount];
		keyPressed = new int[keyCount];
		for (int i = 0; i < keyCount; i++) {
			keys[i] = KeyState.RELEASED;
			keyPressed[i] = 0;
		}
	}

	public void poll() {
		for (int i = 0; i < keyCount; i++) {
			if (Keyboard.isKeyDown(i)&&keys[i] != KeyState.ONCE&&keys[i] != KeyState.PRESSED) {
				keys[i] = KeyState.ONCE;
				keyPressed[i]++;
			} else if (Keyboard.isKeyDown(i)) {
				keys[i] = KeyState.PRESSED;
				keyPressed[i]++;
			}else {
				keys[i] = KeyState.RELEASED;
			}
		}
	}

	public boolean keyPressed(int key) {
		return ((keys[key] == KeyState.PRESSED) ? true : false);
	}
	public boolean keyOnce(int key) {
		return ((keys[key] == KeyState.ONCE) ? true : false);
	}

	public void Update(int delta) {

		/*
		 * while (Keyboard.next()) { if (Keyboard.getEventKeyState()) { if
		 * (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) { try { if
		 * (Display.getDisplayMode() != initDisplay) {
		 * Display.setDisplayMode(initDisplay); } } catch (LWJGLException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 * System.exit(0); } else if (Keyboard.getEventKey() == Keyboard.KEY_F)
		 * { fullscreen = !Display.isFullscreen(); setDisplayMode(displayWidth,
		 * displayHeight,fullscreen); } else if (Keyboard.getEventKey() ==
		 * Keyboard.KEY_V) { vsync = !vsync; Display.setVSyncEnabled(vsync); }
		 * else if (Keyboard.getEventKey() == Keyboard.KEY_1) { displayWidth =
		 * 800; displayHeight = 600; setDisplayMode(displayWidth,
		 * displayHeight,fullscreen); } else if (Keyboard.getEventKey() ==
		 * Keyboard.KEY_2) { displayWidth = 1024; displayHeight = 768;
		 * setDisplayMode(displayWidth, displayHeight, fullscreen); } } }
		 */
		mousePosition = new Point(Mouse.getX(), displayHeight - Mouse.getY());
		updateFPS(); // update FPS Counter
	}

	Point mousePosition = new Point(0, 0);

	/**
	 * Set the display mode to be used
	 * 
	 * @param width
	 *            The width of the display required
	 * @param height
	 *            The height of the display required
	 * @param fullscreen
	 *            True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
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
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, displayWidth, displayHeight, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void Render() {
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

	}

	public void Init() {
		// TODO Auto-generated method stub
		initDisplay = Display.getDisplayMode();
		try {
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
	}
}
