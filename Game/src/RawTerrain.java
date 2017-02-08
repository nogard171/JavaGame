import org.lwjgl.util.vector.Vector3f;

public class RawTerrain
{
	Vector3f[] vectors =
	{};
	Byte[] indices =
	{};
	Vector3f[] normals =
	{};
	Color[] colors =
	{};

	public Vector3f[] getNormals()
	{
		return normals;
	}

	public void setNormals(Vector3f[] normals)
	{
		this.normals = normals;
	}

	public Color[] getColors()
	{
		return colors;
	}

	public void setColors(Color[] colors)
	{
		this.colors = colors;
	}

	public Vector3f getVectorsByIndice(Byte indice)
	{
		// TODO Auto-generated method stub
		return this.vectors[indice];
	}

	public Vector3f[] getVectors()
	{
		return vectors;
	}

	public void setVectors(Vector3f[] vectors)
	{
		this.vectors = vectors;
	}

	public Byte[] getIndices()
	{
		return indices;
	}

	public void setIndices(Byte[] indices)
	{
		this.indices = indices;
	}

	public Vector3f getNormalByIndice(Byte indice)
	{
		// TODO Auto-generated method stub
		return this.normals[indice];
	}

	public Color getColorByIndice(Byte indice)
	{
		// TODO Auto-generated method stub
		return this.colors[indice];
	}
}
