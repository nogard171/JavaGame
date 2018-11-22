package core;

import java.awt.Point;
import java.awt.Polygon;
import java.util.HashMap;

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
	private int dlId = -1;
	private GLObject[][][] objects;
	private Polygon bounds;
	int currentLevel = 0;

	public GLChunk(int x, int y, int z) {
		position = new Point((int) ((x - z) * (size.x * 32)), (int) (((z + x) * (size.x * 16)) + (y * (size.y * 32))));
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

					GLObject obj = new GLObject(GLType.BLANK);

					if (y > 1) {
						obj = new GLObject(GLType.GRASS);
					}

					// obj.bounds = poly;
					objects[x][z][y] = obj;
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
							Vector2f out = isOutFacing(x, y, z);

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

							GLSpriteData sprite = sprites.get("BLANK");
							if (vec.x == 1 || vec.y == 1 || vec.z == 1) {
								sprite = sprites.get(obj.getType().toString());
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
		} 
		if (y +1<size.y) {
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


		if (x - 1 >0) {
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
		

		if (z - 1 >0) {
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

	public int getLevel() {
		return this.currentLevel;
	}

	public void changeLevel(int level) {
		this.currentLevel = level;
		this.updateDisplayList();
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
		GL11.glCallList(this.dlId);
	}
}
