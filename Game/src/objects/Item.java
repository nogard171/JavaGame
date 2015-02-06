package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import util.Window;
import networking.Locker;

public class Item {
	//this identifys the item by name
	private String name = "";
	//this is the inventory index it's in.
	private int ID = -1;
	//this is the type of item it if.
	private ItemType type = ItemType.Blank;
	//this tells the ui if the item is stackable
	public boolean isStackable = false;
	//this idicates the count of the items
	private int count = 1;
	//the position of the item
	private Point position = new Point(0,0);
	//the texture of the item
	private BufferedImage texture;
	//this sets the name for th item
	public void setName(String newName)
	{
		this.name = newName;
	}
	//this gets the name for th item
	public String getName()
	{
		return this.name;
	}
	//this sets the ID
	public void setID(int newID)
	{
		this.ID = newID;
	}
	//this gets the id
	public int getID()
	{
		return this.ID;
	}
	//this sets the type
	public void setType(ItemType newType)
	{
		this.type = newType;
	}
	//this gets the type
	public ItemType getType()
	{
		return this.type;
	}
	//this sets the count
	public void setCount(int newCount)
	{
		this.count = newCount;
	}
	//this gets the count
	public int getCount()
	{
		return this.count;
	}
	//this adds to the count
	public void addAmount(int amount)
	{
		this.count += amount;
	}
	//this takes away amount
	public void removeAmount(int amount)
	{
		this.count -= amount;
	}
	//this sets the items position
	public void setPosition(int x, int y)
	{
		this.position = new Point(x,y);
	}
	//set the item texture
	public void setTexture(BufferedImage img)
	{
		this.texture = img;
	}
	//draw the item
	public void draw(Graphics g, ImageObserver obj, Window win)
	{
		g.drawRect(win.bounds.x+(position.x), win.bounds.y+(position.y), 30,30);
		g.drawImage(texture, win.bounds.x+(position.x), win.bounds.y+(position.y), 30,30, obj);
	}
	public void mouseClicked(MouseEvent e)
	{
		
	}
}
