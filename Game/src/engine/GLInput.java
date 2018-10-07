package engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GLInput {
	private boolean[] mouseButtons;
	private boolean[] keys;

	public GLInput() {
		this.Setup();
	}

	private void Setup() {

		this.mouseButtons = new boolean[Mouse.getButtonCount()];
		this.keys = new boolean[Keyboard.getKeyCount()];
		for (boolean button : this.mouseButtons) {
			button = false;
		}
		for (boolean key : this.keys) {
			key = false;
		}
	}

	public void Update() {

	}
}
