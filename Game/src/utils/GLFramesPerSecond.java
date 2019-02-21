package utils;

import org.lwjgl.Sys;

public class GLFramesPerSecond {

	static long lastFPS;
	static long lastFrame;
	static int oldFPS;
	public static int fps = 0;
	public static float currentDelta = 0;

	public static float getCurrentDelta() {
		return currentDelta;
	}

	public static long getTime() {
		return System.nanoTime() / 1000000;
	}

	public static float getDelta() {
		long time = getTime();
		float delta = (float) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public static int getFPS() {
		return fps;
	}

	public static void start() {
		lastFPS = getTime();
	}

	public static void update() {
		if (getTime() - lastFPS > 1000) {
			fps = oldFPS;
			oldFPS = 0;
			lastFPS += 1000;
		}
		oldFPS++;
		currentDelta = getDelta();
	}
}
