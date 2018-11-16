package core;

public class GLVelocity {
	private float x = 0;
	private float y = 0;

	public GLVelocity() {

	}

	public GLVelocity(float newX, float newY) {
		this.setVelocity(newX, newY);
	}

	public void setVelocity(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public void addVelocity(float newX, float newY) {
		this.x += newX;
		this.y += newY;
	}

	public boolean hasVelocity() {
		return (x != 0 || y != 0);
	}
}
