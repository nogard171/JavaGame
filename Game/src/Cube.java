import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Cube
{
	private Vector3f position = new Vector3f(0,0,0);
	private Vector2f offset = new Vector2f(0,0);
	private Quad quad = new Quad();
	public String type = "grass";
	public Quad getQuad()
	{
		return quad;
	}
	public void setQuad(Quad quad)
	{
		this.quad = quad;
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
		this.offset = new Vector2f(x,y);
	}
}
