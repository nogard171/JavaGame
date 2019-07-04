package core;

import org.lwjgl.util.vector.Vector3f;

public class Camera extends Entity {
	private Vector3f rotation = new Vector3f(0, 30, 0);

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}
