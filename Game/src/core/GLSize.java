package core;

public class GLSize {
	private int width = 0;
	private int height = 0;

	public GLSize() {

	}

	public GLSize(int newWidth, int newHeight) {
		this.setSize(newWidth, newHeight);
	}

	public void setSize(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
