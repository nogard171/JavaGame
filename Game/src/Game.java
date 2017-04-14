import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
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
		sprite3.texture = this.getTexture("res/img/sand.png");
		sprites.put("SAND", sprite3);

		Sprite sprite4 = new Sprite();
		sprite4.texture = this.getTexture("res/img/shallow_water.png");
		sprites.put("SHALLOW", sprite4);

		Sprite sprite5 = new Sprite();
		sprite5.texture = this.getTexture("res/img/deep_water.png");
		sprites.put("DEEP", sprite5);

		Sprite sprite6 = new Sprite();
		sprite6.texture = this.getTexture("res/img/stone.png");
		sprites.put("STONE", sprite6);

		Sprite sprite30 = new Sprite();
		sprite30.texture = this.getTexture("res/img/guy.png");
		sprite30.sprite_size = new Point(32, 64);
		sprites.put("PLAYER", sprite30);

		haspMapObject = new HashMap<Point, Object>();

		map = new int[100][100];

		domidpoint();

		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[0].length; y++)
			{
				Object obj = new Object();

				int height = map[x][y];
				System.out.println("done:" + height);
				obj._type = getTypeByHeight(height);
				obj._name = getTypeByHeight(height);

				if (y - 1 >= 0 && y + 1 < map[x].length && x - 1 >= 0 && x + 1 < map.length)
				{
					String northType = getTypeByHeight(map[x][y - 1]);
					String southType = getTypeByHeight(map[x][y + 1]);
					String westType = getTypeByHeight(map[x - 1][y]);
					String eastType = getTypeByHeight(map[x + 1][y]);
					if (northType == southType && southType == westType && westType == eastType)
					{
						if (obj._type != northType)
						{
							obj._type = northType;
						}
					}
				}

				obj.SetPosition(x * 32, y * 32);

				haspMapObject.put(new Point(x, y), obj);
			}
		}

		Object playerposition = this.getHashMapObject("GRASS");

		Object obj = new Object();
		obj._name = "PLAYER";
		obj._type = "PLAYER";
		obj.SetPosition(playerposition.getPosition().getX(), playerposition.getPosition().getY());
		obj.setSize(32, 64);
		objects.add(obj);

		// addObject("PLAYER", "", new Dimension(32, 64), null);
	}

	public String getTypeByHeight(int height)
	{
		String type = "GRASS";
		if (height < 0)
		{
			type = "DEEP";
		} else if (height >= 0 && height < 10)
		{
			type = "SHALLOW";
		}

		else if (height >= 10 && height < 15)
		{
			type = "SAND";
		}
		return type;
	}

	int[][] map = new int[100][100];
	Random r = new Random();

	// Here the midpoint code begins.
	public void domidpoint()
	{
		// Erase the old map array..
		for (int y = 0; y < 100; y++)
		{
			for (int x = 0; x < 100; x++)
			{
				map[x][y] = 0;
			}
		}
		// Setup points in the 4 corners of the map.
		map[0][0] = 0;
		map[map.length - 1][0] = 0;
		map[map.length - 1][map[0].length - 1] = 0;
		map[0][map[0].length - 1] = 0;
		// Do the midpoint
		midpoint(0, 0, map.length - 1, map[0].length - 1);
	}

	// This is the actual mid point displacement code.
	public boolean midpoint(int x1, int y1, int x2, int y2)
	{
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

	ArrayList<Object> objects = new ArrayList<Object>();
	HashMap<Point, Object> haspMapObject = new HashMap<Point, Object>();
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

	public Object getHashMapObject(String name)
	{
		Object newObjects = null;
		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[0].length; y++)
			{
				Object obj = haspMapObject.get(new Point(x, y));
				if (obj._name == name)
				{
					newObjects = obj;
				}
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

	float gravity = 0;
	int jump = 0;

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
			if (keyboard.keyPressed(Keyboard.KEY_W))
			{
				ySpeed = -speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_S))
			{
				ySpeed = speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_A))
			{
				xSpeed = -speed;
			}
			if (keyboard.keyPressed(Keyboard.KEY_D))
			{
				xSpeed = speed;
			}
			player.move(xSpeed, ySpeed);
			if (player.getPosition().getX() < camera.x + 100)
			{

				camera.x -= speed;
			}
			if (player.getPosition().getX() > (camera.x + super._width) - 132)
			{

				camera.x += speed;
			}

			if (player.getPosition().getY() + (player.getSize().getHeight() / 2) < camera.y + 100)
			{

				camera.y -= speed;
			}
			if (player.getPosition().getY() > (camera.y + super._height) - 132)
			{

				camera.y += speed;
			}
		}
		collision(player, player);
		int collosions = 0;
		ArrayList<Object> collisionObjects = new ArrayList<Object>();
		for (Object obj : diaplayObjects)
		{
			if (obj != null)
			{
				if (obj.getName() != "PLAYER")
				{
					if (collision(obj, player) && (obj.getType() == "DEEP" || obj.getType() == "SHALLOW"))
					{
						collosions++;
						obj.color = new Color(1, 0, 0);
						collisionObjects.add(obj);
					} else
					{
						obj.color = Color.white;
					}
				}
			}
		}
		int playerX = (((int) player.getPosition().getX() / 32) * 32);
		int playerY = (((int) player.getPosition().getY() / 32) * 32);
		for (Object obj : collisionObjects)
		{
			double angle = GetAngleOfLineBetweenTwoPoints(obj.getPosition(), player.getPosition());
			System.out.println("angle:" + angle);
			if (angle > 315 || angle <= 45&&obj.getPosition().getX()<=player.getPosition().getX())
			{
				//System.out.println("LEFT");
				player.collisionDirection.add(Direction.WEST);
			}
			if (angle > 45 && angle <= 135&&obj.getPosition().getY()<=player.getPosition().getY())
			{
				System.out.println("NORTH");
				player.collisionDirection.add(Direction.NORTH);
			}
			if (angle > 135 && angle <= 225&&obj.getPosition().getX()>=player.getPosition().getX())
			{
				//System.out.println("RIGHT");
				player.collisionDirection.add(Direction.EAST);
			}
			if (angle > 225 && angle <= 315&&obj.getPosition().getY()>=player.getPosition().getY())
			{
				//System.out.println("SOUTH");
				player.collisionDirection.add(Direction.SOUTH);
			}

		}
		setDisplayObject();
		super.keyboard.endPoll();
	}

	public static double GetAngleOfLineBetweenTwoPoints(Point p1, Point p2)
	{
		double xDiff = p2.getX() - p1.getX();
		double yDiff = (p2.getY() + 32) - p1.getY();
		double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));
		if (angle < 0)
		{
			angle += 360;
		}
		if (angle >= 360)
		{
			angle -= 360;
		}
		return angle;
	}

	public float getAngle(Point target, Point player)
	{
		float angle = (float) Math
				.toDegrees(Math.atan2(target.getY() - player.getY() + 32, target.getX() - player.getX()));

		if (angle < 0)
		{
			angle += 360;
		}

		return angle;
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
	Vector2f previousCamera = new Vector2f(-1, 0);

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

	public ArrayList<Object> diaplayObjects = new ArrayList<Object>();
	int displayCount = 0;

	public void setDisplayObject()
	{
		if (previousCamera.getX() != camera.getX())
		{
			diaplayObjects.clear();
			displayCount = 0;
			Rectangle display = new Rectangle((int) this.camera.x - 32, (int) this.camera.y - 32,
					(int) this._width + 64, (int) this._height + 64);
			/*
			 * for (Object obj : objects) { if (display.contains(obj._bounds)) {
			 * diaplayObjects.add(obj); displayCount++; } }
			 */
			int newX = ((int) (this.camera.x / 32));
			int newY = ((int) (this.camera.y / 32));
			int newWidth = newX + (this._width / 32) + 1;
			int newHeight = newY + (this._height / 32) + 2;

			for (int x = newX; x < newWidth; x++)
			{
				for (int y = newY; y < newHeight; y++)
				{
					Object obj = this.haspMapObject.get(new Point(x, y));
					if (obj != null)
					{
						diaplayObjects.add(obj);
					}
				}
			}
			// diaplayObjects.add(this.getObject("PLAYER"));
		}
	}

	@Override
	public void Render()
	{
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);
		for (Object obj : diaplayObjects)
		{
			if (obj != null)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(obj.getPosition().getX(), obj.getPosition().getY(), 0);
				Sprite sprite = getSprite(obj);
				sprite.color = obj.color;
				sprite.Render();
				GL11.glPopMatrix();
			}
		}
		for (Object obj : objects)
		{
			if (obj != null)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(obj.getPosition().getX(), obj.getPosition().getY(), 0);
				Sprite sprite = getSprite(obj);
				if (obj.getType() == "PLAYER")
				{
					sprite.texture_Coords = obj.texture_Coords;
					sprite.render_Update = true;
				}
				sprite.color = obj.color;
				sprite.Render();
				GL11.glPopMatrix();
			}
		}
		// Object player = getObject("PLAYER");
		// getSprite(player).Render();
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