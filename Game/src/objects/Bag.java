package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import util.ImageLoader;
import util.Window;

public class Bag {
	public BufferedImage texture;
	public int size = 6;
	public String name = "";
	public ArrayList<Item> items = new ArrayList<Item>();
	Point position = new Point(100,100);
	public boolean isOpened = false;
	public void draw(Graphics g,Window win)
	{
		if(isOpened)
		{
			drawItems(g,win);
		}
		g.drawImage(texture,position.x,position.y,null);
	}
	public boolean isClick(Point mouse)
	{
		Rectangle rec = new Rectangle(position.x,position.y,32,32);
		if(rec.intersects(mouse.x, mouse.y, 1, 1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void drawItems(Graphics g, Window window)
	{
		int x =0;
		int y =0;
		for(Item item:items)
		{
			item.setPosition(x*32+(15), y*32+(10));
			item.draw(g, null, window);
			if(x>=4)
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
}
