package ui;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import utils.Input;
import utils.Ticker;

public class UITextField extends UIControl {

	private String text = "";
	private boolean active = false;
	protected int keyDownCount = 0;
	private boolean intOnly = false;
	private boolean cursorActive = false;

	public void setIntOnly(boolean intOnly) {
		this.intOnly = intOnly;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	Ticker tick;

	@Override
	public void update() {
		super.update();
		if (tick == null) {
			tick = new Ticker();
		}

		if (this.isActive()) {
			tick.update(500);
			if (tick.hasTicked()) {
				cursorActive = !cursorActive;
			}
			List<Integer> keysDown = Input.getKeys();
			if (keysDown.size() > 0) {
				boolean shift = false;
				boolean control = false;
				int i = 0;
				for (int k : keysDown) {
					String key = Keyboard.getKeyName(keysDown.get(i)).toLowerCase();
					if (key.equals("lshift") || key.equals("rshift")) {
						shift = true;
						keysDown.remove(i);
					}
					if (key.equals("lcontrol") || key.equals("rcontrol")) {
						control = true;
						keysDown.remove(i);
					}
					i++;
				}
				if (keysDown.size() > 0) {
					if (keyDownCount == 0) {
						int key = keysDown.get(0);
						String str = Keyboard.getKeyName(key).toLowerCase();
						if ((isNumeric(str) && this.intOnly) || !this.intOnly || str.length() > 1) {
							this.eventAction.onKeyPress(this, key, shift);
						}
					}
					keyDownCount++;
				} else {
					keyDownCount = 0;
				}
			} else {
				keyDownCount = 0;
			}
		}
		else
		{
			cursorActive = false;
		}
	}

	public boolean isCursorActive() {
		return cursorActive;
	}

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
