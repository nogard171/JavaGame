package engine;

import java.util.UUID;

public class GLObject {
	private String name = "";

	private GLVector position = new GLVector(0, 0);

	public GLObject() {
		setName(UUID.randomUUID().toString());
	}

	public GLVector getPosition() {
		return position;
	}

	public void setPosition(GLVector position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
