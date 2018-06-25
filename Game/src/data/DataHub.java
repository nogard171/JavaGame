package data;

import classes.Position;
import engine.FPSEngine;
import engine.Input;
import engine.Window;

public class DataHub {
	
	//debugger based variables
	public static boolean _debug = false;

	// Display based variables
	public static Window window = new Window();

	public static int _defaultWidth = 800;
	public static int _defaultHeight = 600;
	public static int _width = 800;
	public static int _height = 600;
	public static int _fps = 300;
	public static boolean _fullscreen = false;
	public static boolean _resizable = true;
	public static boolean _vsync = false;

	// input based variables
	public static Input input = new Input();
	public static Position mouse = new Position(0, 0);
	public static boolean[] mouseButtons = null;
	public static boolean[] keys = null;
}
