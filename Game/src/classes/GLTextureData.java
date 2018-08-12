package classes;

public class GLTextureData {
	private GLPosition position = new GLPosition(0, 0);

	private GLSize size = new GLSize(0, 0);

	public float getX() {
		return this.position.getX();
	}

	public float getY() {
		return this.position.getY();
	}

	public float getWidth() {
		return this.size.getWidth();
	}

	public float getHeight() {
		return this.size.getHeight();
	}
}
