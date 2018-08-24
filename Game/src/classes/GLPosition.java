package classes;

public class GLPosition {
	public float x = 0;
	public float y = 0;

	public GLPosition(float textureX, float textureY) {
		this.x = textureX;
		this.y = textureY;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public void setPosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}
}
