


import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class RawQuad
{
	Point[] vertices =	{ 
			new Point(0,0), //0
			new Point(0,1), //1
			new Point(1,1),//2
			new Point(1,0) };//3
	byte[] indices =
	{ 0, 1, 2, 2, 3, 0 };
	Vector2f[] textureCoords =	{ 
			new Vector2f(0,0), //0
			new Vector2f(0,1), //1
			new Vector2f(1,1),//2
			new Vector2f(1,0) };//3
	public Vector2f[] getTextureCoords()
	{
		return textureCoords;
	}
	public Vector2f getTextureCoords(byte indice)
	{
		return textureCoords[indice];
	}
	public void setTextureCoords(Vector2f[] textureCoords)
	{
		this.textureCoords = textureCoords;
	}
	public Point[] getVertices()
	{
		return vertices;
	}
	public void setVertices(Point[] vertices)
	{
		this.vertices = vertices;
	}
	public byte[] getIndices()
	{
		return indices;
	}
	public void setIndices(byte[] indices)
	{
		this.indices = indices;
	}
	public Point getVertice(byte indice)
	{
		// TODO Auto-generated method stub
		return this.vertices[indice];
	}
}
