package objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Skill {
	public Rectangle bounds = new Rectangle(0,0,32,32);
	public String name ="";
	public int level = 0;
	public double experience = 0l;
	public double maxExperience =0l;
	public double baseExperience = 83l;
	public String description = "";
	public BufferedImage texture=null;
	public void setTexture(BufferedImage temp)
	{
		this.texture = temp;
	}
}
