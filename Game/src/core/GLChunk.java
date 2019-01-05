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
	public Vector3f size = new Vector3f(16, 16, 16);
	public Vector3f index = new Vector3f();
	private int dlId = -1;
	private GLObject[][][] objects;
	private Polygon bounds;
	int currentLevel = 0;
	boolean needsUpdating = false;
	boolean isEmpty = false;

	public GLChunk(int x, int y, int z) {
		index = new Vector3f(x, y, z);
		position = new Point((int) ((x - z) * (size.x * 32)), (int) (((z + x) * (size.x * 16)) + (y * (size.y * 32))));
		this.setupChunk();
	}

	public void setupChunk() {
		objects = new GLObject[(int) size.x][(int) size.z][(int) size.y];
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;

		this.updateBounds();

		//domidpoint() ;

		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = 0; y < yCount; y++) {

					GLObject obj = new GLObject(GLType.BLANK);

					if (y > map[x][z] - 5) {
						obj = new GLObject(GLType.GRASS);
					}
					objects[x][z][y] = obj;
				}
			}
		}
	}

	// In this array the drawing will be made and stored
	int[][] map = new int[(int) size.x][(int) size.z];
	// use r.nextInt() in the code for random numbers
	Random r = new Random();

	// Here the midpoint code begins.
	public void domidpoint() {
		// Erase the old map array..
		for (int y = 0; y < (int) size.x; y++) {
			for (int x = 0; x < (int) size.z; x++) {
				map[x][y] = 0;
			}
		}
		// Setup points in the 4 corners of the map.
		map[0][0] = (int) size.y;
		map[(int) size.x - 1][0] = (int) size.y;
		map[(int) size.x - 1][(int) size.z - 1] = (int) size.y;
		map[0][(int) size.z - 1] = (int) size.y;
		// Do the midpoint
		midpoint(0, 0, (int) size.x - 1, (int) size.z - 1);
	}

	// This is the actual mid point displacement code.
	public boolean midpoint(int x1, int y1, int x2, int y2) {
		// If this is pointing at just on pixel, Exit because
		// it doesn't need doing}
		if (x2 - x1 < 2 && y2 - y1 < 2)
			return false;

		// Find distance between points and
		// use when generating a random number.
		int dist = (x2 - x1 + y2 - y1);
		int hdist = dist / 2;
		// Find Middle Point
		int midx = (x1 + x2) / 2;
		int midy = (y1 + y2) / 2;
		// Get pixel colors of corners
		int c1 = map[x1][y1];
		int c2 = map[x2][y1];
		int c3 = map[x2][y2];
		int c4 = map[x1][y2];

		// If Not already defined, work out the midpoints of the corners of
		// the rectangle by means of an average plus a random number.
		if (map[midx][y1] == 0)
			map[midx][y1] = ((c1 + c2 + r.nextInt(dist) - hdist) / 2);
		if (map[midx][y2] == 0)
			map[midx][y2] = ((c4 + c3 + r.nextInt(dist) - hdist) / 2);
		if (map[x1][midy] == 0)
			map[x1][midy] = ((c1 + c4 + r.nextInt(dist) - hdist) / 2);
		if (map[x2][midy] == 0)
			map[x2][midy] = ((c2 + c3 + r.nextInt(dist) - hdist) / 2);

		// Work out the middle point...
		map[midx][midy] = ((c1 + c2 + c3 + c4 + r.nextInt(dist) - hdist) / 4);

		// Now divide this rectangle into 4, And call again For Each smaller
		// rectangle
		midpoint(x1, y1, midx, midy);
		midpoint(midx, y1, x2, midy);
		midpoint(x1, midy, midx, y2);
		midpoint(midx, midy, x2, y2);

		return true;
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

	public void updateDisplayList(HashMap<String, GLChunk> chunks) {
		this.isEmpty = false;
		this.updateBounds();
		GLChunk left = null;
		GLChunk right = null;

		if (chunks != null) {
			//left = chunks.get((int) (index.x + 1) + "," + (int) index.y + "," + (int) index.z);
			//right = chunks.get((int) index.x + "," + (int) index.y + "," + (int) (index.z + 1));
		}
		if (left != null) {
			System.out.println(left.index);
		}

		HashMap<String, GLSpriteData> sprites = Main.sprites;

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
							Vector2f out = isOutFacing(x, y, z, left, right);

							if (vec.x == 0 && vec.y == 0 && vec.z == 0) {
								color = new Color(128, 128, 128, 255);
							}

							if (vec.y == 1) {

								int posX = position.x + ((x - z) * 32);
								int posY = position.y + ((this.currentLevel) * 32);
								int posZ = ((z + x) * 16) + posY;

								Polygon poly = new Polygon();
								poly.addPoint(posX + 32, posZ);
								poly.addPoint(posX + 64, posZ + 16);

								poly.addPoint(posX + 32, posZ + 32);
								poly.addPoint(posX, posZ + 16);

								obj.bounds = poly;
							} else {
								obj.bounds = null;
							}

							GLSpriteData sprite = null;
							if (vec.x == 1 || vec.y == 1 || vec.z == 1) {
								sprite = sprites.get(obj.getType().toString());
							}
							else
							{
								//sprite = sprites.get("BLANK");
							}
							if (out.x == 1 || out.y == 1) {
								//sprite = sprites.get("BLANK");
							}
							if (sprite != null) {

								renderObject(sprite, x, y, z);
								renderCount++;
							}
						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
		if (renderCount == 0) {
			this.isEmpty = true;
		}
	}

	private Vector2f isOutFacing(int x, int y, int z, GLChunk leftChunk, GLChunk rightChunk) {

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
				if (leftChunk != null) {
					GLObject leftChunkObject = leftChunk.objects[0][z][y];
					if (leftChunkObject != null) {
						if (leftChunkObject.getType() == GLType.BLANK) {
							visible.x = 1;
						}
					}
				} else {
					visible.x = 1;
				}
			}

			if (z + 1 < objects[0].length) {
				GLObject top = objects[x][z + 1][y];
				if (top != null) {
					if (top.getType() == GLType.BLANK) {
						visible.y = 1;
					}
				}
			} else {
				if (rightChunk != null) {
					GLObject rightChunkObject = rightChunk.objects[x][0][y];
					if (rightChunkObject != null) {
						if (rightChunkObject.getType() == GLType.BLANK) {
							visible.y = 1;
						}
					}
				} else {
					visible.y = 1;
				}
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
		if (y + 1 < size.y) {
			GLObject top = objects[x][z][y + 1];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.y = 1;
				}
			}
		}

		if (x + 1 < objects.length) {
			GLObject top = objects[x + 1][z][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.x = 1;
				}
			}
		}

		if (x - 1 > 0) {
			GLObject top = objects[x - 1][z][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.x = 1;
				}
			}
		}
		if (z + 1 < objects[0].length) {
			GLObject top = objects[x][z + 1][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.z = 1;
				}
			}
		}

		if (z - 1 > 0) {
			GLObject top = objects[x][z - 1][y];
			if (top != null) {
				if (top.getType() == GLType.BLANK) {
					visible.z = 1;
				}
			}
		}

		return visible;
	}

	private void renderObject(GLSpriteData spriteData, int x, int y, int z) {
		if (spriteData != null) {
			GL11.glColor3f(1, 1, 1);

			int posX = position.x + ((x - z) * 32);
			int posY = position.y + ((y - 1) * 32);
			int posZ = ((z + x) * 16) + posY;

			GL11.glTexCoord2f(spriteData.textureData.x, spriteData.textureData.y);
			GL11.glVertex2f(posX, posZ);
			GL11.glTexCoord2f(spriteData.textureData.x + spriteData.textureData.z, spriteData.textureData.y);
			GL11.glVertex2f(posX + spriteData.size.getWidth(), posZ);
			GL11.glTexCoord2f(spriteData.textureData.x + spriteData.textureData.z,
					spriteData.textureData.y + spriteData.textureData.w);
			GL11.glVertex2f(posX + spriteData.size.getWidth(), posZ + spriteData.size.getHeight());
			GL11.glTexCoord2f(spriteData.textureData.x, spriteData.textureData.y + spriteData.textureData.w);
			GL11.glVertex2f(posX, posZ + spriteData.size.getHeight());

		}
	}

	Vector2f hover;

	public void update(Vector2f camera, HashMap<String, GLChunk> chunks) {
		Point mousePoint = new Point(Mouse.getX() - (int) camera.x,
				Display.getHeight() - Mouse.getY() - (int) camera.y);

		boolean mouseInChunk = bounds.contains(mousePoint);
		if (mouseInChunk&&Mouse.isButtonDown(0)) {
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

			if ( hover != null) {
				objects[(int) hover.x][(int) hover.y][this.currentLevel + 1].setType(GLType.BLANK);
				// updateDisplayList();
				needsUpdating = true;
			}
		} else {
			hover = null;
		}
		if (Mouse.isButtonDown(1) && hover != null) {
			objects[(int) hover.x][(int) hover.y][this.currentLevel + 1].setType(GLType.GRASS);
			// updateDisplayList();
			needsUpdating = true;
		}
		int mouseWheel = Mouse.getDWheel();
		if (mouseWheel < 0 && this.currentLevel < size.y - 1) {
			this.currentLevel++;
			// updateDisplayList();
			needsUpdating = true;
		}

		if (mouseWheel > 0 && this.currentLevel > 0) {
			this.currentLevel--;
			// updateDisplayList();
			needsUpdating = true;
		}
		// Display.setTitle("Level: " + this.currentLevel);
		if (needsUpdating) {
			updateDisplayList(chunks);
			needsUpdating = false;
		}
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

	public int getLevel() {
		return this.currentLevel;
	}

	public void changeLevel(int level) {
		this.currentLevel = level;
		needsUpdating = true;
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

		if (hover != null) {

			int posX = this.position.x + (int) ((hover.x - hover.y) * 32);
			int posY = this.position.y + (this.currentLevel * 32);
			int posZ = (int) (((hover.y + hover.x) * 16) + posY);

			int height = 0;
			if (isHoverEmpty()) {
				height = 1;
			}

			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glColor3f(1, 1, 1);
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
		if (this.dlId == -1) {
			needsUpdating = true;
		}
		GL11.glCallList(this.dlId);
	}
}
