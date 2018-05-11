package engine;

import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import classes.GLPosition;
import classes.GLSize;
import classes.GLVelocity;

public class GLCamera {
	boolean changed = true;
	public GLPosition position = new GLPosition(0, 0);
	public GLSize size = new GLSize(100, 100);

	public GLCamera(int width, int height) {
		this.size.width = width;
		this.size.height = height;
	}

	public boolean containsVector(Vector2f vec) {
		boolean contains = false;

		return contains;
	}

	public void Move(GLVelocity glVelocity) {
		this.position.x += glVelocity.xVelocity;
		this.position.y += glVelocity.yVelocity;
		if (glVelocity.xVelocity != 0 || glVelocity.yVelocity != 0) {
			changed = true;
		} else {
			changed = false;
		}
	}
}
