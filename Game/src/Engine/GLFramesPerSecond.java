package Engine;

import org.lwjgl.Sys;

public class GLFramesPerSecond {
	/** time at last frame */
	private static long lastFrame;

	/** frames per second */
	private static int old_fps;

	public static int fps = 0;
	/** last fps time */
	private static long lastFPS;

	public void startFPS() {
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public static int getDelta() {
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
	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = old_fps;
			old_fps = 0;
			lastFPS += 1000;
		}
		old_fps++;
	}
}
