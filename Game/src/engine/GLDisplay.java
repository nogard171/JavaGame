package engine;

import classes.GLSize;
import static utils.GLHandler.*;

public class GLDisplay {

	private GLSize size;
	private boolean isResizable = true;
	private int targetFPS = -1;

	public GLDisplay(int newWidth, int newHeight) {
		this.size = new GLSize(newWidth, newHeight);
	}

	public void Create() {
		CreateDisplay(this);

		SetupDisplayViewport(this);

		SetViewportBackgroundColor();
	}

	public void Setup() {

	}
	
	public void SetTitle(String newTitle)
	{
		SetDisplayTitle(newTitle);
	}

	public void Update() {
		if (DisplayResized()) {
			ResizeDisplay(this);
		}

		UpdateDisplay(this);
		ClearScreen();
	}

	public void Destroy() {

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
