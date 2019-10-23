package classes;

import org.lwjgl.util.vector.Vector2f;

public class UIControl {
	private String name = "";
	private Vector2f position = new Vector2f(0, 0);

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void update() {

	}
}
