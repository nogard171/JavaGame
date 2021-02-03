package core;

import java.awt.Point;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Object;
import utils.Renderer;

public class Chunk {
	private int objectID = -1;
	private int maskID = -1;
	private boolean needsUpdating = false;
	private Point index = new Point(0, 0);
	private Vector2f position = null;
	public HashMap<Point, Object> objects = new HashMap<Point, Object>();
	public HashMap<Point, Object> mask = new HashMap<Point, Object>();

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
		position = new Vector2f((float) (index.x * 32 * GameData.chunkSize.getWidth()),
				(float) (index.y * 32 * GameData.chunkSize.getHeight()));
		build();
	}

	public void build() {
		if (objectID == -1) {
			Renderer.beginBatch(GL11.GL_QUADS);
			for (int x = 0; x < GameData.chunkSize.getWidth(); x++) {
				for (int y = 0; y < GameData.chunkSize.getHeight(); y++) {
					Object obj = objects.get(new Point(x, y));
					if (obj != null) {
						// System.out.println("test");
						int chunkX = (int) (index.x * GameData.chunkSize.getWidth() * 32);
						int chunkY = (int) (index.y * GameData.chunkSize.getHeight() * 32);
						Renderer.renderTexture(chunkX + (x * 32), chunkY + (y * 32), obj.getTexture());
					}
				}
			}
			objectID = Renderer.endBatch();
		}
		if (maskID == -1) {
			Renderer.beginBatch(GL11.GL_QUADS);
			for (int x = 0; x < GameData.chunkSize.getWidth(); x++) {
				for (int y = 0; y < GameData.chunkSize.getHeight(); y++) {
					Object obj = mask.get(new Point(x, y));
					if (obj != null) {
						int chunkX = (int) (index.x * GameData.chunkSize.getWidth() * 32);
						int chunkY = (int) (index.y * GameData.chunkSize.getHeight() * 32);
						Renderer.renderTexture(chunkX + (x * 32), chunkY + (y * 32), obj.getTexture());
					}
				}
			}
			maskID = Renderer.endBatch();
		}
	}

	public void update() {
		if (needsUpdating) {
			build();
			needsUpdating = false;
		}
	}

	public void render() {
		boolean validDraw = false;
		if (GameData.view.intersects(position.x, position.y, GameData.chunkSize.getWidth() * 32,
				GameData.chunkSize.getHeight() * 32)) {
			validDraw = true;
		}
		if (validDraw) {
			Renderer.drawBatch(objectID);
		}
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.drawQuad(position.x, position.y, (int) ((float) GameData.chunkSize.getWidth() * (float) 32),
				(int) ((float) GameData.chunkSize.getHeight() * (float) 32), Color.red);
		Renderer.endDraw();

		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	public void renderMask() {
		boolean validDraw = false;
		if (GameData.view.intersects(position.x, position.y, GameData.chunkSize.getWidth() * 32,
				GameData.chunkSize.getHeight() * 32)) {
			validDraw = true;
		}
		if (validDraw) {
			Renderer.drawBatch(maskID);
		}
	}
}
