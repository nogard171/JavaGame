import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class SpriteData {
	Vector2f[] vertex = { new Vector2f(1f,0), new Vector2f(2f,0.5f), new Vector2f(1f,1f), new Vector2f(0f,0.5f)};
	int[][] faces = { { 0,1,2}, { 0, 3, 2 }};
	Color[] colors = { new Color(64, 128, 64), new Color(64, 128, 64), new Color(32, 92, 32), new Color(32, 92, 32),
			new Color(16, 64, 16), new Color(16, 64, 16) };
	Vector2f[] gridVertex = { new Vector2f(0.9f,0f),new Vector2f(1f,0), new Vector2f(2f,0.5f),new Vector2f(1.9f,0.5f),  new Vector2f(0f,0.5f),new Vector2f(0.1f,0.5f),  new Vector2f(1f,1f),new Vector2f(0.9f,1f)};
	int[][] gridFaces = { { 0,1,2,3},{ 0,1,4,5},{ 4,5,6,7},{ 2,3,6,7}};
}
