package entities;

import java.awt.Dimension;

import org.lwjgl.opengl.GL11;

public class GLBound
{
	private int displayID = -1;
	private Dimension size = new Dimension(32, 32);
	public GLBound()
	{
	}
	public GLBound(int i, int j)
	{
		this.setSize(new Dimension(i,j));
	}

	public int getDisplayID()
	{
		return displayID;
	}

	public void setDisplayID(int displayIDs)
	{
		this.displayID = displayIDs;
	}

	public Dimension getSize()
	{
		return size;
	}

	public void setSize(Dimension size)
	{
		this.size = size;
	}
}
