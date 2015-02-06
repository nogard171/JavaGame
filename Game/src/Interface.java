import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import objects.MenuItem;
import util.ImageLoader;
import util.MouseInput;
import util.Window;


public class Interface {
	private BufferedImage texture;
	private Point position = new Point(0,0);
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	public Window window;
	public Dimension dim = new Dimension(100,100);
	public boolean usesWindow = false;
	public Interface()
	{
		texture = ImageLoader.getImageFromResources("\\resources\\image\\menuset.png");
	}
	public Point getPosition()
	{
		return position;
	}
	public void setPosition(Point newPosition)
	{
		position =newPosition;
	}
	public void onLoad(ArrayList<MenuItem> items)
	{
		if(usesWindow)
		{
			window = new Window();
		}
		menuItems = items;		
	}
	public void onUpdate()
	{
		
	}
	public void onPaint(Graphics g, ImageObserver obj)
	{
		if(usesWindow)
		{
			window.drawWindow(g,position.x,position.y, dim.width, dim.height, obj);
		}
		g.setColor(Color.BLACK);
		for(MenuItem item:menuItems)
		{
			if(item.isImage)
			{
				g.drawImage(getTexture(item.getTag()),item.getBounds().x,item.getBounds().y,item.getBounds().width,item.getBounds().height,obj);
			}
			else
			{
				g.setFont(new Font("Arial", Font.PLAIN, 20)); 
				g.drawString(item.getTag(),item.getBounds().x+5,item.getBounds().y+15);
			}
		}
	}
	public void onInput(MouseInput mouse) 
	{
		for(MenuItem item:menuItems)
		{
			if(item.isClickable()&&item.getBounds().intersects(new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1))&&mouse.buttonDownOnce(1))
			{
				item.isClicked = true;
				break;
			}
			else
			{
				item.isClicked = false;
			}
		}
	}
	public BufferedImage getTexture(String tag)
	{
		switch(tag)
		{
		case "bag":
			return texture.getSubimage(128, 32, 32,32);
		case "chara":
			return texture.getSubimage(128, 64, 32,32);
		default:
			return texture.getSubimage(0, 0, 32,32);
		}
	}
}
