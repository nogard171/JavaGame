package utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
//custom library
import data.EngineData;
import threads.BaseThread;
import threads.GameThread;

public class Window {
	// local tracker for the screenshot function
	private static boolean takingScreenShot = false;

	public static void create() {
		try {
			// setup default display mode with width and height only
			DisplayMode newMode = new DisplayMode(EngineData.width, EngineData.height);
			int highestWidth = -1;
			int highestHeight = -1;
			int highestFreq = -1;
			// loop through all possible display modes based on the monitor data
			for (DisplayMode dm : Display.getAvailableDisplayModes()) {
				// setup variables for data needed
				int dmWidth = dm.getWidth();
				int dmHeight = dm.getHeight();
				int dmFreq = dm.getFrequency();
				// load the supported display modes into the engine data for use in the
				// settings.
				EngineData.supportedDisplayModes.add(newMode);
				if (EngineData.width > -1 && EngineData.height > -1) {
					if (dmWidth == EngineData.width && dmHeight == EngineData.height && dmFreq == 60
							&& dm.isFullscreenCapable()) {
						// set the newMode to the found display mode
						newMode = dm;
						// check if fullscreen is enabled
						if (EngineData.isFullscreen) {
							// set fullscreen to true
							Display.setFullscreen(true);
						}
					}
				} else {
					if ((dmFreq >= highestFreq && dmWidth >= highestWidth && dmHeight >= highestHeight)) {
						// this will help look for highest freq supported
						highestWidth = dmWidth;
						highestHeight = dmHeight;
						highestFreq = dmFreq;
						// set the newMode to the found display mode
						newMode = dm;
					}
					// check if fullscreen is enabled
					if (EngineData.isFullscreen) {
						// set fullscreen to true
						Display.setFullscreen(true);
					}
				}
			}
			// set the width, height and frequency based on the displaymode selected
			EngineData.width = newMode.getWidth();
			EngineData.height = newMode.getHeight();
			EngineData.frequency = newMode.getFrequency();
			// setup basic display mode
			Display.setDisplayMode(newMode);
			// set is the display is resizeable
			Display.setResizable(EngineData.isResizable);
			// create the display
			Display.create();
			// setup the opengl content
			setup();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void setup() {
		// set the width and height in the engine data
		EngineData.width = Display.getWidth();
		EngineData.height = Display.getHeight();
		// setup the viewport with the height and width in the engine data
		GL11.glViewport(0, 0, EngineData.width, EngineData.height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, EngineData.width, EngineData.height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// setup the enables for the use of generic games
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// set the background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public static void update() {
		// if the display was resized adjust the viewport
		if (Display.wasResized()) {
			setup();
		}
		// if the display was closed ,proceed to close the base thread.
		if (Display.isCloseRequested()) {
			BaseThread.close();
		}
	}

	public static void render() {
		// update the display
		Display.update();
		// if the display is not active limit fps to desired fps. default=30
		if (!Display.isActive()) {
			Display.sync(EngineData.inactiveFPS);
		} // if the game is paused, sync to the pause fps. default=30
		else if (EngineData.pauseGame) {
			Display.sync(EngineData.pauseFPS);
		} // if the target fps is not -1 sync to the target fps. default=-1
		else if (EngineData.targetFPS != -1) {
			Display.sync(EngineData.targetFPS);
		}
		// partly future use screen shot, disable clear when taking a screenshot.
		if (!takingScreenShot) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		} else {
			// process to take a screenshot and save to file=save.png
			processScreenShot();
			takingScreenShot = false;
		}
		// fixes pixel perfect textures, so the textures are not blurry
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
	}

	// destroy the display
	public static void destroy() {
		Display.destroy();
	}

	// return the width of the display
	public static int getWidth() {
		return EngineData.width;
	}

	// return the height of the display
	public static int getHeight() {
		return EngineData.height;
	}

	// return if the display was resized
	public static boolean wasResized() {
		return Display.wasResized();
	}

	// proceed to take screenshot
	public static void takeScreenShot() {
		if (EngineData.allowScreenShot) {
			takingScreenShot = true;
		}
	}

	// actual function to get data in the lwjgl display and output to a
	// bufferedimage for save later in the function.
	private static BufferedImage processScreenShot() {
		// Creating an rbg array of total pixels
		int[] pixels = new int[Window.getWidth() * Window.getHeight()];
		int bindex;
		// allocate space for RBG pixels
		ByteBuffer fb = ByteBuffer.allocateDirect(Window.getWidth() * Window.getHeight() * 3);
		// grab a copy of the current frame contents as RGB
		GL11.glReadPixels(0, 0, Window.getWidth(), Window.getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, fb);
		BufferedImage imageIn = new BufferedImage(Window.getWidth(), Window.getHeight(), BufferedImage.TYPE_INT_RGB);
		// convert RGB data in ByteBuffer to integer array
		for (int i = 0; i < pixels.length; i++) {
			bindex = i * 3;
			pixels[i] = ((fb.get(bindex) << 16)) + ((fb.get(bindex + 1) << 8)) + ((fb.get(bindex + 2) << 0));
			System.out.println("Pixel:" + pixels[i]);
		}
		// Allocate colored pixel to buffered Image
		imageIn.setRGB(0, 0, Window.getWidth(), Window.getHeight(), pixels, 0, Window.getWidth());
		// Creating the transformation direction (horizontal)
		AffineTransform at = AffineTransform.getScaleInstance(1, -1);
		at.translate(0, -imageIn.getHeight(null));
		// Applying transformation
		AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage imageOut = opRotated.filter(imageIn, null);
		try {
			ImageIO.write(imageOut, "png", new File("save.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		takingScreenShot = false;
		return imageOut;
	}
}
