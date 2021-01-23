package classes;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;

import core.*;

public class MenuItem {
	private String name = "";
	public Rectangle bounds = null;
	public boolean hovered = false;
	public Color color = Color.white;
	public AFunction func;
	private int clicked = 0;

	public MenuItem(AFunction aFunction) {
		func = aFunction;
	}

	public MenuItem() {
	}

	public void update() {
		if (bounds != null) {
			hovered = bounds.contains(new Point(Input.getMouseX(), Input.getMouseY()));
		}
		if (hovered) {
			if (func != null) {
				func.onMouseHover(this);
			}
		} else {
			if (func != null) {
				func.onMouseOut(this);
			}
		}
		if (hovered && Input.isMousePressed(0)) {

			
			if (func != null) {
				if (clicked == 0) {
					func.onClick(this);
					clicked++;
				}
			}
		} else {
			clicked = 0;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
