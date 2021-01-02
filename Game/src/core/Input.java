package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Input {
	boolean waitForKey = false;
	static int[] mouseDownCount = new int[2];

	public static boolean isMousePressed(int button) {
		boolean returnDown = false;
		boolean isMouseDown = Mouse.isButtonDown(button);
		if (mouseDownCount[button] <= 0 && isMouseDown) {
			returnDown = true;
		}
		if (mouseDownCount[button] > 0 && !isMouseDown) {
			mouseDownCount[button] = 0;
		}
		if (isMouseDown) {
			mouseDownCount[button]++;
		}
		return returnDown;
	}

	public static int getMouseX() {
		return Mouse.getX();
	}

	public static int getMouseY() {
		return Window.getHeight() - Mouse.getY();
	}

	public static Vector2f getMousePosition() {
		return new Vector2f(getMouseX(), getMouseY());
	}

	public static boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}

	public static int getKeyPressed() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (!Keyboard.isRepeatEvent()) {
					return Keyboard.getEventKey();
				}
			}
		}

		return -1;
	}

	public static String getKey(int keycode) {
		return Keyboard.getKeyName(keycode);
	}

	public static boolean isKeyPressed(int key) {
		boolean isPressed = false;
		while (Keyboard.next()) {
			if (key < 0) {
				continue;
			}
			if (Keyboard.getEventKeyState()) {
				if (!Keyboard.isRepeatEvent()) {
					if (Keyboard.isKeyDown(key)) {
						isPressed = true;
					}
				}
			}
		}
		return isPressed;
	}
}