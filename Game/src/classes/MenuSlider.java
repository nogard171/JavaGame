package classes;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;

import core.Input;

public class MenuSlider {
	public String name = "";
	public int value = 0;
	public int maxValue = 100;
	public Rectangle bounds;
	public Color color = Color.white;
	public Color backgroundColor = Color.white;
	public boolean hovered = false;

	public void update() {
		if (bounds != null) {
			hovered = bounds.contains(new Point(Input.getMouseX(), Input.getMouseY()));
		}
		if (hovered && Input.isMouseDown(0)) {
			float mouseX = Input.getMouseX() - bounds.x;
			System.out.println("MouseX: " + value);
			value = (int) (((float) mouseX / (float) bounds.width) * (float) 100);
			if (value == 99) {
				value = 100;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
