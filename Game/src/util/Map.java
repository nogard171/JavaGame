package util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import networking.Locker;
import objects.Direction;
import objects.Object;
import objects.Type;
import objects.Player;

public class Map {
	public Dimension dim = new Dimension(100,100);
	public ArrayList<Object> tiles = new ArrayList<Object>();
	public ArrayList<Object> arrayObjects = new ArrayList<Object>();
	public BufferedImage texture;
	public String title = "test";

	public Map() {
		texture = ImageLoader
				.getImageFromResources("\\resources\\image\\tileset.png");
		//generateObjects(tiles, Type.Grass);
		//generateRandomObjects(arrayObjects);
	}

	public void generateObjects(ArrayList<Object> objs, objects.Type type) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 100; i++) {
			Object obj = new Object();
			obj.lowerType = type;
			obj.index = i;
			obj.bounds = new Rectangle((x * 64), y * 64, 64, 64);
			obj.setTexture(objects.Type.getTexture(texture, obj.upperType),
					objects.Type.getTexture(texture, obj.lowerType));
			
			if (x >= 9) {
				y++;
				x = 0;
			} else {
				x++;
			}
			objs.add(obj);
		}
	}

	public Object getObjectAt(Point position, Direction direction) {
		Object obj = null;
		for (Object tile : arrayObjects) {
			if (direction == Direction.Left) {
				if (tile.getBounds().intersects(
						new Rectangle(position.x - 32, position.y, 10, 10))) {
					obj = tile;
					break;
				}
			}
			if (direction == Direction.Right) {
				if (tile.getBounds().intersects(
						new Rectangle(position.x + 32, position.y, 10, 10))) {
					obj = tile;
					break;
				}
			}
			if (direction == Direction.Up) {
				if (tile.getBounds().intersects(
						new Rectangle(position.x, position.y - 32, 10, 10))) {
					obj = tile;
					break;
				}
			}
			if (direction == Direction.Down) {
				if (tile.getBounds().intersects(
						new Rectangle(position.x, position.y + 32, 10, 10))) {
					obj = tile;
					break;
				}
			}
		}
		return obj;
	}
	public Object getObjectAt(Point position) {
		Object obj = null;
		for (Object tile : arrayObjects) {			
				if (tile.getBounds().intersects(
						new Rectangle(position.x, position.y, 64, 64))) {
					obj = tile;
					break;
				}
			
		}
		return obj;
	}
	public Object getTileAt(Point position) {
		Object obj = null;
		for (Object tile : tiles) {			
				if (tile.getBounds().intersects(
						new Rectangle(position.x, position.y, 64,64))) {
					obj = tile;
					break;
				}
			
		}
		return obj;
	}

	Random rand = new Random();
	Type[] randomLowerTypes = { Type.Tree, Type.Rock, Type.Blank };
	Type[] randomUpperTypes = { Type.Bush, Type.Blank, Type.Blank };

	public void generateRandomObjects(ArrayList<Object> objs) {

		int x = 0;
		int y = 0;
		for (int i = 0; i < 100; i++) {
			Object obj = new Object();
			int randomNum = rand.nextInt(((randomLowerTypes.length-1) - 0) + 1) + 1;
			// this is for map things
			obj.lowerType = randomLowerTypes[randomNum - 1];
			obj.upperType = randomUpperTypes[randomNum - 1];
			obj.index = i;
			if (obj.lowerType == Type.Tree) {
				obj.passable = false;
				obj.upperBounds = new Rectangle(-22, -106, 64, 128);
			}
			if (obj.lowerType == Type.Rock) {
				obj.passable = false;
			}
			obj.bounds = new Rectangle((x * 64) + 16, (y * 64) + 16, 32, 32);
			obj.setTexture(objects.Type.getTexture(texture, obj.upperType),
					objects.Type.getTexture(texture, obj.lowerType));
			if (x >= 9) {
				y++;
				x = 0;
			} else {
				x++;
			}
			objs.add(obj);
		}
	}

	public void checkCollision(Object player) {
		for (Object tile : tiles) {
			if (!tile.passable
					&& tile.getBounds().intersects(player.getBounds())) {
				player.onCollide(tile);
			}
		}
		for (Object tile : arrayObjects) {
			if (!tile.passable
					&& tile.getBounds().intersects(player.getBounds())) {
				player.onCollide(tile);
			}
		}
	}

	public void checkCollision(Player player) {
		for (Object tile : tiles) {
			Rectangle rec = new Rectangle(tile.getBounds().x+position.x,tile.getBounds().y+position.y,tile.getBounds().width,tile.getBounds().height);
			if (!tile.passable
					&& rec.intersects(player.getBounds())) {
				player.onCollide(tile);
			}
		}
		for (Object tile : arrayObjects) {
			Rectangle rec = new Rectangle(tile.getBounds().x+position.x,tile.getBounds().y+position.y,tile.getBounds().width,tile.getBounds().height);
			if (!tile.passable
					&& rec.intersects(player.getBounds())) {
				player.onCollide(tile);
			}
		}
	}

	public void onUpdate(double d) {
		for (Object tile : tiles) {
			tile.onUpdate(d);
		}
		for (Object tile : arrayObjects) {
			tile.onUpdate(d);
		}
	}
	public Point position = new Point(0,0);
	public void onPaint(Graphics g, ImageObserver obj) {
		/*for (Object tile : tiles) {
			Rectangle rec = new Rectangle(position.x,position.y,dim.width,dim.height);
			if(rec.intersects(tile.bounds))
			{
				tile.onPaint(g, obj);
			}
		}*/
		int x=0,y = 0;
		int width = (int)Math.ceil((double)(dim.width/64));
		int height = (int)Math.ceil((float)(dim.height/64));
		for(int i =0;i<((width*2)*(height*2));i++)
		{
			Object tile = this.getTileAt(new Point(((x-2)*64)-position.x,((y-2)*64)-position.y));
			if(tile ==null)
			{
				//System.out.println("null tile");
				Object newTile = new Object();
				newTile.bounds = new Rectangle(((x-2)*64)-position.x,((y-2)*64)-position.y,64,64);
				int randomNum = rand.nextInt(((randomLowerTypes.length-1) - 0) + 1) + 1;
				if (randomNum==1) {
					newTile.lowerType = Type.Grass;
				}
				else if (randomNum==2) 
				{
					newTile.lowerType = Type.Dirt;
				}
				else if (randomNum==3) 
				{
					newTile.lowerType = Type.Grass;
				}
				
				newTile.setTexture(objects.Type.getTexture(texture, newTile.upperType),
				objects.Type.getTexture(texture, newTile.lowerType));
				Object second = this.getTileAt(new Point(newTile.bounds.x,newTile.bounds.y));
				if(second==null)
				{
					Locker.map.tiles.add(newTile);
				}
			}
			else
			{
				tile.onPaint(g,position, obj);
			}
			if(x>=width+2)
			{
				y++;
				x=0;
			}
			else
			{
				x++;
			}
		}
		/*for (Object tile : arrayObjects) {
			tile.onPaint(g, obj);
			// g.drawRect(tile.bounds.x,tile.bounds.y,tile.bounds.width,tile.bounds.height);
		}*/
		x = 0;
		y = 0;
		for(int i =0;i<((width*2)*(height*2));i++)
		{
			Object tile = this.getObjectAt(new Point(((x-2)*64)-position.x,((y-2)*64)-position.y));
			if(tile ==null)
			{
				//System.out.println("null tile");
				Object object = new Object();
				int randomNum = rand.nextInt(((randomLowerTypes.length-1) - 0) + 1) + 1;
				// this is for map things
				object.lowerType = randomLowerTypes[randomNum - 1];
				object.upperType = randomUpperTypes[randomNum - 1];
				object.index = i;
				if (object.lowerType == Type.Tree) {
					object.passable = false;
					object.upperBounds = new Rectangle(-22, -106, 64, 128);
				}
				if (object.lowerType == Type.Rock) {
					object.passable = false;
				}
				object.bounds = new Rectangle(((x-2)*64)-position.x, ((y-2)*64)-position.y, 32, 32);
				object.setTexture(objects.Type.getTexture(texture, object.upperType),
						objects.Type.getTexture(texture, object.lowerType));
					
					Object second = this.getObjectAt(new Point(object.bounds.x,object.bounds.y));
					if(second==null)
					{
						Locker.map.arrayObjects.add(object);
					}
				
			}
			else
			{
				tile.onPaint(g,position, obj);
			}
			if(x>=width+2)
			{
				y++;
				x=0;
			}
			else
			{
				x++;
			}
		}
	}

	public void onUpperPaint(Graphics g, ImageObserver obj) {
		for (Object tile : tiles) {
			tile.onUpperPaint(g,position, obj);

		}
		for (Object tile : arrayObjects) {
			tile.onUpperPaint(g,position, obj);
		}
	}

	public void removeObjectsIn(Rectangle walkableArea) {
		// TODO Auto-generated method stub
		for(int i =0;i<arrayObjects.size();i++)
		{
			if(walkableArea.intersects(arrayObjects.get(i).bounds))
			{
				arrayObjects.remove(i);
			}
		}
	}
}
