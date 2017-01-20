import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class View
{
	Vector2f position = new Vector2f(0, 0);
	int viewWidth = 0;
	int viewHeight = 0;
	public View()
	{
		this.viewWidth = (int)(Display.getWidth()/32);
		this.viewHeight = (int)(Display.getHeight()/32);
	}
	public Vector2f getPosition()
	{
		return position;
	}
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
	public int getViewWidth()
	{
		return viewWidth;
	}
	public void setViewWidth(int viewWidth)
	{
		this.viewWidth = viewWidth;
	}
	public int getViewHeight()
	{
		return viewHeight;
	}
	public void setViewHeight(int viewHeight)
	{
		this.viewHeight = viewHeight;
	}
	public void moveView(Vector2f move)
	{
		this.setPosition(new Vector2f(this.getPosition().getX()+move.getX(),this.getPosition().getY()+move.getY()));
	}
}
