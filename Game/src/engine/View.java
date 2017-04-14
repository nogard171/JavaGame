package engine;

public class View
{
	int X = 0;
	int Y = 0;
	int Width = 0;
	int Height = 0;
	
	public View(int x,int y,int width, int height)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	public int getX()
	{
		return X;
	}
	public void setX(int x)
	{
		X = x;
	}
	public int getY()
	{
		return Y;
	}
	public void setY(int y)
	{
		Y = y;
	}
	public int getWidth()
	{
		return Width;
	}
	public void setWidth(int width)
	{
		Width = width;
	}
	public int getHeight()
	{
		return Height;
	}
	public void setHeight(int height)
	{
		Height = height;
	}
}
