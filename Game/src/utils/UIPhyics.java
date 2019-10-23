package utils;

import classes.UIButton;

public class UIPhyics {
	public static boolean isColliding(UIButton control) {
		boolean isColliding = false;
		if (Window.getMouseX() >= control.getPosition().getX()
				&& Window.getMouseX() <= control.getPosition().getX() + control.getSize().getWidth()
				&& Window.getMouseY() >= control.getPosition().getY()
				&& Window.getMouseY() <= control.getPosition().getY() + control.getSize().getHeight()) {
			isColliding = true;
		}
		return isColliding;
	}
}
