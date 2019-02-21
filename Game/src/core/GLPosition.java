package core;

public class GLPosition {
	private float x = 0;
	private float y = 0;

	public GLPosition() {

	}

	public GLPosition(float newX, float newY) {
		this.setPosition(newX, newY);
	}

	public void setPosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}
}
