import org.lwjgl.util.vector.Vector2f;

public class RawQuad
{
	Vector2f[] vectors = {
			new Vector2f(0,0),//0
			new Vector2f(1,0),//1
			new Vector2f(1,1),//2
			new Vector2f(0,1)//3
	};
	byte[] indices = {
			0,3,2,
			0,1,2
	};
	Vector2f[] textureVectors = {
			new Vector2f(0,0),//0
			new Vector2f(1,0),//1
			new Vector2f(1,1),//2
			new Vector2f(0,1)//3
	};
	byte[] textureIndices = {
			0,3,2,
			0,1,2	
	};
	public Vector2f getVector(byte indice)
	{
		return vectors[indice];
	}
	public void setVectors(Vector2f[] vectors)
	{
		this.vectors = vectors;
	}
	public byte[] getIndices()
	{
		return indices;
	}
	public void setIndices(byte[] indices)
	{
		this.indices = indices;
	}
	public Vector2f getTextureVector(byte indice)
	{
		return textureVectors[indice];
	}
	public Vector2f[] getTextureVectors()
	{
		return textureVectors;
	}
	public void setTextureVectors(Vector2f[] textureVectors)
	{
		this.textureVectors = textureVectors;
	}
	
	public byte[] getTextureIndices()
	{
		return textureIndices;
	}
	public void setTextureIndices(byte[] textureIndices)
	{
		this.textureIndices = textureIndices;
	}
}
