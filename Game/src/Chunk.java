import static org.lwjgl.opengl.GL11.glCallList;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class Chunk
{
	String name = "";
	HashMap<String, Quad> quads = new HashMap<String, Quad>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	float x = 0;

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	float y = 0;
	float width = 0;
	float height = 0;

	public void setBounds(float cx, float cy, float w, float h)
	{
		this.setX(cx);
		this.setY(cy);
		this.setWidth(w);
		this.setHeight(h);
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)this.getX(),(int)this.getY(),(int)this.getWidth(),(int)this.getHeight());
	}

}
