package engine;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import game.Settings;

public class GLInput {
	private static GLVector mousePosition = new GLVector(0, 0);
	private static HashMap<String, Boolean> mouseButtons = new HashMap<String, Boolean>();
	private static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();

	public static void poll() {
		mousePosition = new GLVector(Mouse.getX(), Settings.height-Mouse.getY());
		mouseButtons.put("LEFT", Mouse.isButtonDown(0));
		mouseButtons.put("RIGHT", Mouse.isButtonDown(1));
		while (Keyboard.next()) {
			keys.put(Keyboard.getEventKey(), Keyboard.isKeyDown(Keyboard.getEventKey()));
		}
	}

	public static boolean isMouseDown(String mouseButton) {
		return mouseButtons.get(mouseButton);
	}

	public static boolean isKeyDown(int key) {
		boolean down = false;
		if (keys.containsKey(key)) {
			down = keys.get(key);
		}
		return down;
	}

	public static GLVector getMousePosition() {
		return mousePosition;
	}
}
