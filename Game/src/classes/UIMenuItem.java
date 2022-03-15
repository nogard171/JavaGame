package classes;

import org.newdawn.slick.Color;

import utils.Input;
import utils.UIPhyics;
import utils.Window;

public class UIMenuItem extends UIControl {
	private String text = "";
	private Color backgroundColor = null;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.setSize(new Size(getSize().getWidth(), 12));
		this.text = text;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
