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
		Chunk chunk = Generator.generateChunk(0, 0);
		chunk.init();
		GameData.chunks.put(chunk.getIndex(), chunk);
	}

	public void update() {
		int playerChunkX = (int) GameData.player.getChunkIndex().getX();
		int playerChunkY = (int) GameData.player.getChunkIndex().getY();
		for (int x = playerChunkX - 2; x < playerChunkX + 2; x++) {
			for (int y = playerChunkY - 2; y < playerChunkY + 2; y++) {
				Point key = new Point(x, y);
				if (GameData.chunks.containsKey(key)) {
					Chunk chunk = GameData.chunks.get(key);
					if (chunk != null) {
						chunk.update();
					}
				}
			}
		}
	}

	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-GameData.player.getX(), -GameData.player.getY(), 0);
		int playerChunkX = (int) GameData.player.getChunkIndex().getX();
		int playerChunkY = (int) GameData.player.getChunkIndex().getY();
		System.out.println("player: " + playerChunkX + "," + playerChunkY);
		for (int x = playerChunkX - 2; x < playerChunkX + 2; x++) {
			for (int y = playerChunkY - 2; y < playerChunkY + 2; y++) {
				Point key = new Point(x, y);
				if (GameData.chunks.containsKey(key)) {
					Chunk chunk = GameData.chunks.get(key);
					if (chunk != null) {
						chunk.render();
					}
				}
			}
		}
		GL11.glPopMatrix();
		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.renderTexture((Window.getWidth() / 2) - 16, (Window.getHeight() / 2) - 16, "grass", Color.white);
		Renderer.endDraw();
	}

	public void destroy() {

	}
}
