package core;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import classes.MaterialData;
import classes.ModelData;
import classes.TextureData;

public class GameData {

	public static Properties config = new Properties();

	// Input variables
	public static int mainAction = 0;
	public static int secondaryAction = 0;
	public static int[] mouseActions = new int[2];

	// Telemetry
	public static boolean activeFPS = true;
	public static int fps = 0;
	public static boolean activeCount = true;
	public static int renderCount = 0;

	// Renderer variables
	public static Rectangle view;
	public static Texture texture;
	public static HashMap<String, TextureData> textureData = new HashMap<String, TextureData>();	
	public static HashMap<Integer, TrueTypeFont> fonts = new HashMap<Integer, TrueTypeFont>();

	
	public static HashMap<String, MaterialData> materialData = new HashMap<String, MaterialData>();
	public static HashMap<String, ModelData> modelData = new HashMap<String, ModelData>();
}
