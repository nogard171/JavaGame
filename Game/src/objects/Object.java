package objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import networking.Locker;
import util.KeyboardInput;

public class Object {
	
	public Rectangle bounds = new Rectangle(0,0,32,32);
	private Point velocity = new Point(2,2);
	private Direction direction = Direction.Right;
	public Type lowerType = Type.Blank;
	public Type upperType = Type.Blank;
	public Rectangle upperBounds = new Rectangle(0,0,32,32);
	public boolean isVisible = true;
	public BufferedImage lowerTexture;
	public BufferedImage upperTexture;
	public boolean isColliding = false;
	public boolean passable = true;
	public Point framePoints = new Point(0,0);
	public Dimension specialDimensions = new Dimension(-16,-16);
	public int index = -1;
	public Object(Rectangle rec)
	{
		this.bounds = rec;
	}
	public Object()
	{
		
	}
	public void setUpperTexture(BufferedImage upper)
	{
		if(upper!=null)
		{
			this.upperTexture = upper;
		}
	}
	public void setTexture(BufferedImage upper,BufferedImage lower)
	{
		if(lower!=null)
		{
			this.lowerTexture = lower;
		}
		if(upper!=null)
		{
			this.upperTexture = upper;
		}
	}
	public Rectangle getBounds()
	{
		return this.bounds;
	}
	public void moveUp()
	{
		this.bounds.y-=this.velocity.y;
		this.direction = Direction.Up;
		isMoving = true;
	}
	public void moveDown()
	{
		this.bounds.y+=this.velocity.y;
		this.direction = Direction.Down;
		isMoving = true;
	}
	public void moveRight()
	{
		this.bounds.x+=this.velocity.x;
		this.direction = Direction.Right;
		isMoving = true;
	}
	public void moveLeft()
	{
		this.bounds.x-=this.velocity.x;
		this.direction = Direction.Left;
		isMoving = true;
	}
	boolean isMoving = false;
	public void onCollide(Object recThree) {
		
		// TODO Auto-generated method stub
		if(this.direction ==Direction.Up)
		{
			this.bounds.y= this.bounds.y+this.velocity.y;
		}
		else if(this.direction ==Direction.Down)
		{
			this.bounds.y= this.bounds.y-this.velocity.y;
		}
		else if(this.direction == Direction.Left)
		{
			this.bounds.x= this.bounds.x+this.velocity.x;
		}
		else if(this.direction ==Direction.Right)
		{
			this.bounds.x= this.bounds.x-this.velocity.x;
		}	
		isColliding = true;	
		isMoving = false;
	}
	public void onUpdate(double delta) {
		// TODO Auto-generated method stub
		
	}
	public Type getLowerType()
	{
		return this.lowerType;
	}
	public void onPaint(Graphics g, ImageObserver obj) {
		// TODO Auto-generated method stub
		if(this.isVisible)
		{
			if(upperType==Type.Blank&&harvested)
			{
				//g.drawImage(lowerTexture,this.bounds.x,this.bounds.y,this.bounds.width-specialDimensions.width,this.bounds.height-specialDimensions.height,obj);
			}
			else
			{
				g.drawImage(lowerTexture,this.bounds.x,this.bounds.y,this.bounds.width-specialDimensions.width,this.bounds.height-specialDimensions.height,obj);
			}
		}
	}
	public void onUpperPaint(Graphics g, ImageObserver obj) {
		// TODO Auto-generated method stub
		if(this.isVisible&&upperTexture!=null&&!harvested)
		{
			g.drawImage(upperTexture,this.bounds.x+this.upperBounds.x+6,this.bounds.y+this.upperBounds.y,upperBounds.width-specialDimensions.width,upperBounds.height-specialDimensions.height,obj);
		}
	}
	public boolean harvested = false;
	int hits = 5;
	public void harvest() {
		// TODO Auto-generated method stub
		if(hits <=0)
		{
			Locker.proticol = "harvest";
			Locker.sendLine =index+"";
			harvested = true;
		}
		else
		{
			hits--;
		}
	}
}
