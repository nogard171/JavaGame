package utils;

import org.lwjgl.Sys;

public class GLFramesPerSecond {

	long lastFPS;
	long lastFrame;
	int oldFPS;
	public int fps = 0;
	public float currentDelta = 0;

	public float getCurrentDelta() {
		return currentDelta;
	}

	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	public float getDelta() {
		long time = getTime();
		float delta = (float) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public int getFPS() {
		return fps;
	}

	public void start() {
		lastFPS = getTime();
	}

	public void update() {
		if (getTime() - lastFPS > 1000) {
			fps = oldFPS;
			oldFPS = 0;
			lastFPS += 1000;
		}
		oldFPS++;
		currentDelta = getDelta();
	}
}
