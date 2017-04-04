package entities;

import org.lwjgl.util.Point;

public class Bound
{
	private float X = 0;
	private float Y = 0;
	private float Width= 0;
	private float Height = 0;
	public Bound(float x, float y, float width, float height)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	public float getX()
	{
		return X;
	}
	public void setX(float x)
	{
		X = x;
	}
	public float getY()
	{
		return Y;
	}
	public void setY(float y)
	{
		Y = y;
	}
	public float getWidth()
	{
		return Width;
	}
	public void setWidth(float width)
	{
		Width = width;
	}
	public float getHeight()
	{
		return Height;
	}
	public void setHeight(float height)
	{
		Height = height;
	}
	public Boolean intersects(Bound bound)
	{
		Boolean intersect = false;
		if(((this.getX()<=bound.getX()&&this.getX()+this.getWidth()>=bound.getX())||
				(this.getX()<=bound.getX()+bound.getWidth()&&this.getX()+this.getWidth()>=bound.getX()+bound.getWidth()))&&
				((this.getY()<=bound.getY()&&this.getY()+this.getHeight()>=bound.getY())||
						(this.getY()<=bound.getY()+bound.getHeight()&&this.getY()+this.getHeight()>=bound.getY()+bound.getHeight())))
		{
			intersect = true;
		}
		return intersect;
	}
}
