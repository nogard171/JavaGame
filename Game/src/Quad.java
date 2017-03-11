import java.awt.Polygon;

public class Quad
{
	private int displayID = -1;
	private int textureID = -1;
	private Polygon bounds = null;

	public int getTextureID()
	{
		return textureID;
	}

	public void setTextureID(int textureID)
	{
		this.textureID = textureID;
	}

	public int getDisplayID()
	{

		return displayID;
	}

	public void setDisplayID(int displayID)
	{
		this.displayID = displayID;
	}

	public Polygon getBounds()
	{
		return bounds;
	}

	public void setBounds(Polygon bounds)
	{
		this.bounds = bounds;
	}
}
