package classes;

public class GLSize {
	public float width = 0;
	public float height = 0;
	public float depth = 0;

	public GLSize(float newWidth, float newHeight) {
		this.setSize(newWidth, newHeight);
	}

	public GLSize(float newWidth, float newHeight, float newDepth) {
		this.setSize(newWidth, newHeight, newDepth);
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public void setSize(float newWidth, float newHeight) {
		this.width = newWidth;
		this.height = newHeight;
	}

	public void setSize(float newWidth, float newHeight, float newDepth) {
		this.width = newWidth;
		this.height = newHeight;
		this.depth = newDepth;
	}
}
