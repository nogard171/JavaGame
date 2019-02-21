package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GLInput {

	GLInputAxis[] inputAxis;
	GLInputAxis[] mouseAxis;

	public GLInput() {
		inputAxis = new GLInputAxis[1];
		inputAxis[0] = new GLInputAxis("left", "A", "LEFT");

		inputAxis = new GLInputAxis[2];
		inputAxis[0] = new GLInputAxis("mouse x", "mx");
		inputAxis[1] = new GLInputAxis("mouse y", "my");
	}

	public float getAxis(String key) {
		float axisValue = 0;
		for (GLInputAxis axis : this.inputAxis) {
			if (axis.getName().toLowerCase() == key.toLowerCase()) {
				axisValue = getRawMouseAxis(axis.getKey());
				break;
			}
		}
		return axisValue;
	}

	private float getRawMouseAxis(String key) {
		float axisValue = 0;
		switch (key.toLowerCase()) {
		case "mx":
			axisValue = Mouse.getX();
			break;
		case "my":
			axisValue = Display.getHeight() - Mouse.getY();
			break;
		case "mdx":
			axisValue = Mouse.getDX();
			break;
		case "mdy":
			axisValue = Mouse.getDY();
			break;
		case "ms":
			axisValue = Mouse.getDWheel();
			break;
		case "mc":
			axisValue = Mouse.getButtonCount();
			break;
		}
		return axisValue;
	}

	public boolean getButton(String key) {
		boolean buttonValue = false;
		for (GLInputAxis axis : this.inputAxis) {
			if (axis.getName().toLowerCase() == key.toLowerCase()) {
				buttonValue = (Keyboard.isKeyDown(Keyboard.getKeyIndex(axis.getKey()))
						|| Keyboard.isKeyDown(Keyboard.getKeyIndex(axis.getAlternativeKey())) ? true : false);
				break;
			}
		}

		return buttonValue;
	}

	public void update() {
		//System.out.println("Mouse: " + this.getAxis("mouse x") + "," + this.getAxis("mouse y"));
	}
}
