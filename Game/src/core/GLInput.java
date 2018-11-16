package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GLInput {
	public float getAxis(String key) {
		float axisValue = 0;
		switch (key.toLowerCase()) {
		case "mouse x":
			axisValue = Mouse.getX();
			break;
		case "mouse y":
			axisValue = Mouse.getY();
		}
		return axisValue;
	}

	public boolean getButton(String key) {
		boolean buttonValue = false;

		switch (key.toLowerCase()) {
		case "left":
			buttonValue = Keyboard.isKeyDown(Keyboard.getKeyIndex("A"));
			break;
		case "right":
			buttonValue = Keyboard.isKeyDown(Keyboard.getKeyIndex("D"));
		}
		return buttonValue;
	}

	public void update() {
		System.out.println("Mouse: " + this.getButton("left"));
	}
}
