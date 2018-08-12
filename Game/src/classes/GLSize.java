package classes;

public class GLSize {
	private int width = 0;
	private int height = 0;

	public GLSize(int newWidth, int newHeight) {
		this.setSize(newWidth, newHeight);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setSize(int newWidth, int newHeight)
	{
		this.setWidth(newWidth);
		this.setHeight(newHeight);
	}
}
