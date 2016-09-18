import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {

	Object[][][] objects = null;
	int width = 10;
	int height = 10;
	int depth = 10;
	int level = 0;

	public void Init() {
		super.Init();
		if (objects == null) {

			objects = new Object[width][depth][height];
			for (int x = 0; x < objects.length; x++) {
				for (int y = 0; y < objects[x].length; y++) {
					for (int z = 0; z < objects[x][y].length; z++) {
						Object obj = new Object();
						if (z == 0) {
							if (x + y == 10) {
								obj.type = Type.DIRT;
							}
							if (x == 9) {
								obj.type = Type.WATER;
							}

						} else {

							obj.type = Type.BLANK;
							if (x == 1 && z <= 2) {
								if (z > 1) {
									obj.shadow = false;
								}
								obj.type = Type.TREE;
							}
							if (x == 1 && z == 3) {
								obj.shadow = false;
								obj.type = Type.GRASS;
							}
						}
						objects[x][y][z] = obj;
					}
				}
			}
		}
	}

	public void setTexture(Sprite sprite, String string) {
		try {
			sprite.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(string));
		} catch (RuntimeException ioe) {
			System.out.println(ioe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	float speed = 0;
	float grav = 0;
	boolean moved = false;
	int jump = 0;
	int clicked = 0;
	int tileZ = 0;

	@Override
	public void Update(int delta) {
		super.Update(delta);
		Object obj = null;
		int objX = 0;
		int objY = 0;
		int objZ = 0;
		for (int x = 0; x < objects.length; x++) {
			for (int y = 0; y < objects[x].length; y++) {
				for (int z = 0; z < objects[x][y].length; z++) {
					Object test = objects[x][y][z];
					if (test.type != Type.BLANK) {
						Rectangle rec = new Rectangle(test.position.getX() + 16, test.position.getY(), 32, 32);
						if (rec.contains(mouse.getPosition().getX() - camx,
								mouse.getPosition().getY() + (height * 32) - camy)) {
							test.setColor(new Color(255, 0, 0));
						} else {
							test.resetSprite();
						}

						if (rec.contains(mouse.getPosition().getX() - camx,
								mouse.getPosition().getY() + (height * 32) - camy) &&mouse.mouseDown(0)) {
							test.isHovered = true;
							System.out.println("Z:" + z + "\nTile Z:" + tileZ);
							
						} else {
							test.isHovered = false;
						}
						if (test.isHovered && mouse.mouseDown(0) && insideBounds(x, y, z + 1)) {
							// Object obj = new Object();
							// obj.setPosition(objects[x][y][z].position.getX(),
							// objects[x][y][z].position.getY() + 16);
							// objects[x][y][z + 1] = obj;
							System.out.println("Z:" + z + "\nTile Z:" + tileZ);
							objX = x;
							objY = y;
							objZ = z;
							clicked++;
							tileZ = z;
							obj = objects[x][y][z];
						} else if (!mouse.mouseDown(0)) {
							clicked = 0;
						}
						if ((insideBounds(x, y + 1, z) && objects[x][y + 1][z].type != Type.BLANK)) {
							test.shadow = false;
						}
					}
				}
			}
		}

		if (obj != null&&keyboard.keyPressed(Keyboard.KEY_LCONTROL)) {
			Object object = new Object();
			object.setPosition(objects[objX][objY][objZ].position.getX(),
					objects[objX][objY][objZ].position.getY() + 16);
			objects[objX][objY][objZ] = object;
		}
		else if (obj != null) {
			Object object = new Object();
			object.setPosition(objects[objX][objY][objZ].position.getX(),
					objects[objX][objY][objZ].position.getY() + 16);
			objects[objX][objY][objZ+1] = object;
		}

		if (keyboard.keyPressed(Keyboard.KEY_A)) {
			camx += 10;
		}
		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			camx -= 10;
		}

		if (keyboard.keyPressed(Keyboard.KEY_W)) {
			camy -= 10;
		}
		if (keyboard.keyPressed(Keyboard.KEY_S)) {
			camy += 10;
		}
		if (keyboard.keyOnce(Keyboard.KEY_DOWN) && level > -1) {
			level--;
		}
		if (keyboard.keyOnce(Keyboard.KEY_UP)) {
			level++;
		}

	//	System.out.println("Level:" + level);
		super.keyboard.endPoll();
	}

	private boolean insideBounds(int newX, int newY, int newZ) {
		boolean inside = false;
		if (newX >= 0 && newX < width && newY >= 0 && newY < height && newZ >= 0 && newZ < depth) {
			inside = true;
		}

		return inside;
	}

	int camx = 0;
	int camy = 0;

	@Override
	public void Resized() {
		super.Resized();
	}

	public void Render() {
		super.Render();

		int newX = 400;
		int newY = 400;
		GL11.glPushMatrix();
		GL11.glTranslatef(camx, camy - (height * 32), 0);
		int newHeight = depth;
		if (level < depth - 1) {
			newHeight = level + 2;
		}
		for (int x = 0; x < objects.length; x++) {
			for (int y = objects[x].length - 1; y >= 0; y--) {
				for (int z = 0; z < objects[x][y].length; z++) {
					objects[x][y][z].setPosition(newX + ((x * 32) + (y * 32)),
							newY + ((y * 16) - (x * 16) - (z * -32)));
					if (newHeight - 1 == z) {
						//objects[x][y][z].setTransparent(0.5f);
					}

					objects[x][y][z].Render();
				}
			}
		}

		GL11.glTranslatef(-camx, -camy + (10 * 32), 0);
		GL11.glPopMatrix();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}