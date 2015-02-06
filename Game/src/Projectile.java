import java.awt.Point;
import java.awt.Rectangle;

import objects.Direction;


public class Projectile {
	Rectangle bounds = new Rectangle(0,0,5,5);
	Rectangle startingBounds = new Rectangle(0,0,5,5);
	boolean shooting = false;
	int speedX= 0;
	int speedY= 0;
	Direction direction = Direction.Up;
	boolean isBouncable = true;
	public void onUpdate()
	{
		bounds.x+=speedX;
		bounds.y+=speedY;		
	}
	public void onCollide(Rectangle origin)
	{
		if(isBouncable)
		{
			if(direction == Direction.Right||direction == Direction.Left)
			{
				speedY=0;
				speedX=-speedX;
				bounds.y = (origin.y+startingBounds.y)-(origin.height/2);
			}
			else
			{
				speedX=0;
				speedY=-speedY;
				//bounds.x = (origin.x-bounds.x)-origin.width/2;
			}
		}
		else
		{
			bounds.x = startingBounds.x;
			bounds.y = startingBounds.y;
			speedX=0;
			speedY=0;
			shooting = false;
		}
	}
	public void onProject(Rectangle origin)
	{
		bounds.x = origin.x;
		bounds.y = origin.y;
		startingBounds.x = origin.x;
		startingBounds.y = origin.y;
		shooting = true;
	}
}
