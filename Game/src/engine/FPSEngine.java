package engine;

import org.lwjgl.Sys;

public class FPSEngine {
	private static long lastFrame;
	private static int fps;
	private static long lastFPS;
	private static double delta;
	private static int currentFPS;

	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void initilize() {
		getDelta();
		lastFPS = getTime();
	}

	public static void update() {
		delta = getDelta();
		updateFPS();
	}

	public static int getFPS() {
		return currentFPS;
	}
}
