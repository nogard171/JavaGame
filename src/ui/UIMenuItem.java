package ui;

import org.newdawn.slick.Color;

import classes.Size;
import utils.Input;
import utils.UIPhyics;
import utils.Window;

public class UIMenuItem extends UIControl {
	private String text = "";
	private Color backgroundColor = null;
	private int fontSize = 12;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.setSize(new Size(getSize().getWidth(), fontSize));
		this.text = text;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		this.setSize(new Size(getSize().getWidth(), fontSize));
	}

}
