import org.lwjgl.util.vector.Vector2f;

public class RawQuad
{
	private Vector2f[] vertices = {
			new Vector2f(0,0),
			new Vector2f(0,32),
			new Vector2f(32,0),
			new Vector2f(32,32)
	};
	private byte[] indices = {
			0,1,3,
			0,2,3
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
