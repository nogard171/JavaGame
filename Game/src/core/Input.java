package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	boolean waitForKey = false;
	public static int getMouseX() {
		return Mouse.getX();
	}

	public static int getMouseY() {
		return Window.getHeight() - Mouse.getY();
	}

	public static boolean isMousePressed(int button) {
		boolean isPressed = false;

		// while (Mouse.next()) {
		if (Mouse.getEventButtonState()) {
			if (Mouse.isButtonDown(button)) {
				isPressed = true;
			}
		}
		// }
		return isPressed;
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

	public static boolean isMainAction() {
		return Mouse.isButtonDown(GameData.mainAction);
	}

	public static boolean isSecondaryAction() {
		return Mouse.isButtonDown(GameData.secondaryAction);
	}
}
