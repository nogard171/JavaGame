package Engine;

public class GLSize {
	private int width = 0;
	private int height = 0;

	public GLSize(int w, int h) {
		this.setSize(w, h);
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
