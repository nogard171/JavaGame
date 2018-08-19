package classes;

public class GLPosition {
	private float x = 0;
	private float y = 0;
	private float z = 0;

	public GLPosition(float f, float newY) {
		this.setPosition(f, newY);
	}

	public GLPosition() {
	}

	public GLPosition(float i, float j, float k) {
		this.setPosition(i, j, k);
	}

	public float getY() {
		return y;
	}

	public void setY(float newY) {
		y = newY;
	}

	public float getX() {
		return x;
	}

	public void setX(float newX) {
		x = newX;
	}

	public void setPosition(float newX, float newY) {
		this.setX(newX);
		this.setY(newY);
	}

	public void setPosition(float newX, float newY, float newZ) {
		this.setPosition(newX, newY);
		this.setZ(newZ);
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void moveX(float amount) {
		this.x += amount;
	}
	public void moveY(float amount) {
		this.y += amount;
	}
	public void moveZ(float amount) {
		this.z += amount;
	}
}
