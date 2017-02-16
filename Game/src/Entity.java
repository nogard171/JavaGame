import org.lwjgl.util.Color;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;

public class Entity
{
	private Vector2f position = new Vector2f();
	private float rotationX = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private Dimension size = new Dimension(32, 32);

	public Dimension getSize()
	{
		return size;
	}

	public void setSize(Dimension size)
	{
		this.size = size;
	}

	private String name = "";

	public Color color = new Color(1, 0, 0);

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

	public void setSize(int i, int j)
	{
		this.size = new Dimension(i, j);
	}
}
