import org.lwjgl.util.vector.Vector3f;

public class Voxel
{
	private int dlID = -1;
	private int textureID = -1;
	private Type type = Type.GRASS;
	private Vector3f position = new Vector3f(0,0,0);
	public Vector3f getPosition()
	{
		return position;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public int getDlID()
	{
		return dlID;
	}

	public void setDlID(int dlID)
	{
		this.dlID = dlID;
	}

	public int getTextureID()
	{
		return textureID;
	}

	public void setTextureID(int textureID)
	{
		this.textureID = textureID;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;		
	}
}
