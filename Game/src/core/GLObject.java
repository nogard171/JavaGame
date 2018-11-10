package core;

import org.lwjgl.util.vector.Vector2f;

public class GLObject {
	private GLType type = GLType.BLANK;

	public GLObject(GLType newType) {
		this.type = newType;
	}

	public GLType getType() {
		return this.type;
	}

	public void setType(GLType newType) {
		this.type = newType;
	}
}
