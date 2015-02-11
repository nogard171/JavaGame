import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import networking.Locker;
import objects.Item;
import objects.ItemType;
import objects.MenuItem;
import util.ImageLoader;
import util.MouseInput;
import util.Window;


public class Interface {
	private BufferedImage texture;
	private Point position = new Point(0,0);
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	public Window window;
	public Point windowPosition = new Point(0,0);
	public Dimension dim = new Dimension(100,100);
	public boolean usesWindow = false;
	public int menu =0;
	Point hoverPoints = new Point(0,0);
	String hoverDescription = "";
	private Point transform = new Point(0,0);
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
	public void setWindowPosition(Point newWinPos)
	{
		windowPosition = newWinPos;
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
		g.translate(transform.x, transform.y);
		if(usesWindow)
		{
			window.drawWindow(g,windowPosition.x,windowPosition.y, dim.width, dim.height, obj);
		}		
		g.setColor(Color.BLACK);
		for(MenuItem item:menuItems)
		{
			if(item.isImage)
			{
				g.drawImage(getTexture("back"),position.x+item.getBounds().x,position.y+item.getBounds().y,item.getBounds().width,item.getBounds().height,obj);
				g.drawImage(getTexture(item.getTag()),position.x+item.getBounds().x,position.y+item.getBounds().y,item.getBounds().width,item.getBounds().height,obj);
				
			}
			else
			{
				g.setFont(new Font("Arial", Font.PLAIN, 20)); 
				g.drawString(item.getTag(),position.x+item.getBounds().x+5,position.y+item.getBounds().y+15);
				g.setFont(new Font("Arial", Font.PLAIN, 12));
			}
		}
		if(usesWindow&&menu-1>=0)
		{
			g.drawImage(getTexture("selector"),menuItems.get(menu-1).getBounds().x, menuItems.get(menu-1).getBounds().y+getPosition().y, menuItems.get(menu-1).getBounds().width, menuItems.get(menu-1).getBounds().height,obj);
		}

		if(hoverDescription != "")
		{
			g.setColor(new Color(0,0,0,128));
			g.fillRect(hoverPoints.x+position.x, hoverPoints.y+position.y, hoverDescription.length()*6, 12);
			g.setColor(Color.white);
			g.drawString(hoverDescription, hoverPoints.x+position.x, hoverPoints.y+10+position.y);
		}
		if(menu==1&&usesWindow)
		{
			for(Item item:Locker.player.inventory)
			{
				item.draw(g, obj, window);
			}
		}
		else if(menu ==2&&usesWindow)
		{
			g.setFont(new Font("Engravers MT",1,12));
			g.setColor(new Color(81,65,41));
			g.drawString("Username:"+Locker.player.getName(), position.x+37,position.y+16);
		}
		else if(menu ==3&&usesWindow)
		{
			
		}
		else if(menu ==4&&usesWindow)
		{
			
		}
		else
		{
			usesWindow = false;
		}
		g.setColor(Color.black);
		g.setFont(new Font("arial",1,12));		
	}
	public void onInput(MouseInput mouse) 
	{
		for(MenuItem item:menuItems)
		{
			if(item.isClickable()&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(position.x+item.getBounds().x+transform.x,position.y+item.getBounds().y+transform.y,item.getBounds().width,item.getBounds().height))&&mouse.buttonDownOnce(1))
			{
				item.isClicked = true;
				break;
			}
			else
			{
				item.isClicked = false;
			}
		}
		for(MenuItem item:menuItems)
		{
			if(item.isHoverable&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(position.x+item.getBounds().x,position.y+item.getBounds().y,item.getBounds().width,item.getBounds().height)))
			{
				item.isHovered = true;
				break;
			}
			else
			{
				item.isHovered = false;
			}
		}
		for(MenuItem item:menuItems)
		{
			if(item.isClicked)
			{
				if(item.getTag()=="bag")
				{
					if(usesWindow&&menu==1)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 1;
					break;
				}
				else if(item.getTag()=="chara")
				{
					if(usesWindow&&menu==2)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 2;
					break;
				}
				else if(item.getTag()=="equip")
				{
					if(usesWindow&&menu==3)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 3;
					break;
				}
				else if(item.getTag()=="magic")
				{
					if(usesWindow&&menu==4)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 4;
					break;
				}
				else if(item.getTag()=="hide")
				{
					item.setBounds(new Rectangle(item.getBounds().x+32,item.getBounds().y,item.getBounds().width,item.getBounds().height));
					transform = new Point(-32,0);
					item.setTag("show");
					break;
				}
				else if(item.getTag()=="show")
				{				
					item.setBounds(new Rectangle(item.getBounds().x-32,item.getBounds().y,item.getBounds().width,item.getBounds().height));
					transform = new Point(0,0);
					item.setTag("hide");
					break;
				}
				else if(item.getTag()=="exit")
				{

					exitClicked++;
					if(exitClicked>=2)
					{
						System.exit(1);
					}
					break;
				}
			}
		}
	}
	int exitClicked = 0;
	public BufferedImage getTexture(String tag)
	{
		switch(tag)
		{
			case "back":
				return texture.getSubimage(128, 0, 32,32);
			case "selector":
				return texture.getSubimage(96, 128, 32,32);
			case "bag":
				return texture.getSubimage(128, 32, 32,32);
			case "chara":
				return texture.getSubimage(128, 64, 32,32);
			case "exit":
				return texture.getSubimage(128, 96, 32,32);
			case "equip":
				return texture.getSubimage(128, 128, 32,32);
			case "magic":
				return texture.getSubimage(128, 160, 32,32);
			case "show":
				return texture.getSubimage(64, 192, 32,32);
			case "hide":
				return texture.getSubimage(96, 192, 32,32);
			default:
				return texture.getSubimage(0, 0, 32,32);
		}
	}
}
