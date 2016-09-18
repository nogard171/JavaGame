import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class SpriteData {
	Vector2f[] vertex = { new Vector2f(1f, 0), new Vector2f(2f, 0.5f), new Vector2f(1f, 1f), new Vector2f(0, 0.5f),
			new Vector2f(0, -0.5f), new Vector2f(1, -1f), new Vector2f(2, -0.5f) };
	int[][] faces = { { 0, 1, 2 }, { 0, 3, 2 }, { 0, 3, 4 }, { 0, 4, 5 }, { 0, 5, 6 }, { 0, 1, 6 } };
	Color[] colors = { new Color(64, 128, 64), new Color(64, 128, 64), new Color(32, 92, 32), new Color(32, 92, 32),
			new Color(16, 64, 16), new Color(16, 64, 16) };
	Vector2f[] shadowVertex = { new Vector2f(2, 0.5f), new Vector2f(3f, 0f), new Vector2f(2f, -0.5f) };
	int[][] shadowFaces = { { 2, 1, 0 } };
	boolean shadow = true;
}
