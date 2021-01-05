package core;

import java.awt.Point;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import classes.Object;
import utils.Renderer;

public class Chunk {
	private int batchID = -1;
	private boolean needsUpdating = false;
	private Point index = new Point(0, 0);
	public HashMap<Point, Object> objects = new HashMap<Point, Object>();

	public Chunk() {
	}

	public Chunk(int chunkX, int chunkY) {
		index = new Point(chunkX, chunkY);
	}

	public Point getIndex() {
		return index;
	}

	public void setIndex(int x, int y) {
		index = new Point(x, y);
	}

	public void init() {
		for (int x = 0; x < GameData.chunkSize.getWidth(); x++) {
			for (int y = 0; y < GameData.chunkSize.getHeight(); y++) {
				Object obj = new Object();
				obj.texture = "grass";
				objects.put(new Point(x, y), obj);
			}
		}
		build();
	}

	public void build() {
		if (batchID == -1) {
			Renderer.beginBatch(GL11.GL_QUADS);
			for (int x = 0; x < GameData.chunkSize.getWidth(); x++) {
				for (int y = 0; y < GameData.chunkSize.getHeight(); y++) {
					Object obj = objects.get(new Point(x, y));
					if (obj != null) {
						System.out.println("test");
						Renderer.renderTexture(x * 33, y * 33, obj.getTexture());
					}
				}
			}
			batchID = Renderer.endBatch();
		}
	}

	public void update() {
		if (needsUpdating) {
			build();
			needsUpdating = false;
		}
	}

	public void render() {
		Renderer.drawBatch(batchID);
	}
}
