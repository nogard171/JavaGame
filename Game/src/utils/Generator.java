package utils;

import core.Chunk;
import core.GameData;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;

import classes.Object;

public class Generator {
	public static Chunk generateChunk(int chunkX, int chunkY) {
		Chunk chunk = new Chunk(chunkX, chunkY);

		for (int x = 0; x < GameData.chunkSize.width; x++) {
			for (int y = 0; y < GameData.chunkSize.height; y++) {
				Object obj = new Object();
				obj.texture = "grass";
				chunk.objects.put(new Point(x, y), obj);
			}
		}
		return chunk;
	}
}
