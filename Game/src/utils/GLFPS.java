package utils;

public class GLFPS {
	static long lastFPS;
	static long lastFrame;
	static int oldFPS;
	public static int fps = 0;
	public static float currentDelta = 0;

	public static float GetCurrentDelta() {
		return currentDelta;
	}

	public static long GetTime() {
		return System.nanoTime() / 1000000;
	}

	public static float GetDelta() {
		long time = GetTime();
		float delta = (float) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public static int GetFPS() {
		return fps;
	}

	public static void Start() {
		lastFPS = GetTime();
	}

	public static void Update() {
		if (GetTime() - lastFPS > 1000) {
			fps = oldFPS;
			oldFPS = 0;
			lastFPS += 1000;
		}
		oldFPS++;
		currentDelta = GetDelta();
	}
}
