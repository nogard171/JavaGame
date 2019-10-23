package data;

import classes.Size;

import java.util.HashMap;
import java.util.List;

import classes.Chunk;
import classes.Index;
import classes.Object;

public class WorldData {
	public static HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	public static int[][] globalMapData;
	public static List path;
}
