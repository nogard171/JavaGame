package ui;

import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import utils.Input;
import utils.UIPhyics;
import utils.Window;

public class UIButton extends UIControl {
	private String material = "GRASS";
	private String text = "";
	private int fontSize = 12;
	private Color fontColor = Color.black;
	private Color backgroundColor = null;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		setText(text, 12);
	}

	public void setText(String text, int newFontSize) {
		setText(text, newFontSize, Color.black);
	}

	public void setText(String text, int newFontSize, Color newColor) {
		this.setFontColor(newColor);
		this.fontSize = newFontSize;
		this.setSize(new Size(getSize().getWidth(), newFontSize));
		this.text = text;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
