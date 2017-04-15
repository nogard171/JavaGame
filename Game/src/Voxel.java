
public class Voxel
{
	private int dlID = -1;
	private int textureID = -1;
	private Type type = Type.GRASS;
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
}
