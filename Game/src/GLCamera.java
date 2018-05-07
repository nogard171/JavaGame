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
}
