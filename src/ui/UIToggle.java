package ui;

import java.awt.Rectangle;

import org.newdawn.slick.Color;

import utils.Renderer;

public class UIToggle extends UIControl {
	private boolean toggle = false;

	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}
}
