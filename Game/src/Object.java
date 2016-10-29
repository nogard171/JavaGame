import java.awt.Dimension;
import java.awt.Rectangle;

import org.lwjgl.util.Point;

public class Object extends ObjectData
{
	public boolean collosion = false;

	public Point getPosition()
	{
		return new Point(this._bounds.x, this._bounds.y);
	}

	public void SetPosition(Point newPosition)
	{
		this._bounds = new Rectangle(newPosition.getX(), newPosition.getY(), this._bounds.width, this._bounds.height);
	}

	public void SetPosition(int x, int y)
	{
		this._bounds = new Rectangle(x, y, this._bounds.width, this._bounds.height);
	}

	Direction direction = Direction.NORTH;
	Direction collisionDir = Direction.NONE;
	public org.lwjgl.util.Point texture_Coords = new Point(0, 0);
	float tex_X = 0;

	public void move(int x, int y)
	{
		int xSpeed = getPosition().getX();
		int ySpeed = getPosition().getY();

		if (x < 0)
		{
			direction = Direction.WEST;
			this.texture_Coords = new Point(0, 3);
		}
		if (x > 0)
		{
			direction = Direction.EAST;
			this.texture_Coords = new Point(0, 2);
		}
		if (y < 0)
		{
			direction = Direction.NORTH;
			this.texture_Coords = new Point(0, 1);
		}
		if (y > 0)
		{
			direction = Direction.SOUTH;
			this.texture_Coords = new Point(0, 0);
		}
		if (direction != collisionDir && (direction != Direction.EAST || direction != Direction.WEST))
		{
			ySpeed += y;

		}
		if (direction != collisionDir && (direction != Direction.NORTH || direction != Direction.SOUTH))
		{
			xSpeed += x;
		}
		if (x != 0 || y != 0)
		{
			this.setTextureX(tex_X);
			if (tex_X > 4)
			{
				tex_X = 0;
			}
			tex_X += 0.05;
		}
		else
		{
			this.setTextureX(0);
		}
		this.SetPosition(xSpeed, ySpeed);
		if (collosion && collisionDir == Direction.NONE)
		{
			collisionDir = direction;
		} else if (!collosion)
		{
			collisionDir = Direction.NONE;
		}

	}
	public void setTextureX(float value)
	{
		this.texture_Coords = new Point((int) value, this.texture_Coords.getY());
	}
	public void setTextureY(float value)
	{
		this.texture_Coords = new Point(this.texture_Coords.getX(),(int) value);
	}
	public Dimension getSize()
	{
		return new Dimension(this._bounds.width, this._bounds.height);
	}

	public void setSize(Dimension size)
	{
		this._bounds = new Rectangle(this._bounds.x, this._bounds.y, size.width, size.height);
	}

	public void setSize(int width, int height)
	{
		this._bounds = new Rectangle(this._bounds.x, this._bounds.y, width, height);
	}

	public String getType()
	{
		return this._type;
	}

	public void setType(String newType)
	{
		this._type = newType;
	}

	public String getName()
	{
		// TODO Auto-generated method stub
		return this._name;
	}

}
