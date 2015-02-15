import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import networking.Locker;
import objects.Item;
import objects.ItemType;
import objects.KeyBindings;
import objects.MenuItem;
import objects.Skill;
import util.ImageLoader;
import util.KeyboardInput;
import util.MouseInput;
import util.Window;


public class Interface {
	private BufferedImage texture;
	private BufferedImage skillTexture;
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
	public boolean fullscreen = false;
	public Interface()
	{
		texture = ImageLoader.getImageFromResources("\\resources\\image\\menuset.png");
		skillTexture = ImageLoader.getImageFromResources("\\resources\\image\\skills.png");
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
		g.setFont(new Font("arial",Font.PLAIN,12));
		g.setColor(new Color(81,65,41));
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
		
		if(menu==1&&usesWindow)
		{//inventory
			for(Item item:Locker.player.inventory)
			{
				item.draw(g, obj, window);
			}
		}
		else if(menu ==2&&usesWindow)
		{//chara
			g.drawString("Name: "+Locker.player.getName(), position.x+37,position.y+16);
			g.drawString("Level: "+Locker.player.level, position.x+37,position.y+30);
			
			int xpWidth = (int) (((window.bounds.width-17)/(double)Locker.player.maxExperience)*((double)Locker.player.experience));
			xpBounds =new Rectangle(position.x+41,position.y+window.bounds.height-15,xpWidth,9); 
			g.drawRect(xpBounds.x-1,xpBounds.y-1,(window.bounds.width-17),xpBounds.height+1);
			g.setColor(new Color(0,255,0));
			g.fillRect(xpBounds.x,xpBounds.y,xpBounds.width,xpBounds.height);
			//g.drawString("Skills Count: "+Locker.player.skills.size(), position.x+37,position.y+30);
		}
		else if(menu ==3&&usesWindow)
		{//skills
			int x=1,y=0;
			for(Skill skill:Locker.player.skills)
			{
				skill.bounds = new Rectangle(position.x+(x*32)+(x*5),position.y+(y*32)+((y+1)*4),32,32);
				//g.drawRect(skill.bounds.x, skill.bounds.y, skill.bounds.width, skill.bounds.height);
				g.drawImage(getSkillTexture("back"),skill.bounds.x,skill.bounds.y,skill.bounds.width,skill.bounds.height,obj);
				g.drawImage(getSkillTexture(skill.name),skill.bounds.x,skill.bounds.y,skill.bounds.width,skill.bounds.height,obj);
				g.drawImage(getSkillTexture("xpback"),skill.bounds.x,skill.bounds.y+12,skill.bounds.width,skill.bounds.height,obj);
				int xpWidth = (int) ((32/(double)skill.maxExperience)*((double)skill.experience+1));
				g.drawImage(getSkillTexture("xp"),skill.bounds.x,skill.bounds.y+12,xpWidth,skill.bounds.height,obj);
				//g.drawString(skill.name, skill.bounds.x,skill.bounds.y+16);
				if(x>=5)
				{
					y++;
					x=1;
				}
				else
				{
					x++;
				}
			}
		}
		else if(menu ==4&&usesWindow)
		{//equipment
			
		}
		else if(menu ==5&&usesWindow)
		{//magic
			
		}
		else if(menu ==6&&usesWindow)
		{//options
			
			g.drawString("options", window.bounds.x+5,window.bounds.y+16);
			for(MenuItem item:options)
			{
				item.setBounds(new Rectangle(window.bounds.width-32-8,item.getBounds().y,32,10));
				g.drawString(item.PreTag + item.getTag(), window.bounds.x+5,window.bounds.y+item.getBounds().y+10);
				
				if(!item.isInput)
				{
					g.drawRect(window.bounds.x+item.getBounds().x,window.bounds.y+item.getBounds().y,item.getBounds().width,item.getBounds().height);
					if(item.status)
					{
						g.setColor(Color.green);
					}
					else
					{
						g.setColor(Color.red);
					}

					g.fillRect(window.bounds.x+item.getBounds().x+1,window.bounds.y+item.getBounds().y+1,item.getBounds().width-1,item.getBounds().height-1);
					g.setColor(Color.black);
				}
				else
				{
					g.drawString(item.text, window.bounds.x+item.getBounds().x,window.bounds.y+item.getBounds().y+10);
					if(item.getTag()=="left")
					{
						item.text = String.valueOf(Locker.keys.Left)+"("+(char)Locker.keys.Left+")";
						
					}
					if(item.getTag()=="right")
					{
						item.text = String.valueOf(Locker.keys.Right)+"("+(char)Locker.keys.Right+")";
						
					}
					if(item.getTag()=="up")
					{
						item.text = String.valueOf(Locker.keys.Up)+"("+(char)Locker.keys.Up+")";
						
					}
					if(item.getTag()=="down")
					{
						item.text = String.valueOf(Locker.keys.Down)+"("+(char)Locker.keys.Down+")";
						
					}
				}
			}
		}
		else
		{
			usesWindow = false;
		}
		
		if(hoverDescription != "")
		{
			g.setColor(new Color(0,0,0,128));
			g.fillRect(hoverPoints.x+position.x, hoverPoints.y+position.y, hoverDescription.length()*6, 12);
			g.setColor(Color.white);
			g.drawString(hoverDescription, hoverPoints.x+position.x, hoverPoints.y+10+position.y);
		}
		g.setColor(Color.black);
		g.drawLine(hoverPoints.x, hoverPoints.y+position.y, mouseUI.x, mouseUI.y);
		g.setFont(new Font("arial",1,12));		
	}
	Rectangle xpBounds =new Rectangle(0,0,32,32); 
	Point mouseUI = new Point(0,0);
	public void onInput(MouseInput mouse) 
	{
		mouseUI = new Point(mouse.getPosition().x,mouse.getPosition().y);
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
		if(menu ==3&&usesWindow)
		{
			for(Skill skill:Locker.player.skills)
			{
				if(skill.isHoverable&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(skill.getBounds().x,skill.getBounds().y,skill.getBounds().width,skill.getBounds().height)))
				{
					hoverPoints = new Point(skill.getBounds().x,skill.getBounds().y-position.y);
					hoverDescription = skill.description;
					skill.isHovered = true;
					break;
				}
				else
				{
					skill.isHovered = false;
				}
			}
		}
		if(menu ==6&&usesWindow)
		{
			for(MenuItem item:options)
			{
				if(item.isHoverable&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(window.bounds.x+item.getBounds().x+transform.x,window.bounds.y+item.getBounds().y+transform.y,item.getBounds().width,item.getBounds().height)))
				{
					hoverPoints = new Point(item.getBounds().x+32,item.getBounds().y);
					hoverDescription = item.description;
					item.isHovered = true;
					break;
				}
				else
				{
					item.isHovered = false;
				}
			}
			for(MenuItem item:options)
			{
				if(item.isClickable()&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(window.bounds.x+item.getBounds().x+transform.x,window.bounds.y+item.getBounds().y+transform.y,item.getBounds().width,item.getBounds().height))&&mouse.buttonDown(1))
				{
					item.isClicked = true;
					break;
				}
				else
				{
					item.isClicked = false;
				}
			}
			for(MenuItem item:options)
			{
				if(item.isClicked)
				{
					if(item.getTag()=="left"||item.getTag()=="right"||item.getTag()=="up"||item.getTag()=="down")
					{
						Locker.showMessage ="Enter the new "+item.getTag()+" movement key.";
						Locker.changeKey = item.getTag();
						Locker.changeKeyBindings = true;
						break;
					}
				}
			}
		}
		if(menu ==1&&usesWindow)
		{
			for(Item item:Locker.player.inventory)
			{
				if(item.isHoverable&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(item.getBounds().x,item.getBounds().y,item.getBounds().width,item.getBounds().height)))
				{
					hoverPoints = new Point(item.getBounds().x,item.getBounds().y-position.y);
					hoverDescription = item.description;
					item.isHovered = true;
					break;
				}
				else
				{
					item.isHovered = false;
				}
			}
		}
		if(menu ==2&&usesWindow)
		{
			if(new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(xpBounds))
			{
				hoverPoints = new Point(xpBounds.x,xpBounds.y-position.y);
				hoverDescription = Locker.player.experience+"/"+Locker.player.maxExperience;
			}
		}
		for(MenuItem item:menuItems)
		{
			if(item.isHoverable&&new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).getBounds().intersects(new Rectangle(position.x+item.getBounds().x,position.y+item.getBounds().y,item.getBounds().width,item.getBounds().height)))
			{
				hoverPoints = new Point(item.getBounds().x,item.getBounds().y);
				hoverDescription = item.description;
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
				else if(item.getTag()=="skills")
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
				else if(item.getTag()=="equip")
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
				else if(item.getTag()=="magic")
				{
					if(usesWindow&&menu==5)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 5;
					break;
				}
				else if(item.getTag()=="hide")
				{
					item.setBounds(new Rectangle(item.getBounds().x+32,item.getBounds().y,item.getBounds().width,item.getBounds().height));
					transform = new Point(-32,0);
					item.setTag("show");
					usesWindow = false;
					break;
				}
				else if(item.getTag()=="show")
				{				
					item.setBounds(new Rectangle(item.getBounds().x-32,item.getBounds().y,item.getBounds().width,item.getBounds().height));
					transform = new Point(0,0);
					item.setTag("hide");
					usesWindow = false;
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
				else if(item.getTag()=="options")
				{
					if(usesWindow&&menu==6)
					{
						usesWindow = !usesWindow;
					}
					else
					{
						usesWindow = true;
					}
					menu = 6;
					break;
				}
			}
		}
	}
	int exitClicked = 0;
	public ArrayList<MenuItem> options = new ArrayList<MenuItem>();
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
				return texture.getSubimage(128, 192, 32,32);
			case "skills":
				return texture.getSubimage(128, 64, 32,32);
			case "exit":
				return texture.getSubimage(128, 96, 32,32);
			case "equip":
				return texture.getSubimage(128, 128, 32,32);
			case "magic":
				return texture.getSubimage(128, 160, 32,32);
			case "xp":
				return texture.getSubimage(96, 160, 32,16);
			case "xpback":
				return texture.getSubimage(96, 176, 32,16);
			case "options":
				return texture.getSubimage(0, 224, 32,32);
			case "show":
				return texture.getSubimage(64, 192, 32,32);
			case "hide":
				return texture.getSubimage(96, 192, 32,32);
			default:
				return texture.getSubimage(0, 0, 32,32);
		}
	}
	public BufferedImage getSkillTexture(String tag)
	{
		switch(tag)
		{
			case "attack":
				return skillTexture.getSubimage(64, 0, 32,32);
			case "defense":
				return skillTexture.getSubimage(128, 0, 32,32);
			case "back":
				return skillTexture.getSubimage(64, 160, 32,32);
			case "xp":
				return skillTexture.getSubimage(0, 160, 32,32);
			case "xpback":
				return skillTexture.getSubimage(32, 160, 32,32);
			default:
				return skillTexture.getSubimage(0, 0, 32,32);
		}
	}
}
