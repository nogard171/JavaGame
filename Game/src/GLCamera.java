import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GLCamera {
	boolean changed = true;
	Vector2f position = new Vector2f(0, 0);
	Vector2f size = new Vector2f(100, 100);

	public GLCamera(int width, int height) {
		this.size.x = width;
		this.size.y = height;
	}

	public boolean containsVector(Vector2f vec) {
		boolean contains = false;

		return contains;
	}

	public void Move(Vector2f velocity) {
		this.position.x += velocity.x;
		this.position.y += velocity.y;
		if(velocity.x!=0||velocity.y!=0)
		{
			changed = true;
		}
		else
		{
			changed = false;
		}			
	}

	public boolean containsChunk(GLChunk chunk)
	{
		boolean contains = false;
		
		Rectangle rec = new Rectangle((int)this.position.x,(int)this.position.y,(int)this.size.x,(int)this.size.y);
		
		float newX = ((chunk.position.x) * 32) - ((chunk.position.y) * 32);
		float newY = ((chunk.position.y) * 16) + ((chunk.position.x) * 16);
		System.out.println(newX+","+newY);
		if(rec.intersects(new Rectangle(-(int)newX+((int)size.getX()-(10*32)), -(int)newY+((int)size.getY()-(10*32)),10*64,10*64)))
		{
			contains = true;
		}
		
		return contains;
	}
}
