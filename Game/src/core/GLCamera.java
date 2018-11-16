package core;

import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GLCamera {
	boolean changed = true;
	public GLPosition position = new GLPosition(0, 0);
	public GLSize size = new GLSize(100, 100);

	public GLCamera(int width, int height) {
		this.size.setSize(width, height);
	}

	public boolean containsVector(Vector2f vec) {
		boolean contains = false;

		return contains;
	}

	public void Move(GLVelocity velocity) {
		this.position.translate(velocity.getX(), velocity.getY());
		if (velocity.hasVelocity()) {
			changed = true;
		} else {
			changed = false;
		}
	}
}
