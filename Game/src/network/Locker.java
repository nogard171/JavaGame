package network;


import java.awt.Point;
import java.util.ArrayList;

import objects.Player;


public class Locker {
	public static String ipAddress = "";
	public static String sendLine ="";
	public static String recieveLine ="";
	public static String username = "";
	public static String proticol;
	public static Player player = new Player();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static boolean grid = false;
	public static int clientWidth = 0;
	public static int clientHeight = 0;
}
