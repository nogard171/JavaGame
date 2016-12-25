import org.lwjgl.util.vector.Vector2f;

public class Camera
{
	Vector2f position = new Vector2f(0,0);
	public Camera(float x, float y)
	{
		position.set(x, y);
	}
	public float getX()
	{
		return this.position.getX();
	}
	public float getY()
	{
		return this.position.getY();
	}
}
