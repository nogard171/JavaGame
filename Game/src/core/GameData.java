package core;

import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class GameData {
	
	public static Properties config = new Properties();
	
	//Input variables
	public static int mainAction = 0;
	public static int secondaryAction = 0;
	
	//Telemetry
	public static boolean activeFPS = true;
	public static int fps = 0;
	
	//Renderer variables
	public static HashMap<String, Texture> slickTextures = new HashMap<String, Texture>();
	public static HashMap<String, Integer> compiledTextures = new HashMap<String, Integer>();
	public static HashMap<Integer,TrueTypeFont> fonts = new HashMap<Integer,TrueTypeFont>();
	
}
