package core;

import org.lwjgl.util.vector.Vector3f;

public class Entity extends Object {
	private Vector3f position = new Vector3f(0, 0, 0);
	private String materialName = "";

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public String getMaterialName() {
		// TODO Auto-generated method stub
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}
