package core;

import java.awt.Point;
import java.awt.Polygon;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLChunk {
	public Point position;
	public Vector3f size = new Vector3f(100, 16, 100);
	private int dlId = -1;
	private GLObject[][][] objects;
	private Polygon bounds;
	int currentLevel = 0;

	public GLChunk(int x, int y, int z) {
		position = new Point((int) ((x - z) * (size.x * 32)), 0);// ((z + x) * (16 * 16)) + (y * (16 * 32)));
		this.setupChunk();
	}

	public void setupChunk() {
		objects = new GLObject[(int) size.x][(int) size.z][(int) size.y];
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;

		this.updateBounds();

		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = 0; y < yCount; y++) {
					if (y > 1) {
						objects[x][z][y] = new GLObject(GLType.GRASS);
					} else {
						objects[x][z][y] = new GLObject(GLType.BLANK);
					}
				}
			}
		}
		updateDisplayList();
	}

	public void updateBounds() {
		bounds = new Polygon();
		bounds.addPoint(position.x + 32, position.y + (0 + (this.currentLevel * 32)));
		bounds.addPoint((int) (position.x + ((size.x + 1) * 32)),
				(int) (position.y + (size.x * 16 + (this.currentLevel * 32))));
		bounds.addPoint((int) (position.x + (size.x - size.z + 1) * 32),
				(int) (position.y + ((size.z + size.x) * 16 + (this.currentLevel * 32))));
		bounds.addPoint((int) (position.x + ((1 - size.z) * 32)),
				(int) (position.y + (size.z * 16 + (this.currentLevel * 32))));
	}

	int renderCount = 0;

	public void updateDisplayList() {
		this.updateBounds();
		renderCount = 0;
		dlId = GL11.glGenLists(1);
		GL11.glNewList(dlId, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_QUADS);
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;
		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = yCount - 1; y > this.currentLevel; y--) {
					GLObject obj = objects[x][z][y];
					if (obj != null) {
						Color color = new Color(128, 128, 128, 255);
						if (obj.getType() == GLType.BLANK) {
						} else if (obj.getType() == GLType.GRASS) {
							color = new Color(128, 255, 128, 255);
						}
						if (obj.getType() != GLType.BLANK) {
							Vector3f vec = isVisible(x, y, z);
							Vector2f out = isOutFacing(x, y, z);
							if (vec.x == 0 && vec.y == 0 && vec.z == 0) {
								color = new Color(128, 128, 128, 255);
							}
							renderObject(vec, out, color, x, y, z);
							renderCount++;

						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}

	private Vector2f isOutFacing(int x, int y, int z) {
		Vector2f visible = new Vector2f(0, 0);
		boolean topFacing = false;
		if (y - 1 > 0) {
			GLObject top = objects[x][z][y - 1];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					topFacing = true;
				}
			}
		} else {
			topFacing = true;
		}
		if (!topFacing) {
			if (x + 1 < objects.length) {
				GLObject right = objects[x + 1][z][y];
				if (right != null) {
					if (right.getType() == GLType.BLANK) {
						visible.x = 1;
					}
				}
			} else {
				visible.x = 1;
			}

			if (z + 1 < objects[0].length) {
				GLObject top = objects[x][z + 1][y];
				if (top != null) {
					if (top.getType() == GLType.BLANK) {
						visible.y = 1;
					}
				}
			} else {
				visible.y = 1;
			}
		} else {
			visible = new Vector2f(0, 0);
		}
		return visible;
	}

	private Vector3f isVisible(int x, int y, int z) {
		Vector3f visible = new Vector3f(0, 0, 0);
		if (y - 1 > 0) {
			GLObject top = objects[x][z][y - 1];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.y = 1;
				}
			}
		} else {
			visible.y = 1;
		}

		if (x + 1 < objects.length) {
			GLObject top = objects[x + 1][z][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.x = 1;
				}
			}
		} else {
			visible.x = 1;
		}

		if (z + 1 < objects[0].length) {
			GLObject top = objects[x][z + 1][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.z = 1;
				}
			}
		} else {
			visible.z = 1;
		}
		return visible;
	}

	private void renderObject(Vector3f visiblility, Vector2f outFacing, Color color, int x, int y, int z) {

		int posX = (x - z) * 32;
		int posY = (y - 1) * 32;
		int posZ = ((z + x) * 16) + posY;
		Color oldColor = color;
		if (visiblility.y == 1) {// || y == this.currentLevel + 1) {
			GL11.glColor4f((float) color.getRed() / (float) 255, (float) color.getGreen() / (float) 255,
					(float) color.getBlue() / (float) 255, (float) color.getAlpha() / (float) 255);

			GL11.glVertex2f(posX + 32, posZ);
			GL11.glVertex2f(posX + 64, posZ + 16);
			GL11.glVertex2f(posX + 32, posZ + 32);
			GL11.glVertex2f(posX, posZ + 16);
		}
		color = oldColor;
		if (visiblility.z == 1) {// || outFacing.y == 1 || y == this.currentLevel + 1) {
			if (outFacing.y == 1) {
				color = new Color(128, 128, 128);
			}
			GL11.glColor4f(((float) color.getRed() / (float) 255) * 0.75f,
					((float) color.getGreen() / (float) 255) * 0.75f, ((float) color.getBlue() / (float) 255) * 0.75f,
					(float) color.getAlpha() / (float) 255);

			GL11.glVertex2f(posX, posZ + 16);
			GL11.glVertex2f(posX + 32, posZ + 32);
			GL11.glVertex2f(posX + 32, posZ + 64);
			GL11.glVertex2f(posX, posZ + 48);
		}
		color = oldColor;
		if (visiblility.x == 1) {// || outFacing.x == 1 || y == this.currentLevel + 1) {
			if (outFacing.x == 1) {
				color = new Color(128, 128, 128);
			}
			GL11.glColor4f(((float) color.getRed() / (float) 255) * 0.5f,
					((float) color.getGreen() / (float) 255) * 0.5f, ((float) color.getBlue() / (float) 255) * 0.5f,
					(float) color.getAlpha() / (float) 255);

			GL11.glVertex2f(posX + 32, posZ + 32);
			GL11.glVertex2f(posX + 64, posZ + 16);
			GL11.glVertex2f(posX + 64, posZ + 48);
			GL11.glVertex2f(posX + 32, posZ + 64);
		}
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
						int posX = position.x + (x - z) * 32;
						int posY = position.y + this.currentLevel * 32;
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
				// System.out.println("test2: " + this.currentLevel);
				objects[(int) hover.x][(int) hover.y][this.currentLevel + 1].setType(GLType.BLANK);
				updateDisplayList();
			}
		} else {
			hover = null;
		}
		if (Mouse.isButtonDown(1) && hover != null) {
			objects[(int) hover.x][(int) hover.y][this.currentLevel + 1].setType(GLType.GRASS);
			updateDisplayList();
		}
		int mouseWheel = Mouse.getDWheel();
		if (mouseWheel < 0 && this.currentLevel < size.y - 1) {
			this.currentLevel++;
			updateDisplayList();
		}

		if (mouseWheel > 0 && this.currentLevel > 0) {
			this.currentLevel--;
			updateDisplayList();

		}
		// Display.setTitle("Level: " + this.currentLevel);

	}

	public boolean isBlank(int x, int y, int z) {
		boolean visible = false;
		GLObject top = objects[x][z][y];
		if (top.getType() == GLType.BLANK) {
			visible = true;
		}
		return visible;
	}

	public boolean isHoverEmpty() {
		boolean empty = false;
		if (hover != null) {
			int yCount = objects[0][0].length;
			for (int y = yCount - 1; y > this.currentLevel; y--) {
				GLObject top = objects[(int) hover.x][(int) hover.y][y];
				if (top != null) {
					if (top.getType() == GLType.BLANK) {
						empty = true;
						break;
					}
				}
			}
		}
		return empty;
	}

	public void render() {
		GL11.glCallList(this.dlId);
		if (hover != null) {

			int posX = (int) ((hover.x - hover.y) * 32);
			int posY = this.currentLevel * 32;
			int posZ = (int) (((hover.y + hover.x) * 16) + posY);

			int height = 0;
			if (isHoverEmpty()) {
				height = 1;
			}

			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex2f(posX + 32, posZ);
			GL11.glVertex2f(posX + 64, posZ + 16);
			GL11.glVertex2f(posX + 32, posZ + 32);
			GL11.glVertex2f(posX, posZ + 16);
			GL11.glVertex2f(posX + 32, posZ);

			if (isBlank((int) hover.x, 1, (int) hover.y)) {

				GL11.glVertex2f(posX + 32, posZ);
				GL11.glVertex2f(posX, posZ + 16);
				GL11.glVertex2f(posX, posZ + (32 * height) + 16);
				GL11.glVertex2f(posX, posZ + (32 * height) + 16);
				GL11.glVertex2f(posX + 32, posZ + (32 * height) + 32);

				GL11.glVertex2f(posX + 32, posZ + (32 * height) + 32);
				GL11.glVertex2f(posX + 64, posZ + (32 * height) + 16);
				GL11.glVertex2f(posX + 64, posZ + 16);

			}
			GL11.glEnd();

		}
	}
}
