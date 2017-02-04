
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class RawCube
{
	Vector3f[] vectors =
	{ 
			new Vector3f(0, 0, 0),//0
			new Vector3f(1, 0, 0),//1
			new Vector3f(0, 1, 0),//2
			new Vector3f(0, 0, 1),//3
			new Vector3f(1, 1, 0),//4
			new Vector3f(1, 0, 1),//5
			new Vector3f(0, 1, 1),//6
			new Vector3f(1, 1, 1),//7
	};
	
	/*
	  	0,1,5,
		5,3,0,
	 */
	
	Color[] colors = {
			new Color(255,0,0),//0
			new Color(0.3f,0.2f,0),//1
			new Color(0,0.3f,0),//2
			new Color(0.4f,0.3f,0.1f),//3
			new Color(0.2f,0.1f,0),//4
	};
	int[] indices = {
			1,0,2,
			2,4,1,
			
			2,7,4,
			2,6,7,
			
			0,3,6,
			6,2,0,
			
			3,5,7,
			7,6,3,
			
			0,1,5,
			5,3,0,
			
			5,1,4,
			4,7,5
			
	};int[] colorIndices = {
			1,1,1,
			1,1,1,
			
			2,2,2,
			2,2,2,
			
			3,3,3,
			3,3,3,
			
			1,1,1,
			1,1,1,
			
			4,4,4,
			4,4,4,
			
			4,4,4,
			4,4,4,
			
	};
	int[] textureIndices = {
			7,8,4,
			4,3,7,
			
			2,1,3,
			2,0,1,
			
			7,8,4,
			4,3,7,
			
			7,8,4,
			4,3,7,
			
			3,4,5,
			5,1,3,
			
			7,8,4,
			4,3,7,
			
	};
	Vector2f[] textureVectors =
		{ 
				new Vector2f(0, 0),//0
				new Vector2f(0.5f, 0),//1
				new Vector2f(0, 0.5f),//2
				new Vector2f(0.5f, 0.5f),//3
				new Vector2f(1, 0.5f),//4
				new Vector2f(1, 0),//5
				new Vector2f(0, 1),//6
				new Vector2f(0.5f, 1),//7
				new Vector2f(1, 1),//8
		};
	
	public Color[] getColors()
	{
		return colors;
	}
	public Color getColorByIndice(int indice)
	{
		return colors[indice];
	}
	public void setColors(Color[] colors)
	{
		this.colors = colors;
	}
	public int[] getColorIndices()
	{
		return colorIndices;
	}
	public void setColorIndices(int[] colorIndices)
	{
		this.colorIndices = colorIndices;
	}
	public int[] getTextureIndices()
	{
		return textureIndices;
	}
	public void setTextureIndices(int[] textureCoords)
	{
		this.textureIndices = textureCoords;
	}
	public Vector2f[] getTextureVectors()
	{
		return textureVectors;
	}
	public void setTextureVectors(Vector2f[] textureVectors)
	{
		this.textureVectors = textureVectors;
	}
	public Vector3f[] getVectors()
	{
		return vectors;
	}
	public void setVectors(Vector3f[] vectors)
	{
		this.vectors = vectors;
	}
	public int[] getIndices()
	{
		return indices;
	}
	public void setIndices(int[] indices)
	{
		this.indices = indices;
	}
	public Vector3f getVectorsByIndice(int indice)
	{
		// TODO Auto-generated method stub
		return this.vectors[indice];
	}
	public Vector2f getTextureVectorsByIndice(int textureIndice)
	{
		// TODO Auto-generated method stub
		return this.textureVectors[textureIndice];
	}
}
