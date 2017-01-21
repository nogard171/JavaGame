import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Entity
{
	private String name = "";
	private String quadName = "GRASS";
	private Vector2f position = new Vector2f(100, 100);
	private Vector2f origin = new Vector2f(0, 0);
	private float rotation = 0;
	private Vector2f scale = new Vector2f(1, 1);
	private Color color = new Color(1,1,1);

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public String getQuadName()
	{
		return quadName;
	}

	public void setQuadName(String quadName)
	{
		this.quadName = quadName;
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public Vector2f getOrigin()
	{
		return origin;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public void setOrigin(Vector2f origin)
	{
		this.origin = origin;
	}

	public void increaseRotation(float r)
	{
		this.setRotation(this.getRotation() + r);
	}
}
