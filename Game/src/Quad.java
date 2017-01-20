import org.lwjgl.opengl.GL11;

public class Quad
{
	private String name = "";
	private int displayID = -1;
	private int textureID = -1;
	private int width = 32;
	private int height = 32;
	private int renderType = GL11.GL_TRIANGLES;

	public Quad()
	{

	}

	public Quad(String name)
	{
		this.name = name;
	}

	public Quad(int width, int height)
	{
		this.setWidth(width);
		this.setHeight(height);
	}

	public Quad(String name, int width, int height)
	{
		this.name = name;
		this.setWidth(width);
		this.setHeight(height);
	}

	public String getName()
	{
		return name;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getTextureID()
	{
		return textureID;
	}

	public void setTextureID(int textureID)
	{
		this.textureID = textureID;
	}

	public int getRenderType()
	{
		return renderType;
	}

	public void setRenderType(int renderType)
	{
		this.renderType = renderType;
	}

	public int getDisplayID()
	{
		return displayID;
	}

	public void setDisplayID(int displayID)
	{
		this.displayID = displayID;
	}
}
