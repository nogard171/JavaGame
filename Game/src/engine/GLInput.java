package engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import classes.GLPosition;
import utils.GLHandler;

public class GLInput {
	private static GLPosition mousePosition = new GLPosition(0, 0);
	private static boolean[] mouseButtons;
	private static boolean[] keys;

	public GLInput() {
		Setup();
	}

	public static void Setup() {

		mouseButtons = new boolean[Mouse.getButtonCount()];
		keys = new boolean[Keyboard.getKeyCount()];
		for (boolean button : mouseButtons) {
			button = false;
		}
		for (boolean key : keys) {
			key = false;
		}
	}

	public static void Update() {
		mousePosition = new GLPosition(Mouse.getX(), -Mouse.getY() + GLHandler.GetDisplayHeight());
		for (int b = 0; b < mouseButtons.length; b++) {
			mouseButtons[b] = Mouse.isButtonDown(b);
		}
		for (int k = 0; k < keys.length; k++) {
			keys[k] = Keyboard.isKeyDown(k);
		}
	}

	public static boolean IsKeyDown(int key) {
		return keys[key];
	}

	public static boolean IsButtonDown(int button) {
		return mouseButtons[button];
	}

	public static GLPosition GetMousePosition() {
		return mousePosition;
	}
}
