package core;

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;

public class Window {

	private static int width = 800;
	private static int height = 600;
	private static int vsync = 60;
	private static int loseFocus = 0;
	private static int menuVsync = 0;

	public static void create() {
		setupProperties();
		try {
			// Display.setDisplayMode(new DisplayMode(width, height));
			//
			// Display.setFullscreen(Boolean.parseBoolean(GameData.config.getProperty("window.fullscreen")));

			setDisplayMode(width, height, Boolean.parseBoolean(GameData.config.getProperty("window.fullscreen")));
			Display.setResizable(Boolean.parseBoolean(GameData.config.getProperty("window.resize")));
			Display.create();

			Display.setVSyncEnabled(true);

			viewport();
		} catch (LWJGLException e) {
			destroy();
		}
	}

	public static void updateWindow() {
		setupProperties();
		setDisplayMode(width, height, Boolean.parseBoolean(GameData.config.getProperty("window.fullscreen")));
		Display.setResizable(Boolean.parseBoolean(GameData.config.getProperty("window.resize")));
		viewport();
	}

	public static void setDisplayMode(int width, int height, boolean fullscreen) {

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

	public static ArrayList<Point> getResolutions() {
		ArrayList<Point> resolutions = new ArrayList<Point>();
		try {
			DisplayMode[] displayModes = Display.getAvailableDisplayModes();

			for (DisplayMode displayMode : displayModes) {
				resolutions.add(new Point(displayMode.getWidth(), displayMode.getHeight()));
			}
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resolutions;
	}

	private static void setupProperties() {

		width = Integer.parseInt(GameData.config.getProperty("window.width"));
		height = Integer.parseInt(GameData.config.getProperty("window.height"));
		if (GameData.config.containsKey("window.vsync")) {
			vsync = Integer.parseInt(GameData.config.getProperty("window.vsync"));
		}
		if (GameData.config.containsKey("window.menu_vsync")) {
			menuVsync = Integer.parseInt(GameData.config.getProperty("window.menu_vsync"));
		}
		if (GameData.config.containsKey("window.lose_focus")) {
			loseFocus = Integer.parseInt(GameData.config.getProperty("window.lose_focus"));
		}

		if (GameData.view != null) {
			GameData.view.width = width;
			GameData.view.height = height;
		}
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static boolean wasResized() {
		return Display.wasResized();
	}

	private static void viewport() {

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

	}

	public static void update() {
		if (Display.wasResized()) {
			GameData.config.setProperty("window.width", Display.getWidth() + "");
			GameData.config.setProperty("window.height", Display.getHeight() + "");
			width = Integer.parseInt(GameData.config.getProperty("window.width"));
			height = Integer.parseInt(GameData.config.getProperty("window.height"));
			viewport();
		}
	}

	public static void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

	}

	public static void finalizeRender() {
		Display.update();
		if (GameData.mainMenuShown) {
			Display.sync(menuVsync);
		} else if (loseFocus > 0 && !hasFocus()) {
			Display.sync(loseFocus);
		} else {
			if (vsync > 0) {
				Display.sync(vsync);
			}
		}
	}

	private static boolean hasFocus() {
		return Display.isActive();
	}

	public static void destroy() {
		Display.destroy();
	}

	public static int getHeight() {
		return height;
	}

	public static int getWidth() {
		return width;
	}

}
