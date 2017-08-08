import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GLDisplayMode {

	public DisplayMode getDisplayMode(int width, int height) throws LWJGLException {
		return getDisplayMode(width, height, Display.getDisplayMode().getFrequency(), false);
	}

	public DisplayMode getDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException {
		return getDisplayMode(width, height, Display.getDisplayMode().getFrequency(), fullscreen);
	}

	public DisplayMode getDisplayMode(int width, int height, int refreshRate) throws LWJGLException {
		return getDisplayMode(width, height, refreshRate, false);
	}

	public DisplayMode getDisplayMode(int width, int height, int refreshRate, boolean fullscreen)
			throws LWJGLException {
		DisplayMode displayMode = null;
		DisplayMode[] modes;

		modes = Display.getAvailableDisplayModes();
		for (int i = 0; i < modes.length; i++) {
			if (modes[i].getWidth() == width && modes[i].getHeight() == height && modes[i].isFullscreenCapable()
					&& modes[i].getFrequency() == refreshRate) {
				displayMode = modes[i];
			}
		}
		Display.setFullscreen(fullscreen);

		if (displayMode == null) {
			System.out.println("Error trying to load display mode, using defaults");
			displayMode = getDisplayMode(width, height);
		}
		return displayMode;
	}
}
