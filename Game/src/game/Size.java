package game;

public class Size {
	private int width;
	private int height;

	public Size(int newWidth, int newHeight) {
		this.setWidth(newWidth);
		this.setHeight(newHeight);
	}

	public void setWidth(int newWidth) {
		this.width = newWidth;
	}

	public void setHeight(int newHeight) {
		this.height = newHeight;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
