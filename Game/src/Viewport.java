import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;

public class Viewport
{
	private Vector2f position = new Vector2f(0,0);
	private Dimension size = new Dimension(800,600);
	public Vector2f getPosition()
	{
		return position;
	}
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
	public Dimension getSize()
	{
		return size;
	}
	public void setSize(Dimension size)
	{
		this.size = size;
	}
}
