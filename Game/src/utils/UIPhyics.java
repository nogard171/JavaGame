package utils;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;

import classes.UIControl;

public class UIPhyics {
	public static boolean inside(UIControl control, Vector2f point) {
		return inside(control, new Point((int) point.x, (int) point.y));
	}

	public static boolean inside(UIControl control, Point point) {
		boolean isColliding = false;
		if (control.isVisible) {
			if (point.x >= control.getPosition().getX()
					&& point.x <= control.getPosition().getX() + control.getSize().getWidth()
					&& point.y >= control.getPosition().getY()
					&& point.y <= control.getPosition().getY() + control.getSize().getHeight()) {
				isColliding = true;
			}
		}
		return isColliding;
	}
}
