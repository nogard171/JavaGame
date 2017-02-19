import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Object
{
	private Rectangle bounds = new Rectangle(0, 0, 32, 32);
	private Type type = Type.GRASS;

	public Rectangle getBounds()
	{
		return bounds;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public Vector2f getPosition()
	{
		return new Vector2f(bounds.getX(), bounds.getY());
	}

	public Dimension getSize()
	{
		return new Dimension(bounds.getWidth(), bounds.getHeight());
	}

	public void setPosition(int x, int y)
	{
		bounds = new Rectangle(x, y, bounds.getWidth(), bounds.getHeight());
	}

	public void setSize(int w, int h)
	{
		bounds = new Rectangle(bounds.getX(), bounds.getY(), w, h);
	}
}
