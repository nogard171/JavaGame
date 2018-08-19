package classes;

public class GLSize {
	private float width = 0;
	private float height = 0;

	public GLSize(float newWidth, float newHeight) {
		this.setSize(newWidth, newHeight);
	}

	public GLSize() {
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setSize(float newWidth, float newHeight) {
		this.setWidth(newWidth);
		this.setHeight(newHeight);
	}
}
