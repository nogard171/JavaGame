package classes;

public class GLVelocity {
	public float xVelocity = 0;
	public float yVelocity = 0;

	public GLVelocity(float xSpeed, float ySpeed) {
		this.xVelocity = xSpeed;
		this.yVelocity = ySpeed;
	}

	public float getX() {
		return this.xVelocity;
	}

	public float getY() {
		return this.yVelocity;
	}
}
