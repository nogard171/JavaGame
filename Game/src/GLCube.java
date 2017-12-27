import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLCube {
	public Vector3f position = new Vector3f(0, 0, 0);
	public Point position2D = new Point(0, 0);
	public boolean visible = true;
	public String type = "GRASS";

	public GLCube(int x, int y, int z) {
		int newX = x;
		int newY = y;
		int newZ = z;
		this.position = new Vector3f(newX, newY, newZ);
		 newX = (x * 32) - (z * 32);
		 newY = (y * 32);
		 newZ = (z * 16) + (x * 16);
		position2D = new Point(newX, -newZ + newY);
	}
}
