import org.lwjgl.util.vector.Vector2f;

public class Sprite
{
	private int DisplayList = -1;
	private Vector2f textureCoords = new Vector2f(0,0);
	public int getDisplayList()
	{
		return DisplayList;
	}
	public void setDisplayList(int displayList)
	{
		DisplayList = displayList;
	}
	public Vector2f getTextureCoords()
	{
		return textureCoords;
	}
	public void setTextureCoords(Vector2f textureCoords)
	{
		this.textureCoords = textureCoords;
	}
}
