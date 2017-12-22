import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLCube {
	public Vector3f position = new Vector3f(0, 0, 0);
	public Point position2D = new Point(0, 0);
	public boolean visible = true;
	public String type = "GRASS";

	public GLCube(int x, int y, int z) {
		int newX = (x * 32) - (z * 32);
		int newY = (y * 32);
		int newZ = (z * 16) + (x * 16);
		position2D = new Point(newX, -newZ + newY);
		this.position = new Vector3f(newX, newY, newZ);
	}
}
