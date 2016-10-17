import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {

	public void Init() {
		super.Init();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				addObject("GRASS", "/res/img/grass.png", new Dimension(32, 32), new Vector2f(x * 32, y * 32));
			}
		}
		addObject("PLAYER", "", new Dimension(32, 64), null);
	}

	ArrayList<Object> objects = new ArrayList<Object>();

	public ArrayList<Object> getObjects(String name) {
		ArrayList<Object> newObjects = new ArrayList<Object>();
		for (Object obj : objects) {
			if (obj.name == name) {
				newObjects.add(obj);
			}
		}
		return newObjects;
	}

	public Texture getTexture(String string) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(string));
		} catch (RuntimeException ioe) {
			System.out.println(ioe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		return texture;
	}

	public void addObject(String name, String sprite, Dimension newSize, Vector2f newPosition) {
		Object obj = new Object();
		obj.name = name;
		if (newPosition != null) {
			obj.position = newPosition;
		} else {
			obj.position = new Vector2f(100, 100);
		}
		if (sprite != "") {
			obj.texture = this.getTexture(sprite);
			if (newSize == null) {
				obj.width = obj.texture.getImageWidth();
				obj.height = obj.texture.getImageHeight();
			}
		} else {
			obj.color = new Color(255, 0, 0);
		}
		if (newSize != null) {
			obj.width = newSize.getWidth();
			obj.height = newSize.getHeight();
		} else {
			obj.width = 32;
			obj.height = 32;
		}
		objects.add(obj);
	}

	float grav = 0;

	@Override
	public void Update(int delta) {
		super.Update(delta);
		float speed = delta / 2;

		// (this.Width / 2) - (player.width / 2), (this.Height / 2) -
		// (player.height / 2)
		Object player = getObjects("PLAYER").get(0);
		if (keyboard.keyPressed(Keyboard.KEY_A)) {
			player.position.x -= speed;
		}
		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			player.position.x += speed;
		}
		if (keyboard.keyPressed(Keyboard.KEY_W)) {
			player.position.y -= speed;
		}
		if (keyboard.keyPressed(Keyboard.KEY_S)) {
			player.position.y += speed;
		}

		if (player.position.x < camera.x + 100) {

			camera.x -= speed;
		}
		if (player.position.x > (camera.x + super.Width) - 132) {

			camera.x += speed;
		}

		if (player.position.y + (player.height / 2) < camera.y + 100) {

			camera.y -= speed;
		}
		if (player.position.y > (camera.y + super.Height) - 132) {

			camera.y += speed;
		}

		for (Object obj : objects) {
			if (collision(obj, player)) {
				obj.setColor(new Color(255,0,0));
			}
			else
			{
				obj.setColor(new Color(255,255,255));
			}
		}

		// position.x = x;d
		// position.y = y;

		super.keyboard.endPoll();
	}

	public boolean collision(Object obj, Object obj2) {
		boolean collides = false;
		System.out.println("X:" + obj2.position.x + "/" + obj.position.x);
		if ((obj2.position.x >= obj.position.x && obj2.position.x <= obj.position.x + obj.width )||
				(obj2.position.x+ obj2.width >= obj.position.x && obj2.position.x + obj2.width<= obj.position.x + obj.width ))
		{
			collides = true;
		}
		return collides;
	}

	@Override
	public void Resized() {
		super.Resized();
	}

	int x = 0;
	int y = 0;

	int time = 0;
	// int move = 0;
	Random ran = new Random();
	boolean move_test2 = false;
	Vector2f camera = new Vector2f(0, 0);

	@Override
	public void Render() {
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);
		// System.out.println("Count:"+objects.size());
		for (Object obj : objects) {
			obj.Render();
		}
		GL11.glPopMatrix();
	}

	@Override
	public void Destroy() {
		super.Destroy();
		for (Object obj : objects) {
			obj.Destroy();
		}
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}