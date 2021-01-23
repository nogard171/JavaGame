package classes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import core.Input;

public class MenuDropDown {
	LinkedList<MenuItem> items = new LinkedList<MenuItem>();
	public Rectangle bounds;
	public boolean hovered = false;
	public boolean showDropDown = false;

	public boolean addItem(MenuItem item) {
		boolean added = false;
		if (!items.contains(item)) {
			added = items.add(item);
		}
		return added;
	}

	public void update() {
		hovered = bounds.contains(new Point(Input.getMouseX(), Input.getMouseY()));

		if (showDropDown) {
			for (MenuItem item : items) {
				item.update();
			}
		}
		
		if (hovered && Input.isMouseDown(0)) {
			showDropDown = true;
		} else if (Input.isMouseDown(0)) {
			showDropDown = false;
		}
	}

	public LinkedList<MenuItem> getItems() {
		return items;
	}
}
