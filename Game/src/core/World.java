package core;

import java.awt.Point;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import utils.Generator;
import utils.Renderer;

public class World {

	public void init() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Chunk chunk = Generator.generateChunk(x, y);
				chunk.init();
				GameData.chunks.put(chunk.getIndex(), chunk);
			}
		}
	}

	int chunkXMin = 0;
	int chunkXMax = 0;
	int chunkYMin = 0;
	int chunkYMax = 0;

	public void update() {
		PlayerController.update();
		ViewController.update();
		double chunkX = Math.ceil(((float) Window.getWidth() / (float) 32) / (float) GameData.chunkSize.getWidth());
		double chunkY = Math.ceil(((float) Window.getHeight() / (float) 32) / (float) GameData.chunkSize.getHeight());
		int chunkWidth = (int) (chunkX / 2);
		int chunkHeight = (int) (chunkY / 2);
		int playerChunkX = (int) GameData.player.getChunkIndex().getX();
		int playerChunkY = (int) GameData.player.getChunkIndex().getY();
		chunkXMin = playerChunkX - (chunkWidth);
		chunkXMax = playerChunkX + (chunkWidth + 1);
		chunkYMin = playerChunkY - (chunkHeight);
		chunkYMax = playerChunkY + (chunkHeight + 1);

		for (int x = chunkXMin; x < chunkXMax; x++) {
			for (int y = chunkYMin; y < chunkYMax; y++) {
				Point key = new Point(x, y);
				if (GameData.chunks.containsKey(key)) {
					Chunk chunk = GameData.chunks.get(key);
					if (chunk != null) {
						chunk.update();
					}
				} else if (GameData.autoGenerateWorld) {
					Chunk chunk = Generator.generateChunk(x, y);
					chunk.init();
					GameData.chunks.put(chunk.getIndex(), chunk);
				}
			}
		}
	}

	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-GameData.view.x, -GameData.view.y, 0);

		for (int x = chunkXMin; x < chunkXMax; x++) {
			for (int y = chunkYMin; y < chunkYMax; y++) {
				Point key = new Point(x, y);
				Chunk chunk = GameData.chunks.get(key);
				if (chunk != null) {
					// System.out.println("chunk: " + x + "," + y);
					chunk.render();
				}
			}
		}
		GL11.glPopMatrix();

		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.renderTexture((Window.getWidth() / 2) - 16, (Window.getHeight() / 2) - 16, "dirt", Color.white, false);
		Renderer.endDraw();
	}

	public void destroy() {

	}
}
