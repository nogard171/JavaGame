package Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import Objects.ObjectType;

public class Tile extends Sprite {
	public int x = 0;
	public int y = 0;
	private boolean border;
	public ObjectType type = ObjectType.OTHER;
	public ObjectType topType = ObjectType.OTHER;

	public Tile(int x2, int y2, int width, int height) {
		this.x = x2;
		this.y = y2;
		this.width = width;
		this.height = height;
	}

	public int index = -1;

	public Tile(int size, int x2, int y2, int width, int height) {
		// TODO Auto-generated constructor stub
		index = size;
		this.x = x2;
		this.y = y2;
		this.width = width;
		this.height = height;
	}
	public void Draw(Graphics g) {
		g.drawRect(this.x,this.y, width, height);
		g.setColor(Color.black);		
	}

	boolean isSolid = false;
}
