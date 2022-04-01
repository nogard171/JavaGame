package utils;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import data.EngineData;

public class Input {
	private static int[] buttons;
	private static int[] buttonCount;
	private static boolean[] buttonDown;

	private static int[] keyCount;
	private static boolean[] keysDown;

	public static void setup() {
		int bCount = Mouse.getButtonCount();
		buttons = new int[bCount];
		buttonCount = new int[bCount];
		buttonDown = new boolean[bCount];

		keyCount = new int[256];
		keysDown = new boolean[256];

		for (int b = 0; b < bCount; b++) {
			buttons[b] = b;
			buttonCount[b] = 0;
		}
		for (int k = 0; k < 256; k++) {
			keyCount[k] = 0;
			keysDown[k] = false;
		}
	}

	public static void poll() {
		for (int b = 0; b < buttons.length; b++) {
			buttonCount[b] = (buttonDown[b] == true ? 1 : 0);
			buttonDown[b] = Mouse.isButtonDown(buttons[b]);
		}
		for (int k = 0; k < 256; k++) {
			keyCount[k] = (keysDown[k] == true ? 1 : 0);
			keysDown[k] = Keyboard.isKeyDown(k);
		}
	}

	public static boolean isMousePressed(int index) {
		boolean isPressed = (buttonDown[index] == true && buttonCount[index] == 0 ? true : false);
		return isPressed;
	}

	public static boolean isKeyDown(int index) {
		if (index >= 0) {
			return keysDown[index];
		} else {
			return false;
		}
	}

	public static boolean isKeyPressed(int index) {
		if (index >= 0) {
			boolean isPressed = (keysDown[index] == true && keyCount[index] == 0 ? true : false);
			return isPressed;
		} else {
			return false;
		}
	}

	public static boolean isKeyReleased(int index) {
		if (index >= 0) {
			boolean isPressed = (keysDown[index] == true && keyCount[index] == 1 ? true : false);
			return isPressed;
		} else {
			return false;
		}
	}

	public static boolean isMouseDown(int index) {
		if (index >= 0) {
			return buttonDown[index];
		} else {
			return false;
		}
	}

	public static Vector2f getMousePosition() {
		return new Vector2f(Mouse.getX(), Window.getHeight() - Mouse.getY());
	}

	public static int getMouseWheel() {
		return Mouse.getDWheel();
	}

	public static int getMouseButton() {
		for (int b = 0; b < buttons.length; b++) {
			if (buttonDown[b]) {
				return b;
			}
		}
		return -1;
	}

	public static List<Integer> getKeys() {
		List<Integer> keys = new LinkedList<Integer>();
		for (int k = 0; k < 256; k++) {
			if (keysDown[k]) {
				if (!keys.contains(k)) {
					keys.add(k);
				}
			}
		}
		return keys;
	}

	public static int getMouseButtonCount() {
		return Mouse.getButtonCount();
	}
}
