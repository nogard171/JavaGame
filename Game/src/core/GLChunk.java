package core;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.Main;

public class GLChunk {
	public Point position;
	public Vector2f size = new Vector2f(16, 16);
	private int dlId = -1;
	private GLObject[][] objects;
	private Polygon bounds;
	int currentLevel = 0;
	boolean needsUpdating = false;
	boolean isEmpty = false;

	public GLChunk(int x, int y) {
		position = new Point(x, y);
		this.setupChunk();
	}

	public void setupChunk() {
		objects = new GLObject[(int) size.x][(int) size.y];
		int xCount = (int) size.x;
		int yCount = (int) size.y;

		this.updateBounds();

		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {

				GLObject obj = new GLObject(GLType.BLANK);

				obj = new GLObject(GLType.GRASS);

				objects[x][y] = obj;
			}

		}
	}

	public void updateBounds() {
		bounds = new Polygon();
		bounds.addPoint(position.x, position.y);
		bounds.addPoint((int) (position.x + (this.size.x * 32)), position.y);
		bounds.addPoint((int) (position.x + (this.size.x * 32)), (int) (position.y + (this.size.y * 32)));
		bounds.addPoint(position.x, (int) (position.y + (this.size.y * 32)));
	}

	int renderCount = 0;

	public void updateDisplayList() {
		this.isEmpty = false;
		this.updateBounds();

		HashMap<String, GLSpriteData> sprites = Main.sprites;

		renderCount = 0;
		dlId = GL11.glGenLists(1);
		GL11.glNewList(dlId, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_QUADS);
		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				GLObject obj = objects[x][y];
				if (obj != null) {
					Color color = new Color(128, 128, 128, 255);
					if (obj.getType() == GLType.BLANK) {
					} else if (obj.getType() == GLType.GRASS) {
						color = new Color(128, 255, 128, 255);
					}
					if (obj.getType() != GLType.BLANK) {

						GLSpriteData sprite = sprites.get(obj.getType().toString());

						if (sprite != null) {

							renderObject(sprite, x, y);
							renderCount++;
						}

					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();

	}

	private void renderObject(GLSpriteData spriteData, int x, int y) {
		if (spriteData != null) {
			GL11.glColor3f(1, 1, 1);

			int posX = position.x + (x * 32);
			int posY = position.y + (y * 32);

			GL11.glTexCoord2f(spriteData.textureData.x, spriteData.textureData.y);
			GL11.glVertex2f(posX, posY);
			GL11.glTexCoord2f(spriteData.textureData.x + spriteData.textureData.z, spriteData.textureData.y);
			GL11.glVertex2f(posX + spriteData.size.getWidth(), posY);
			GL11.glTexCoord2f(spriteData.textureData.x + spriteData.textureData.z,
					spriteData.textureData.y + spriteData.textureData.w);
			GL11.glVertex2f(posX + spriteData.size.getWidth(), posY + spriteData.size.getHeight());
			GL11.glTexCoord2f(spriteData.textureData.x, spriteData.textureData.y + spriteData.textureData.w);
			GL11.glVertex2f(posX, posY + spriteData.size.getHeight());

		}
	}

	Vector2f hover;

	public void update() {
		if (needsUpdating) {
			updateDisplayList();
			needsUpdating = false;
		}
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

	public void render() {
		if (this.dlId == -1) {
			needsUpdating = true;
		}
		GL11.glCallList(this.dlId);
	}
}
