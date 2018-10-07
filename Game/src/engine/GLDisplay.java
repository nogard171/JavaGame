package engine;

import classes.GLSize;
import static utils.GLHandler.*;

public class GLDisplay {

	private GLSize size;
	private boolean isResizable = true;
	private int targetFPS = 60;

	public GLDisplay(int newWidth, int newHeight) {
		this.size = new GLSize(newWidth, newHeight);
	}

	public void Create() {
		CreateDisplay(this);

		SetupDisplayViewport(this);
	}

	public void Setup() {

	}

	public void Update() {
		if (DisplayResized()) {
			this.ResizeDisplay();
		}

		UpdateDisplay(this);
		ClearScreen();
	}

	public void Destroy() {

	}

	private void ResizeDisplay() {

	}

	public boolean GetResizable() {
		return this.isResizable;
	}

	public float GetWidth() {
		return this.size.GetWidth();
	}

	public float GetHeight() {
		return this.size.GetHeight();
	}

	public boolean IsClosed() {
		return IsDisplayClosed();
	}

	public int GetTargetFPS() {
		return targetFPS;
	}

	public void SetHeight(float newHeight) {
		this.size.SetHeight(newHeight);
	}

	public void SetWidth(float newWidth) {
		this.size.SetWidth(newWidth);

	}
}
