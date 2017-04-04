package entities;
import java.awt.Dimension;

import org.lwjgl.opengl.GL11;

public class GLSprite
{
	private int[][] displayIDs;
	private int textureID = -1;
	private Dimension size= new Dimension(32,32);
	public GLSprite()
	{
	}
	public GLSprite(int width, int height)
	{
		this.setSize(new Dimension(width,height));
	}
	public Dimension getSize()
	{
		return size;
	}
	public void setSize(Dimension size)
	{
		this.size = size;
	}
	public void setDisplayIDs(int[][] displayIDs)
	{
		this.displayIDs = displayIDs;
	}
	public int getDisplayID(int x,int y)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.getTextureID());
		int newDisplayID = -1;
		
		if(x<displayIDs.length&& y<displayIDs[0].length)
		{
			newDisplayID = displayIDs[x][y];
		}
		return newDisplayID;
	}
	public int getDisplayID()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.getTextureID());
		int newDisplayID = displayIDs[0][0];
		return newDisplayID;
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
