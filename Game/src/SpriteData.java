import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class SpriteData {
	Vector2f[] vertex = { 
			//0
			new Vector2f(1f,0),
			//1
			new Vector2f(2f,0.5f),
			//2
			new Vector2f(1f,1f),
			//3
			new Vector2f(0f,0.5f),
			//4
			new Vector2f(0f,0.75f),
			//5
			new Vector2f(1f,1.25f),
			//6
			new Vector2f(2f,0.75f)
			};
	int[][] faces = { { 0,1,2}, { 0, 3, 2 }, {3,4,5}, {3,2,5}, {1,6,5}, {1,2,5}};
	Color[] colors = { new Color(64, 128, 64), new Color(64, 128, 64), new Color(32, 92, 32), new Color(32, 92, 32),
			new Color(16, 64, 16), new Color(16, 64, 16) };
	Vector2f[] gridVertex = { 
			//0
			new Vector2f(1f,0.05f),
			//1
			new Vector2f(1f,0),
			//2
			new Vector2f(2f,0.5f),
			//3
			new Vector2f(1.95f,0.5f),
			//4
			new Vector2f(1f,1f),
			//5
			new Vector2f(1f,0.95f),
			//3
			new Vector2f(0f,0.5f),
			//3
			new Vector2f(0.05f,0.5f),
			};
	int[][] gridFaces = { { 0,1,2,3},{ 2,3,5,4},{ 5,4,6,7},{ 6,7,0,1}};
}
