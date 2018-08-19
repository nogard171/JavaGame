package classes;

public class GLTextureData {
	public GLPosition position = new GLPosition(0, 0);

	public GLSize size = new GLSize(1f, 1f);

	public GLTextureData() {

	}

	public GLTextureData(float newX, float newY, float newWidth, float newHeight) {
		this.position.setPosition(newX, newY);
		this.size.setSize(newWidth, newHeight);
	}

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
