package core;

public class GLVelocity {
	private float xVelocity = 0;
	private float yVelocity = 0;

	public GLVelocity() {

	}

	public GLVelocity(float newX, float newY) {
		this.setVelocity(newX, newY);
	}

	public void setVelocity(float newX, float newY) {
		this.xVelocity = newX;
		this.yVelocity = newY;
	}

	public float getXVelocity() {
		return this.xVelocity;
	}

	public float getYVelocity() {
		return this.yVelocity;
	}

	public void addVelocity(float newXVelocity, float newYVelocity) {
		this.xVelocity += newXVelocity;
		this.yVelocity += newYVelocity;
	}

	public boolean hasVelocity() {
		return (xVelocity != 0 || yVelocity != 0);
	}
}
