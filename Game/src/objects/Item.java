package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Item {
	public Point position = new Point(200, 200);
	public BufferedImage texture;
	
	public void draw(Graphics g) {
		
		g.drawImage(texture,position.x,position.y,null);
	}
}
