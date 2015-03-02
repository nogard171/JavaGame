package objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Slot{
	public Point position = new Point(0,0);
	public Dimension dim = new Dimension(32,32);
	public Item obj = new Item();
	public void draw(Graphics g)
	{
		obj.draw(g);
		g.drawRect(position.x,position.y,dim.width, dim.height);
	}
}
