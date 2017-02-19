import org.lwjgl.util.Color;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;

public class Entity
{
	private Vector2f position = new Vector2f();
	private float rotationX = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private Type type = Type.BLANK;
	private String name = "";
	public Color color = new Color(1, 0, 0);

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float[] getScale()
	{
		float[] floatArray =
		{ scaleX, scaleY };
		return floatArray;
	}

	public float getRotation()
	{
		return rotationX;
	}

	public Vector2f getPosition()
	{
		return this.position;
	}

	public void setPosition(float x, float y)
	{
		this.position = new Vector2f(x, y);
	}

	public void setRotation(int x)
	{
		this.rotationX = x;
	}

	public void setScale(float x, float f)
	{
		this.scaleX = x;
		this.scaleY = f;
	}

}
