package classes;

public class GLPosition {
	private float x = 0;
	private float y = 0;

	public GLPosition() {
	}

	public GLPosition(float newX, float newY) {
		this.SetPosition(newX, newY);
	}

	public void SetPosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}

	public void SetPosition(GLPosition newPosition) {
		this.SetPosition(newPosition.GetX(), newPosition.GetY());
	}

	public float GetX() {
		return this.x;
	}

	public float GetY() {
		return this.y;
	}
}
