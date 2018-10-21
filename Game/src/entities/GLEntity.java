package entities;

import classes.GLPosition;

public class GLEntity {
	private String name = "";
	private GLPosition position = new GLPosition(0, 0);

	public void SetPosition(float x, float y) {
		this.position.SetPosition(x, y);
	}

	public void SetPosition(GLPosition newPosition) {
		this.SetPosition(newPosition.GetX(), newPosition.GetY());
	}

	public void SetName(String newName) {
		this.name = newName;
	}

	public String GetName() {
		return this.name;
	}
}
