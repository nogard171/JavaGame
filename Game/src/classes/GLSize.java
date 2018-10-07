package classes;

public class GLSize {
	private float width = 0;
	private float height = 0;

	public GLSize() {

	}

	public GLSize(float newWidth, float newHeight) {
		this.SetSize(newWidth, newHeight);
	}

	public void SetSize(float newWidth, float newHeight) {
		this.SetWidth(newWidth);
		this.SetHeight(newHeight);
	}

	public void SetWidth(float newWidth) {
		this.width = newWidth;
	}

	public void SetHeight(float newHeight) {
		this.height = newHeight;
	}

	public float GetWidth() {
		return this.width;
	}

	public float GetHeight() {
		return this.height;
	}
}
