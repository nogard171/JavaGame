
public class GLImage
{
	private int displayID = -1;
	private int textureID = -1;
	
	public GLImage(int newDisplayID, int newTextureID)
	{
		this.setDisplayID(newDisplayID);
		this.setTextureID(newTextureID);
	}
	public int getDisplayID()
	{
		return displayID;
	}
	public void setDisplayID(int displayID)
	{
		this.displayID = displayID;
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
