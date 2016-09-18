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

	public static void drawString(String s, int x, int y) {
		int startX = x;
		GL11.glBegin(GL11.GL_POINTS);
		for (char c : s.toLowerCase().toCharArray()) {
			if (c == 'a') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'b') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 5);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				x += 8;
			} else if (c == 'c') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'd') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'e') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'f') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'g') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				GL11.glVertex2f(x + 5, y + 3);
				GL11.glVertex2f(x + 7, y + 3);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'h') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'i') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 3, y + i);
				}
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'j') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 0);
				}
				GL11.glVertex2f(x + 1, y + 3);
				GL11.glVertex2f(x + 1, y + 2);
				GL11.glVertex2f(x + 1, y + 1);
				x += 8;
			} else if (c == 'k') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 6, y + 8);
				GL11.glVertex2f(x + 5, y + 7);
				GL11.glVertex2f(x + 4, y + 6);
				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 2, y + 4);
				GL11.glVertex2f(x + 2, y + 3);
				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 'l') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				x += 7;
			} else if (c == 'm') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 3, y + 6);
				GL11.glVertex2f(x + 2, y + 7);
				GL11.glVertex2f(x + 4, y + 5);

				GL11.glVertex2f(x + 5, y + 6);
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 4, y + 5);
				x += 8;
			} else if (c == 'n') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y + 7);
				GL11.glVertex2f(x + 2, y + 6);
				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 5, y + 3);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				x += 8;
			} else if (c == 'o' || c == '0') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'p') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				x += 8;
			} else if (c == 'q') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					if (i != 1)
						GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					if (i != 6)
						GL11.glVertex2f(x + i, y + 0);
				}
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 'r') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);

				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 's') {
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);
				GL11.glVertex2f(x + 1, y + 5);
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y);
				}
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 1, y + 2);
				x += 8;
			} else if (c == 't') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'u') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'v') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 6, y + i);
				}
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 5, y + 1);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				x += 7;
			} else if (c == 'w') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == 'x') {
				for (int i = 1; i <= 7; i++)
					GL11.glVertex2f(x + i, y + i);
				for (int i = 7; i >= 1; i--)
					GL11.glVertex2f(x + i, y + 8 - i);
				x += 8;
			} else if (c == 'y') {
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 4, y + 1);
				GL11.glVertex2f(x + 4, y + 2);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 4, y + 4);

				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 2, y + 6);
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 8);

				GL11.glVertex2f(x + 5, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 8);
				x += 8;
			} else if (c == 'z') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + i);
				}
				GL11.glVertex2f(x + 6, y + 7);
				x += 8;
			} else if (c == '1') {
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				GL11.glVertex2f(x + 3, y + 7);
				x += 8;
			} else if (c == '2') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);

				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				x += 8;
			} else if (c == '3') {
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '4') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 1);
				}
				for (int i = 0; i <= 4; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == '5') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 4; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);

				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);

				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 2, y + 4);
				x += 8;
			} else if (c == '6') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				x += 8;
			} else if (c == '7') {
				for (int i = 0; i <= 7; i++)
					GL11.glVertex2f(x + i, y + 8);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);

				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 1, y);
				x += 8;
			} else if (c == '8') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '9') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 5; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 1, y + 0);
				x += 8;
			} else if (c == '.') {
				GL11.glVertex2f(x + 1, y);
				x += 2;
			} else if (c == ':') {
				GL11.glVertex2f(x + 1, y);
				GL11.glVertex2f(x + 1, y + 7);
				x += 2;
			} else if (c == ',') {
				GL11.glVertex2f(x + 1, y);
				GL11.glVertex2f(x + 1, y + 1);
				x += 2;
			} else if (c == '\n') {
				y -= 10;
				x = startX;
			} else if (c == ' ') {
				x += 8;
			}
		}
		GL11.glEnd();
	}

	public void Init() {
		super.Init();
		if (objects == null) {

			objects = new Object[width][depth][height];
			for (int x = 0; x < objects.length; x++) {
				for (int y = 0; y < objects[x].length; y++) {
					for (int z = 0; z < objects[x][y].length; z++) {
						Object obj = new Object();
						if (z == 0) {
							obj.type = Type.UNKNOWN;
						} else if (z == 1) {
							if (x + y == 10) {
								obj.type = Type.DIRT;
							}
							if (x == 9) {
								obj.type = Type.WATER;
							}
						} else {

							obj.type = Type.BLANK;
							if (x == 1 && z <= 3) {
								if (z > 1) {
									obj.shadow = false;
								}
								obj.type = Type.TREE;
							}
							if (x == 1 && z == 4) {
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
	Type type = Type.BLANK;

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
								mouse.getPosition().getY() + (height * 32) - camy)) {
							test.isHovered = true;
							type = test.type;
							if (z - 1 >= 0 && objects[x][y][z - 1].type == Type.TREE) {
								type = Type.LEAVES;
							}

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

		if (obj != null && keyboard.keyPressed(Keyboard.KEY_LCONTROL)) {
			Object object = new Object();
			object.setPosition(objects[objX][objY][objZ].position.getX(),
					objects[objX][objY][objZ].position.getY() + 16);
			objects[objX][objY][objZ] = object;
		} else if (obj != null) {
			Object object = new Object();
			object.setPosition(objects[objX][objY][objZ].position.getX(),
					objects[objX][objY][objZ].position.getY() + 16);
			objects[objX][objY][objZ + 1] = object;
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

		if (keyboard.keyOnce(Keyboard.KEY_LEFT) ) {
			season--;
		}
		if (keyboard.keyOnce(Keyboard.KEY_RIGHT)) {
			season++;
			
		}
		if(season>3)
		{
			season = 0;
		}
		else if(season<0)
		{
			season = 3;
		}
		Color seasonColor = new Color(Type.getSpriteData(Type.GRASS).colors[0]);
		if(season == 0)
		{
			seasonColor = new Color(224,160,64);
		}
		
		for (int x = 0; x < objects.length; x++) {
			for (int y = 0; y < objects[x].length; y++) {
				for (int z = 0; z < objects[x][y].length; z++) {
					Object test = objects[x][y][z];
					test.setColor(seasonColor);
				}
			}
		}
		// System.out.println("Level:" + level);
		super.keyboard.endPoll();
	}

	int season = 0;

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
				for (int z = 0; z < newHeight; z++) {
					objects[x][y][z].setPosition(newX + ((x * 32) + (y * 32)),
							newY + ((y * 16) - (x * 16) - (z * -32)));
					if (newHeight - 1 == z) {
						objects[x][y][z].setTransparent(0.5f);
					}

					objects[x][y][z].Render();
				}
			}
		}

		GL11.glTranslatef(-camx, -camy + (10 * 32), 0);
		GL11.glPopMatrix();
		GL11.glColor3f(1, 1, 1);
		drawString("Type:" + type, 0, 0);
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}