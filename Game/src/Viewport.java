import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;

public class Viewport
{
	private Vector2f Position = new Vector2f(0, 0);
	private Dimension size = new Dimension(100, 100);
	private int mode = GL11.GL_TRIANGLES;

	public Dimension getSize()
	{
		return size;
	}

	public void setSize(Dimension size)
	{
		this.size = size;
	}

	public Vector2f getPosition()
	{
		return Position;
	}

	public void setPosition(Vector2f position)
	{
		Position = position;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}
}
