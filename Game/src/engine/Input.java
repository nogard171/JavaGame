package engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import classes.Position;

public class Input {
	public Position mouse = new Position(0, 0);
	public boolean[] mouseButtons = null;
	public boolean[] keys = null;

	public void poll() {
		this.pollMouse();
		this.pollKeys();
	}

	public void pollMouse() {
		this.mouse.setX(Mouse.getX());
		this.mouse.setY(Mouse.getY());

		if (this.mouseButtons == null) {
			this.mouseButtons = new boolean[Mouse.getButtonCount()];
		}

		for (int m = 0; m < this.mouseButtons.length; m++) {
			if (Mouse.isButtonDown(m)) {
				this.mouseButtons[m] = true;
			} else {
				this.mouseButtons[m] = false;
			}
		}
	}

	public void pollKeys() {
		if (this.keys == null) {
			this.keys = new boolean[Keyboard.getKeyCount()];
		}

		for (int k = 0; k < this.keys.length; k++) {
			if (Keyboard.isKeyDown(k)) {
				this.keys[k] = true;
			} else {
				this.keys[k] = false;
			}
		}
	}

	public boolean mouseIsDown(int index) {
		return this.mouseButtons[index];
	}

	public boolean isKeyDown(int index) {
		return this.keys[index];
	}
}
