import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Object extends ObjectData
{
	public boolean collosion = false;

	public Point getPosition()
	{
		return new Point(this._bounds.x, this._bounds.y);
	}

	public void SetPosition(Point newPosition)
	{
		this._bounds = new Rectangle(newPosition.x, newPosition.y, this._bounds.width, this._bounds.height);
	}

	public void SetPosition(int x, int y)
	{
		this._bounds = new Rectangle(x, y, this._bounds.width, this._bounds.height);
	}

	Direction direction = Direction.NORTH;
	Direction collisionDir = Direction.NONE;

	public void move(int x, int y)
	{
		int xSpeed = getPosition().x;
		int ySpeed = getPosition().y;

		if (x < 0)
		{
			direction = Direction.WEST;
		}
		else if (x > 0)
		{
			direction = Direction.EAST;
		}
		if (y < 0)
		{
			direction = Direction.NORTH;
		}
		else if (y > 0)
		{
			direction = Direction.SOUTH;
		}
		if (direction != collisionDir&&(direction!=Direction.EAST||direction!=Direction.WEST))
		{
			ySpeed += y;
		}
		if (direction != collisionDir&&(direction!=Direction.NORTH||direction!=Direction.SOUTH))
		{
			xSpeed += x;
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
