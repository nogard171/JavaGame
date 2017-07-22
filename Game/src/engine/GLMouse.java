package engine;

import org.lwjgl.input.Mouse;

public class GLMouse {
	public boolean isMouseDown(int mouseButton) {
		return Mouse.isButtonDown(mouseButton);
	}
	public boolean isMouseUp(int mouseButton) {
		return !Mouse.isButtonDown(mouseButton);
	}
	public int getMouseCount()
	{
		return Mouse.getButtonCount();
	}
	public int getMouseX()
	{
		return Mouse.getX();
	}
	public int getMouseY()
	{
		return Mouse.getY();
	}
	public int getMouseDX()
	{
		return Mouse.getDX();
	}
	public int getMouseDY()
	{
		return Mouse.getDY();
	}
}
