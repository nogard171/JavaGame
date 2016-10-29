import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
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

public class Game extends GLWindow
{

	public void Init()
	{
		super.Init();

		Sprite sprite = new Sprite();
		sprite.texture = this.getTexture("res/img/grass.png");
		sprites.put("GRASS", sprite);

		Sprite sprite2 = new Sprite();
		sprite2.texture = this.getTexture("res/img/dirt.png");
		sprites.put("DIRT", sprite2);

		Sprite sprite3 = new Sprite();
		sprite3.texture = this.getTexture("res/img/guy.png");
		sprite3.sprite_size = new Point(32, 64);
		sprites.put("PLAYER", sprite3);

		for (int x = 0; x < 20; x++)
		{
			for (int y = 0; y < 20; y++)
			{
				Object obj = new Object();
				obj._type = "GRASS";
				if (x + y % 7 == 0)
				{
					obj._type = "DIRT";
				}

				obj.SetPosition(x * 32, y * 32);
				objects.add(obj);
			}
		}
		Object obj = new Object();
		obj._name = "PLAYER";
		obj._type = "PLAYER";
		obj.SetPosition(100, 100);
		obj.setSize(32, 64);
		objects.add(obj);

		// addObject("PLAYER", "", new Dimension(32, 64), null);
	}

	ArrayList<Object> objects = new ArrayList<Object>();
	HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	public ArrayList<Object> getObjects(String name)
	{
		ArrayList<Object> newObjects = new ArrayList<Object>();
		for (Object obj : objects)
		{
			if (obj._name == name)
			{
				newObjects.add(obj);
			}
		}
		return newObjects;
	}

	public Object getObject(String name)
	{
		Object newObjects = null;
		for (Object obj : objects)
		{
			if (obj._name == name)
			{
				newObjects = obj;
			}
		}
		return newObjects;
	}

	public Texture getTexture(String string)
	{
		Texture texture = null;
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(string));
		} catch (RuntimeException ioe)
		{
			System.out.println(ioe);
		} catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		return texture;
	}

	@Override
	public void Update(int delta)
	{
		super.Update(delta);
		int speed = delta / 4;

		// (this.Width / 2) - (player.width / 2), (this.Height / 2) -
		// (player.height / 2)
		Object player = getObject("PLAYER");
		if (player != null)
		{
			int xSpeed = 0;
			int ySpeed = 0;
			if (keyboard.keyPressed(Keyboard.KEY_W) && xSpeed == 0)
			{
				ySpeed = -speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_S) && xSpeed == 0)
			{
				ySpeed = speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_A) && ySpeed == 0)
			{
				xSpeed = -speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_D) && ySpeed == 0)
			{
				xSpeed = speed;
			}

			// System.out.println("Facing:" + player.collisionDir);
			player.move(xSpeed, ySpeed);
			if (player.getPosition().getX() < camera.x + 100)
			{

				camera.x -= speed;
			}
			if (player.getPosition().getX() > (camera.x + super.Width) - 132)
			{

				camera.x += speed;
			}

			if (player.getPosition().getY() + (player.getSize().getHeight() / 2) < camera.y + 100)
			{

				camera.y -= speed;
			}
			if (player.getPosition().getY() > (camera.y + super.Height) - 132)
			{

				camera.y += speed;
			}
		}
		int collosions = 0;
		for (Object obj : objects)
		{
			if (obj.getName() != "PLAYER")
			{
				if (collision(obj, player) && obj.getType() == "DIRT")
				{
					collosions++;

				}
			}
		}
		if (collosions > 0)
		{
			player.collosion = true;
		} else
		{
			player.collosion = false;
		}
		super.keyboard.endPoll();
	}

	public boolean collision(Object obj, Object obj2)
	{
		boolean collides = false;
		if (((obj2.getPosition().getX() >= obj.getPosition().getX()
				&& obj2.getPosition().getX() <= obj.getPosition().getX() + obj.getSize().getWidth())
				|| (obj2.getPosition().getX() + obj2.getSize().getWidth() >= obj.getPosition().getX()
						&& obj2.getPosition().getX() + obj2.getSize().getWidth() <= obj.getPosition().getX()
								+ obj.getSize().getWidth()))
				&& ((obj2.getPosition().getY() >= obj.getPosition().getY() && obj2.getPosition().getY()
						+ (obj2.getSize().getHeight() / 2) <= obj.getPosition().getY() + obj.getSize().getHeight())
						|| (obj2.getPosition().getY() + obj2.getSize().getHeight() >= obj.getPosition().getY()
								&& obj2.getPosition().getY() + obj2.getSize().getHeight() <= obj.getPosition().getY()
										+ obj.getSize().getHeight())
						|| (obj2.getPosition().getY() <= obj.getPosition().getY() && obj2.getPosition().getY()
								+ obj2.getSize().getHeight() >= obj.getPosition().getY() + obj.getSize().getHeight())))
		{
			collides = true;
		}
		return collides;
	}

	@Override
	public void Resized()
	{
		super.Resized();
	}

	Vector2f camera = new Vector2f(0, 0);

	public Sprite getSprite(Object obj)
	{
		Sprite sprite = new Sprite();
		if (sprites.containsKey(obj._type))
		{
			sprite = sprites.get(obj._type);

		} else
		{
			sprite.color = Color.red;
		}
		sprite.setSize(obj.getSize().width, obj.getSize().height);
		return sprite;
	}

	@Override
	public void Render()
	{
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);

		for (Object obj : objects)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(obj.getPosition().getX(), obj.getPosition().getY(), 0);
			Sprite sprite = getSprite(obj);
			if (obj.getType() == "PLAYER")
			{
				sprite.texture_Coords = obj.texture_Coords;
				sprite.render_Update = true;
			}
			sprite.Render();
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	@Override
	public void Destroy()
	{
		super.Destroy();
		for (Object obj : objects)
		{
			// obj.Destroy();
		}
	}

	public static void main(String[] argv)
	{
		Game displayExample = new Game();
		displayExample.start();

	}
}