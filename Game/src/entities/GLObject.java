package entities;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class GLObject
{
	private Vector2f displayListCoords = new Vector2f(0,0);
	private Bound bounds = new Bound(0,0,32,32);
	private Vector2f spriteOffset = new Vector2f(0,0);
	private Boolean hasBound = false;
	public Boolean getHasBound()
	{
		return hasBound;
	}
	public Point getPosition()
	{
		return new Point((int)this.getBounds().getX(),(int)this.getBounds().getY());
	}
	public void setHasBound(Boolean hasBound)
	{
		this.hasBound = hasBound;
	}
	public Vector2f getDisplayListCoords()
	{
		return displayListCoords;
	}
	public void setDisplayListCoords(Vector2f displayListCoords)
	{
		this.displayListCoords = displayListCoords;
	}
	public Bound getBounds()
	{
		return bounds;
	}
	public void setBounds(Bound bounds)
	{
		this.bounds = bounds;
	}
	public Vector2f getSpriteOffset()
	{
		return spriteOffset;
	}
	public void setSpriteOffset(Vector2f spriteOffset)
	{
		this.spriteOffset = spriteOffset;
	}
	public void setPosition(int x, int y)
	{
		this.bounds = new Bound(x,y,this.bounds.getWidth(),this.bounds.getHeight());
	}
}
