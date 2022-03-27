package data;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;

import classes.Chunk;
import classes.Index;
import classes.MouseIndex;
import classes.Size;
import ui.UIControl;

public class EngineData {
	// Window data
	public static int width = 800;
	public static int height =600;
	public static int frequency = -1;
	public static boolean isResizable = true;
	public static boolean isFullscreen = false;
	public static boolean isVsync = false;
	public static LinkedList<DisplayMode> supportedDisplayModes = new LinkedList<DisplayMode>();

	public static int inactiveFPS = 30;
	public static int pauseFPS = 30;
	public static int targetFPS = -1;

	public static boolean allowScreenShot = false;

	// World Data
	public static Size chunkSize = new Size(16, 16, 16);
	public static HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();

	public static LinkedList<Index> loadChunks = new LinkedList<Index>();
	public static LinkedList<Chunk> renderedChunks = new LinkedList<Chunk>();
	public static LinkedList<Chunk> cleanupChunks = new LinkedList<Chunk>();

	public static int[][] globalMapData;
	public static LinkedList<Point> path;

	// UI Data
	public static boolean blockInput = false;
	public static boolean showTelematry = true;
	public static boolean showMainMenu = false;
	public static boolean pauseGame = false;

	// font data so that a font doesn't need to be reloaded everytime it is used and
	// can be optimized to use the same font over again instead.
	public static HashMap<String, TrueTypeFont> fonts = new HashMap<String, TrueTypeFont>();
	// too be removed
	public static HashMap<String, UIControl> controls = new HashMap<String, UIControl>();

	// Backend Data
	public static boolean dataLoaded = false;

	// User based Data
	public static String userFolder = "user/";
	public static String worldFolder = "user/world/";
}
