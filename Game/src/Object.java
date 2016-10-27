import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Object extends ObjectData
{
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

	public void move(int x, int y)
	{
		this.SetPosition(getPosition().x+x,getPosition().y+y);
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
