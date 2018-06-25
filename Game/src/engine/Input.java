package engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import classes.Position;
import data.DataHub;

public class Input {

	public void poll() {
		this.pollMouse();
		this.pollKeys();
	}

	public void pollMouse() {
		DataHub.mouse.setX(Mouse.getX());
		DataHub.mouse.setY(Mouse.getY());

		if (DataHub.mouseButtons == null) {
			DataHub.mouseButtons = new boolean[Mouse.getButtonCount()];
		}

		for (int m = 0; m < DataHub.mouseButtons.length; m++) {
			if (Mouse.isButtonDown(m)) {
				DataHub.mouseButtons[m] = true;
			} else {
				DataHub.mouseButtons[m] = false;
			}
		}
	}

	public void pollKeys() {
		if (DataHub.keys == null) {
			DataHub.keys = new boolean[Keyboard.getKeyCount()];
		}

		for (int k = 0; k < DataHub.keys.length; k++) {
			if (Keyboard.isKeyDown(k)) {
				DataHub.keys[k] = true;
			} else {
				DataHub.keys[k] = false;
			}
		}
	}

	public boolean mouseIsDown(int index) {
		return DataHub.mouseButtons[index];
	}

	public boolean isKeyDown(int index) {
		return DataHub.keys[index];
	}
}
