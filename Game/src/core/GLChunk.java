package core;

import java.awt.Point;
import java.awt.Polygon;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class GLChunk {
	private int dlId = -1;
	private GLObject[][][] objects;
	private Polygon bounds;
	int currentLevel = 0;

	public GLChunk() {

		this.setupChunk();
	}

	public void setupChunk() {
		objects = new GLObject[16][16][16];
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;

		bounds = new Polygon();
		bounds.addPoint(32, 0 + (this.currentLevel * 32));
		bounds.addPoint(17 * 32, 16 * 16 + (this.currentLevel * 32));
		bounds.addPoint(32, (16 + 16) * 16 + (this.currentLevel * 32));
		bounds.addPoint(-15 * 32, 16 * 16 + (this.currentLevel * 32));

		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = 0; y < yCount; y++) {
					objects[x][z][y] = new GLObject(GLType.GRASS);
				}
			}
		}
		updateDisplayList();
	}

	int renderCount = 0;

	public void updateDisplayList() {
		renderCount = 0;
		dlId = GL11.glGenLists(1);
		GL11.glNewList(dlId, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_QUADS);
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;
		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = yCount - 1; y > 0; y--) {
					GLObject obj = objects[x][z][y];
					if (obj != null) {
						Color color = new Color(128, 128, 128);
						if (obj.getType() == GLType.BLANK) {
						} else if (obj.getType() == GLType.GRASS) {
							color = new Color(128, 255, 128);
						}
						if (obj.getType() != GLType.BLANK) {
							if (isVisible(x, y, z)) {
								System.out.println("tes: " + y);
								renderObject(color, x, y, z);
								renderCount++;
							}
						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}

	private boolean isVisible(int x, int y, int z) {
		boolean visible = true;
		if (y - 1 > 0) {
			GLObject top = objects[x][z][y - 1];
			if (top.getType() != GLType.BLANK) {
				visible = false;
			}
		}

		if (y - 1 > 0 && x < objects[0].length - 1 && z < objects[0].length - 1) {
			GLObject adj = objects[x + 1][z + 1][y - 1];
			if (adj.getType() != GLType.BLANK) {
				visible = false;
			}
		}

		return visible;
	}

	private void renderObjectShadow(int x, int y, int z) {

		int posX = (x - z) * 32;
		int posY = (y - 1) * 32;
		int posZ = ((z + x) * 16) + posY;

		GL11.glColor4f(1, 0, 0, 0.5f);

		GL11.glVertex2f(posX + 64, posZ + 48);
		GL11.glVertex2f(posX + 96, posZ + 64);
		GL11.glVertex2f(posX + 64, posZ + 80);
		GL11.glVertex2f(posX + 32, posZ + 64);
	}

	private void renderObject(Color color, int x, int y, int z) {

		int posX = (x - z) * 32;
		int posY = (y - 1) * 32;
		int posZ = ((z + x) * 16) + posY;

		GL11.glColor3f((float) color.getRed() / (float) 255, (float) color.getGreen() / (float) 255,
				(float) color.getBlue() / (float) 255);

		GL11.glVertex2f(posX + 32, posZ);
		GL11.glVertex2f(posX + 64, posZ + 16);
		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX, posZ + 16);

		GL11.glColor3f(((float) color.getRed() / (float) 255) * 0.75f, ((float) color.getGreen() / (float) 255) * 0.75f,
				((float) color.getBlue() / (float) 255) * 0.75f);

		GL11.glVertex2f(posX, posZ + 16);
		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX + 32, posZ + 64);
		GL11.glVertex2f(posX, posZ + 48);

		GL11.glColor3f(((float) color.getRed() / (float) 255) * 0.5f, ((float) color.getGreen() / (float) 255) * 0.5f,
				((float) color.getBlue() / (float) 255) * 0.5f);

		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX + 64, posZ + 16);
		GL11.glVertex2f(posX + 64, posZ + 48);
		GL11.glVertex2f(posX + 32, posZ + 64);
	}

	Vector2f hover;

	public void update(Vector2f camera) {
		Point mousePoint = new Point(Mouse.getX() - (int) camera.x,
				Display.getHeight() - Mouse.getY() - (int) camera.y);
		boolean mouseInChunk = bounds.contains(mousePoint);
		if (mouseInChunk) {
			hover = null;
			int xCount = objects.length;
			int zCount = objects[0].length;
			int yCount = objects[0][0].length;
			for (int x = 0; x < xCount; x++) {
				for (int z = 0; z < zCount; z++) {
					GLObject obj = objects[x][z][this.currentLevel];
					if (obj != null) {
						int posX = (x - z) * 32;
						int posY = this.currentLevel * 32;
						int posZ = ((z + x) * 16) + posY;
						Polygon poly = new Polygon();
						poly.addPoint(posX + 32, posZ);
						poly.addPoint(posX + 64, posZ + 16);
						poly.addPoint(posX + 32, posZ + 32);
						poly.addPoint(posX, posZ + 16);

						if (poly.contains(mousePoint)) {
							if (objects[x][z][this.currentLevel] != null) {
								hover = new Vector2f(x, z);
							}
							break;

						}
					}
				}
			}

			if (Mouse.isButtonDown(0) && hover != null) {
				System.out.println("test2: " + this.currentLevel);
				objects[(int) hover.x][(int) hover.y][this.currentLevel + 1].setType(GLType.BLANK);
				updateDisplayList();
			}
		}
	}

	public boolean isBlank(int x, int y, int z) {
		boolean visible = false;
		GLObject top = objects[x][z][y];
		if (top.getType() == GLType.BLANK) {
			visible = true;
		}
		return visible;
	}

	public void render() {
		System.out.println("Count:" + renderCount);
		GL11.glCallList(this.dlId);

		if (hover != null) {

			int posX = (int) ((hover.x - hover.y) * 32);
			int posY = this.currentLevel * 32;
			int posZ = (int) (((hover.y + hover.x) * 16) + posY);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex2f(posX + 32, posZ);
			GL11.glVertex2f(posX + 64, posZ + 16);
			GL11.glVertex2f(posX + 32, posZ + 32);
			GL11.glVertex2f(posX, posZ + 16);
			GL11.glEnd();

			if (isBlank((int) hover.x, 1, (int) hover.y)) {
				GL11.glColor4f(1, 0, 0, 0.5f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(posX + 32, posZ);
				GL11.glVertex2f(posX + 64, posZ + 16);
				GL11.glVertex2f(posX + 32, posZ + 32);
				GL11.glVertex2f(posX, posZ + 16);
				GL11.glEnd();
			}

		}
	}
}
