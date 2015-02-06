import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import objects.Object;
import objects.Type;
import util.ImageLoader;
public class Map {
ArrayList<Object> tiles = new ArrayList<Object>();
ArrayList<Object> arrayObjects = new ArrayList<Object>();
BufferedImage texture;
public Map()
{
	texture = ImageLoader.getImageFromResources("\\resources\\image\\tileset.png");
	generateObjects(tiles,Type.Grass);
	generateRandomObjects(arrayObjects);
}
public void generateObjects(ArrayList<Object> objs, objects.Type type)
{
	int x = 0; 
	int y=0;
	for(int i =0;i<100;i++)
	{
		Object obj = new Object();
		obj.lowerType = type;
		obj.bounds = new Rectangle(100+(x*32),y*32,32,32);
		obj.upperBounds = new Rectangle(-16,-96,64,128);
		obj.setTexture(objects.Type.getTexture(texture,obj.upperType), objects.Type.getTexture(texture,obj.lowerType));
		if(x>=10)
		{
			y++;
			x=0;
		}
		else
		{
			x++;
		}
		objs.add(obj);
	}
}
Random rand = new Random();
Type[] randomLowerTypes = {Type.Tree,Type.Blank};
Type[] randomUpperTypes = {Type.Bush,Type.Blank};
public void generateRandomObjects(ArrayList<Object> objs)
{
	
	int x = 0; 
	int y=0;
	for(int i =0;i<100;i++)
	{
		Object obj = new Object();
		int randomNum = rand.nextInt((1-0) + 1) + 1;
	    //this is for map things
	    obj.lowerType = randomLowerTypes[randomNum-1];
	    obj.upperType = randomUpperTypes[randomNum-1];
		if(obj.lowerType==Type.Tree)
		{
			obj.passable = false;
		}
		obj.bounds = new Rectangle(100+(x*32),y*32,32,32);
		obj.upperBounds = new Rectangle(-16,-96,64,128);
		obj.setTexture(objects.Type.getTexture(texture,obj.upperType), objects.Type.getTexture(texture,obj.lowerType));
		if(x>=10)
		{
			y++;
			x=0;
		}
		else
		{
			x++;
		}
		objs.add(obj);
	}
}
public void checkCollision(Object player)
{
	for(Object tile: tiles)
	{
		if(!tile.passable&&tile.getBounds().intersects(player.getBounds()))
		{
			player.onCollide(tile);		
		}
	}
	for(Object tile: arrayObjects)
	{
		if(!tile.passable&&tile.getBounds().intersects(player.getBounds()))
		{
			player.onCollide(tile);		
		}
	}
}
public void onUpdate()
{
	for(Object tile: tiles)
	{
		tile.onUpdate();
	}
	for(Object tile: arrayObjects)
	{
		tile.onUpdate();
	}
}
public void onPaint(Graphics g, ImageObserver obj)
{
	for(Object tile: tiles)
	{
		tile.onPaint(g, obj);
	}
	for(Object tile:arrayObjects)
	{
		tile.onPaint(g, obj);
	}
}
public void onUpperPaint(Graphics g, ImageObserver obj)
{
	for(Object tile: tiles)
	{
		tile.onUpperPaint(g, obj);
	}
	for(Object tile: arrayObjects)
	{
		tile.onUpperPaint(g, obj);
	}
}
}
