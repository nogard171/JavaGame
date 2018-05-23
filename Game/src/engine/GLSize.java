package engine;

public class GLSize {
	public float width = 0;
	public float height = 0;

	public GLSize(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}
}
