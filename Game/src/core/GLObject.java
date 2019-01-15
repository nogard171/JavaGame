package core;

import java.awt.Polygon;

import org.lwjgl.util.vector.Vector2f;

public class GLObject {
	private GLType type = GLType.BLANK;
	public Polygon bounds = new Polygon();
	private boolean isVisible = false;

	public GLObject(GLType newType) {
		this.type = newType;
	}

	public GLType getType() {
		return this.type;
	}

	public void setType(GLType newType) {
		this.type = newType;
	}

	public boolean visible() {
		// TODO Auto-generated method stub
		return isVisible;
	}

	public void setVisible(boolean b) {
		this.isVisible = b;
		
	}
}
