import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class RawQuad
{
	private Vector2f[] vertices = {
			new Vector2f(0,0),
			new Vector2f(0,32),
			new Vector2f(32,32),
			new Vector2f(32,0)
	};
	private byte[] indices = {
			0, 1, 2, 2, 3, 0
	};
	public Vector2f[] textureCoords = {
			new Vector2f(0.25f,0),
			new Vector2f(0.25f,0.25f),
			new Vector2f(0.5f,0),
			new Vector2f(0.5f,0.25f)
	};
	public Vector2f[] getVertices()
	{
		return vertices;
	}
	public Vector2f getVerticeFromIndice(byte indice)
	{
		return vertices[indice];
	}
	public byte[] getIndices()
	{
		return indices;
	}
}
