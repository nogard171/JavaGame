package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Window {

	private static int width = 800;
	private static int height = 600;
	private static int vsync = 60;
	private static int loseFocus = 0;

	public static void create() {
		setupProperties();
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setResizable(Boolean.parseBoolean(GameData.config.getProperty("window.resize")));
			Display.create();

			viewport();
		} catch (LWJGLException e) {
			destroy();
		}
	}

	private static void setupProperties() {

		width = Integer.parseInt(GameData.config.getProperty("window.width"));
		height = Integer.parseInt(GameData.config.getProperty("window.height"));
		if (GameData.config.containsKey("window.vsync")) {
			vsync = Integer.parseInt(GameData.config.getProperty("window.vsync"));
		}
		if (GameData.config.containsKey("window.lose_focus")) {
			loseFocus = Integer.parseInt(GameData.config.getProperty("window.lose_focus"));
		}
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static boolean wasResized() {
		return Display.wasResized();
	}

	private static void viewport() {
		width = Display.getWidth();
		height = Display.getHeight();

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
		if (loseFocus > 0 && !hasFocus()) {
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
