package util;

public class FrameRate {
	private String frameRate;
	private long lastTime;
	private long delta;
	public int frameCount;

	public void initialize() {
		lastTime = System.currentTimeMillis();
		frameRate = "FPS 0";
	}
	public int getDelta()
	{
		return (int)delta;
	}
	public void calculate() {
		long current = System.currentTimeMillis();
		delta += current - lastTime;
		lastTime = current;
		frameCount++;
		if (delta > 1000) {
			delta -= 1000;
			frameRate = String.format("FPS %s", frameCount);
			frameCount = 0;
		}
	}

	public String getFrameRate() {
		return frameRate;
	}
}
