import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
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
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {

	Object[][][] objects = null;
	int width = 10;
	int height = 2;
	int depth = 10;

	public void Init() {
		super.Init();
		// go.setPosition(100, 100);
		if (objects == null) {
			int newX = 400;
			int newY = 400;
			objects = new Object[width][depth][height];
			for (int x = 0; x < objects.length; x++) {
				for (int y = 0; y < objects[x].length; y++) {
					for (int z = 0; z < objects[x][y].length; z++) {
						objects[x][y][z] = new Object();
						objects[x][y][z].setPosition(newX + ((x * 32) + (y * -32)),
								newY + ((y * -16) - (x * 16) - (z * -32)));
						if (z == 0) {

							if (x + y == 10) {
								objects[x][y][z].type = Type.DIRT;
							}
							if (x == 9) {
								objects[x][y][z].type = Type.WATER;
							}

						} else {

							objects[x][y][z].type = Type.BLANK;
							if (x == 1) {
								objects[x][y][z].type = Type.TREE;
							}
						}

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

	@Override
	public void Update(int delta) {
		super.Update(delta);

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
					}
				}
			}
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
		super.keyboard.endPoll();
	}

	int camx = 0;
	int camy = 0;

	@Override
	public void Resized() {
		super.Resized();
	}

	Object ground = new Object();
	Object go = new Object();

	public void Render() {
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(camx, camy - (height * 32), 0);

		for (int x = 0; x < objects.length; x++) {
			for (int y = 0; y < objects[x].length; y++) {
				for (int z = 0; z < objects[x][y].length; z++) {
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