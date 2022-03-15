package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;

import classes.Chunk;
import classes.Index;
import classes.MouseIndex;
import classes.Size;
import classes.UIControl;

public class EngineData {
	// Window data
	public static int width = 800;
	public static int height = 600;
	public static boolean isResizable = true;
	public static int inactiveFPS = 30;
	public static int targetFPS = -1;

	// World Data
	public static Size chunkSize = new Size(16, 16, 16);
	public static HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	public static LinkedList<Index> loadChunks = new LinkedList<Index>();
	public static LinkedList<Chunk> renderedChunks = new LinkedList<Chunk>();
	public static LinkedList<Chunk> cleanupChunks = new LinkedList<Chunk>();
	public static int[][] globalMapData;
	public static List path;

	// UI Data
	public static boolean blockInput = false;
	public static boolean showTelematry = true;

	// public static MouseIndex hover;

	public static HashMap<String, TrueTypeFont> fonts = new HashMap<String, TrueTypeFont>();
	public static HashMap<String, UIControl> controls = new HashMap<String, UIControl>();
	public static String selectedMaterial = "";

}
