package Engine;

import org.lwjgl.Sys;

public class GLFramesPerSecond {
	private static long lastFrame;
	private static int old_fps;
	public static int fps = 0;
	private static long lastFPS;

	public void startFPS() {
		getDelta();
		lastFPS = getTime();
	}

	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = old_fps;
			old_fps = 0;
			lastFPS += 1000;
		}
		old_fps++;
	}
}
