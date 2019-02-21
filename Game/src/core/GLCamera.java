package core;

import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GLCamera {
	public GLPosition position = new GLPosition(0, 0);
	public GLSize size = new GLSize(100, 100);

	public GLCamera(int width, int height) {
		this.size.setSize(width, height);
	}

}
