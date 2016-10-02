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

		// setTexture(hud.test, "/res/img/window.png");
		this.addSprite("GRASS", "/res/img/grass.png", null);
		this.addSprite("PLAYER", "", new Dimension(32, 64));
		

		for (int x = 0; x < 5; x++) {
			addObject("GRASS",sprites.get("GRASS"),new Vector2f(x * 128, this.Height - 32));
		}

	}

	HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	HashMap<String, Object> objects = new HashMap<String, Object>();

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
	public void addObject(String name, Sprite sprite, Vector2f newPosition) {
		Object newSprite = new Object();
		newSprite.name =name;
		newSprite.position = newPosition;
		if (sprite != null) {
			newSprite.texture = sprite.texture;
			newSprite.faces = sprite.faces;
			newSprite.vectors = sprite.vectors;
		} 		
		objects.put(name, newSprite);		
	}
	public void addSprite(String name, String sprite, Dimension newSize) {
		Sprite newSprite = new Sprite();
		if (sprite != "") {
			newSprite.texture = this.getTexture(sprite);
			if (newSize == null) {
				newSprite.width = newSprite.texture.getImageWidth();
				newSprite.height = newSprite.texture.getImageHeight();
			}
		} else {
			newSprite.color = new Color(255, 0, 0);
		}
		if (newSize != null) {
			newSprite.width = newSize.getWidth();
			newSprite.height = newSize.getHeight();
		}
		sprites.put(name, newSprite);
	}

	float grav = 0;

	@Override
	public void Update(int delta) {
		super.Update(delta);
		float speed = delta / 2;

		grav += 0.098f;
		Sprite player = sprites.get("PLAYER");
		if (position.y + player.height > this.Height) {
			grav = 0;
		}
		if (keyboard.keyPressed(Keyboard.KEY_SPACE)) {
			grav = -5;
		}

		for (Object obj : objects.values()) {
			Sprite sprite = sprites.get(obj.name);

			if (collision(obj,sprite)) {
				sprite.setColor(new Color(255,0,0));
			}
			else
			{
				sprite.setColor( new Color(255,255,255));
			}
		} // (this.Width / 2) - (player.width / 2), (this.Height / 2) -
			// (player.height / 2)

		if (keyboard.keyPressed(Keyboard.KEY_A)) {
			position.x -= speed;
		}
		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			position.x += speed;
		}

		position.y += grav;

		// position.x = x;
		// position.y = y;

		super.keyboard.endPoll();
	}

	public boolean collision(Object obj, Sprite sprite) {
		Sprite player = sprites.get("PLAYER");
		boolean collides = false;
		System.out.println("X:"+position.x+"/"+obj.position.x);
		if((position.x>obj.position.x&&position.x<obj.position.x+(sprite.width/2)))//||(position.x+player.width>obj.position.x&&position.x+player.width<obj.position.x+sprite.width))
		{
			collides= true;
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
	Vector2f position = new Vector2f(0, 0);

	@Override
	public void Render() {
		super.Render();
		GL11.glPushMatrix();
		Sprite player = sprites.get("PLAYER");
		GL11.glTranslatef(position.x, position.y, 0);
		player.Render();
		GL11.glPopMatrix();

		for (Object obj : objects.values()) {
			obj.Render();

		}
	}

	@Override
	public void Destroy() {
		super.Destroy();
		for (Sprite sprite : sprites.values()) {
			sprite.Destroy();
		}
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}