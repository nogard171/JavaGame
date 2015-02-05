import java.awt.Point;
import java.awt.Rectangle;


public class Projectile {
	Rectangle bounds = new Rectangle(0,0,5,5);
	boolean shooting = false;
	int speedX= 0;
	int speedY= 0;
	public void onUpdate()
	{
		bounds.x+=speedX;
	}
	public void onCollide()
	{
		shooting = false;
	}
	public void onProject(Point origin)
	{
		this.bounds.x = origin.x;
		this.bounds.y = origin.y;
		shooting = true;
	}
}
