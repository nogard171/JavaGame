package game;

import org.lwjgl.input.Keyboard;

import engine.GLDisplay;

public class KeyHandler {
	public void Update() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					new Main().Destroy();
				}
			}
		}
	}
}
