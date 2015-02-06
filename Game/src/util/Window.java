package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import util.*;

public class Window 
{
	//this is the menuset for texturing the windows.
	public BufferedImage texture;
	//the bounds of the window
	public Rectangle bounds = new Rectangle(0,0,0,0);
	//this is the constructor
	public Window()
	{
		this.texture = ImageLoader.getImageFromResources("\\resources\\image\\menuset.png");
	}
	//this draws the window
	public void drawCustomWindow(Graphics bg, int x, int y, int width, int height,ImageObserver obj)
	{
		bounds = new Rectangle(x,y,width,height);
		BufferedImage off_Image = new BufferedImage(800,600,
				                    BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = off_Image.createGraphics();
		
		//calculate the width of the window based on the tile size being 7.
		int tileWidth = (int)(((float)width/(float)7)-1);
		//calculate the height of the window based on the tile size being 7.
		int tileHeight = (int)(((float)height/(float)7)-5);
		//draw the top left part first
		g.drawImage(this.texture.getSubimage(0, 0, 7, 7), x, y, obj);
		//loop to draw the top middle part.
		for(int i =0;i<tileWidth;i++)
		{
			g.drawImage(this.texture.getSubimage(7,0, 7, 7), x+((i+1)*7), y, obj);
		}
		//draw the top right corner
		g.drawImage(this.texture.getSubimage(120,0,7,7), x+((tileWidth+1)*7), y, obj);
		//loop the total height
		for(int j =0;j<tileHeight;j++)
		{
			//draw the left middle part
			g.drawImage(this.texture.getSubimage(0, 7, 7, 7), x, y+((j+1)*7), obj);
			//loop the middle parts
			for(int i =0;i<tileWidth;i++)
			{
				g.drawImage(this.texture.getSubimage(7,7, 7, 7), x+((i+1)*7), y+((j+1)*7), obj);
			}
			//draw the middle left part
			g.drawImage(this.texture.getSubimage(120,7,7,7), x+((tileWidth+1)*7), y+((j+1)*7), obj);
		}
		//draw the bottom left part
		g.drawImage(this.texture.getSubimage(0, 120, 7, 7), x, y+((tileHeight+1)*7), obj);
		//draw the bottom middle part
		for(int i =0;i<tileWidth;i++)
		{
			g.drawImage(this.texture.getSubimage(7,120, 7, 7), x+((i+1)*7), y+((tileHeight+1)*7), obj);
		}
		//draw the bottom right
		g.drawImage(this.texture.getSubimage(120,120,7,7), x+((tileWidth+1)*7), y+((tileHeight+1)*7), obj);
		
		
		bg.drawImage(off_Image, 0,0, obj);
	}
	public void drawWindow(Graphics g, int x, int y, int width, int height,ImageObserver obj)
	{
		bounds = new Rectangle(x,y,width,height);
		//draw the bottom right
		g.drawImage(this.texture.getSubimage(0,0,128,128), x, y, width, height,obj);
		
	}
}
