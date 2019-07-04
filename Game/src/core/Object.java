package core;

import org.newdawn.slick.Color;

public class Object {
	private Type type = Type.AIR;
	private int height = 0;
	private Color color = Color.white;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
