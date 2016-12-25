


import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class RawQuad
{
	Vector3f[] vertices =	{ 
			new Vector3f(0,0.75f,0), 
			new Vector3f(1,1.25f,0), 
			new Vector3f(1,1.5f,0),
			new Vector3f(0,2,0),
			new Vector3f(0,1.5f,1),
			new Vector3f(0,1.25f,1),
			new Vector3f(1,1,1) };
	byte[] indices =
	{ 
			0,1,2,
			0,2,6,
			0,4,5,
			0,6,4,
			6,2,3,
			6,4,3};
	byte[] textureIndices =
		{ 
				0,1,3,
				2,3,1,
				0,2,3,
				1,3,0,
				1,2,3,
				0,1,2};
	Vector2f[] textureCoords =	{ 
			new Vector2f(1,1), 
			new Vector2f(0,1), 
			new Vector2f(0,0),			
			new Vector2f(1,0)};
	byte[] colorIndices =
		{ 
				1,1,1,//bottomrightlower
				1,1,1,//bottomrightupper
				2,2,2,//bottomleftlower
				2,2,2,//bottomleftupper
				0,0,0,//upperright
				0,0,0//upperleft
				};
	Vector3f[] Colors =	{ 
			new Vector3f(1,1,1), 
			new Vector3f(0.7f,0.7f,0.7f), 
			new Vector3f(0.8f,0.8f,0.8f), 
			new Vector3f(0.8f,0,0)};
	public byte[] getTextureIndices()
	{
		return textureIndices;
	}
	public void setTextureIndices(byte[] textureIndices)
	{
		this.textureIndices = textureIndices;
	}
	public byte getColorIndices(byte index)
	{
		if(index>colorIndices.length-1)
		{
			index =0;
		}
		return colorIndices[index];
	}
	public void setColorIndices(byte[] colorIndices)
	{
		this.colorIndices = colorIndices;
	}
	public Vector3f[] getColors()
	{
		return Colors;
	}
	public void setColors(Vector3f[] colors)
	{
		Colors = colors;
	}
	public Vector2f[] getTextureCoords()
	{
		return textureCoords;
	}
	public Vector2f getTextureCoords(byte indice)
	{
		return textureCoords[textureIndices[indice]];
	}
	public void setTextureCoords(Vector2f[] textureCoords)
	{
		this.textureCoords = textureCoords;
	}
	public Vector3f[] getVertices()
	{
		return vertices;
	}
	public void setVertices(Vector3f[] vertices)
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
	public Vector3f getVertice(byte indice)
	{
		// TODO Auto-generated method stub
		return this.vertices[indice];
	}
	public void addVertice(float parseInt, float parseInt2, float parseInt3)
	{
		// TODO Auto-generated method stub
		
	}
	public byte getTextureIndices(byte index)
	{
		if(index>this.textureIndices.length-1)
		{
			index =0;
		}
		return textureIndices[index];
	}
}
