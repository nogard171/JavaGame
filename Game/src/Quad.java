import org.lwjgl.util.Dimension;
import org.newdawn.slick.opengl.Texture;

public class Quad
{
	private int dlid = -1;
	private int textureid;
	private Dimension size = null;
	
	public Dimension getSize()
	{
		return size;
	}

	public void setSize(int width,int height)
	{
		this.size = new Dimension(width,height);
	}

	public int getTextureID()
	{
		return textureid;
	}

	public void setTextureID(int textureid)
	{
		this.textureid = textureid;
	}

	public int getDlid()
	{
		return dlid;
	}

	public void setDlid(int dlid)
	{
		this.dlid = dlid;
	}
}
