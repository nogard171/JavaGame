package utils;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import data.EngineData;
import threads.GameThread;

public class Window {

	public static void create() {
		try {
			Display.setDisplayMode(new DisplayMode(EngineData.width, EngineData.height));
			Display.setResizable(EngineData.isResizable);
			Display.create();

			setup();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void setup() {
		EngineData.width = Display.getWidth();
		EngineData.height = Display.getHeight();

		GL11.glViewport(0, 0, EngineData.width, EngineData.height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, EngineData.width, EngineData.height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

	}

	public static void update() {
		//EngineData.targetFPS = 120;
		if (Display.wasResized()) {
			setup();
		}
		if (Display.isCloseRequested()) {
			GameThread.close();
		}
	}

	public static void render() {
		Display.update();
		if (!Display.isActive()) {
			Display.sync(EngineData.inactiveFPS);
		} else if (EngineData.targetFPS != -1) {
			Display.sync(EngineData.targetFPS);
		}

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

	}

	public static void destroy() {
		Display.destroy();
	}

	public static int getWidth() {
		return EngineData.width;
	}

	public static int getHeight() {
		return EngineData.height;
	}
}
