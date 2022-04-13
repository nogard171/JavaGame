package utils;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;

import ui.UIControl;

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

	public static boolean inside(Rectangle bounds, Vector2f point) {
		return inside(bounds, new Point((int) point.x, (int) point.y));
	}

	public static boolean inside(Rectangle bounds, Point point) {
		boolean isColliding = false;
		if (point.x >= bounds.getX() && point.x <= bounds.getX() + bounds.getWidth() && point.y >= bounds.getY()
				&& point.y <= bounds.getY() + bounds.getHeight()) {
			isColliding = true;

		}
		return isColliding;
	}
}
