package objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TextBox {
	public Rectangle bounds = new Rectangle(0,0,200,30);
	public String value = "";
	public boolean focus = false;
	public TextBox(int i, int j) {
		// TODO Auto-generated constructor stub
		bounds.x = i;
		bounds.y = j;
	}
	public boolean intersects(Point mouse)
	{
		if(mouse.x>bounds.x&&mouse.x<bounds.x+bounds.width&&
				mouse.y>bounds.y&&mouse.y<bounds.y+bounds.height)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
