import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPSCounter
{
	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	int stableFPS = 0;

	public FPSCounter()
	{
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
	}
	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta()
	{
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
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
			stableFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public int getFPS()
	{
		return stableFPS;
	}
}
