import java.awt.Polygon;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLObject
{
	private Vector3f position = new Vector3f(0, 0, 0);
	private Vector2f offset = new Vector2f(0, 0);
	private GLQuad gLQuad = new GLQuad();
	public String type = "grass";

	public GLQuad getQuad()
	{
		return gLQuad;
	}

	public void setQuad(GLQuad gLQuad)
	{
		this.gLQuad = gLQuad;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public Vector2f getOffset()
	{
		return offset;
	}

	public void setOffset(Vector2f offset)
	{
		this.offset = offset;
	}

	public void setOffset(float x, float y)
	{
		this.offset = new Vector2f(x, y);
	}

	public Polygon getBounds()
	{
		Polygon newBounds = new Polygon();
		if (gLQuad != null)
		{
			for (int b = 0; b < this.gLQuad.getBounds().npoints; b++)
			{
				newBounds.addPoint((int) (this.gLQuad.getBounds().xpoints[b] + this.getPosition().getX()),
						(int) (this.gLQuad.getBounds().ypoints[b] + this.getPosition().getY()));
			}
		}
		return newBounds;
	}
}
