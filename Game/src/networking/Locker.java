package networking;

import java.util.ArrayList;

import objects.KeyBindings;
import objects.Player;
import util.Map;

public class Locker {
	public static String location="";
	public static boolean fullscreen = false;
	public static boolean changeKeyBindings = false;
	public static KeyBindings keys = new KeyBindings(0,0,0,0);
	public static String showMessage ="";
	public static String changeKey = "";
	//this is the player object
	public static Map map = new Map();
	public static Player player = new Player();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static String sendLine ="";
	public static String recieveLine ="";
	public static String username;
	public static String proticol;
}
