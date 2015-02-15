package objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Skill {
	public Rectangle bounds = new Rectangle(0,0,32,32);
	public String name ="";
	public int level = 0;
	public double experience = 0;
	public double maxExperience =83;
	public double baseExperience = 83;
	public String description = "test";
	public BufferedImage texture=null;
	public boolean isHoverable = true;
	public boolean isHovered = false;
	public void setTexture(BufferedImage temp)
	{
		this.texture = temp;
	}
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return bounds;
	}
}
