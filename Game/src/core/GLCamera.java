package core;

import java.awt.Rectangle;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GLCamera extends GLEntity {
	public GLSize size = new GLSize(100, 100);

	public GLCamera() {
		
	}
	public void update()
	{
		this.size = new GLSize(Display.getWidth(),Display.getHeight());
	}
}
