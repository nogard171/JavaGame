import org.lwjgl.util.Dimension;

public class QuadArray
{
	private int[][] dlid = null;
	public int[][] getDlid()
	{
		return dlid;
	}
	public int getDlid(int x, int y)
	{
		return dlid[x][y];
	}
	public void setDlid(int[][] dlid)
	{
		this.dlid = dlid;
	}
	public void setDlid(int x, int y, int dlid)
	{
		this.dlid[x][y] = dlid;
	}
	public int getTextureid()
	{
		return textureid;
	}
	public void setTextureid(int textureid)
	{
		this.textureid = textureid;
	}
	public Dimension getSize()
	{
		return size;
	}
	public void setSize(Dimension size)
	{
		this.size = size;
	}
	private int textureid;
	private Dimension size = null;
}
