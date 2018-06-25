package engine;

import org.lwjgl.Sys;

public class FPSEngine {
	private long lastFrame;
	private int fps;
	private long lastFPS;
	private double delta;
	private int currentFPS;

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initilize() {
		getDelta();
		lastFPS = getTime();
	}

	public void update() {
		//delta = getDelta();
		updateFPS();
	}

	public int getFPS() {
		return currentFPS;
	}
}
