package objects;

import java.awt.Rectangle;

public class MenuItem {
	private Rectangle bounds = new Rectangle(0,0,0,0);
	private boolean isClickable=true;
	private String Tag = "";
	public boolean isImage = true;
	public boolean isClicked = false;
	public Rectangle getBounds()
	{
		return this.bounds;
	}
	public boolean isClickable()
	{
		return isClickable;
	}
	public String getTag()
	{
		return this.Tag;
	}
	public void setBounds(Rectangle newBounds)
	{
		bounds =newBounds;
	}
	public void setClickable(boolean newClickable)
	{
		isClickable=newClickable;
	}
	public void setTag(String newTag)
	{
		Tag=newTag;
	}
}
