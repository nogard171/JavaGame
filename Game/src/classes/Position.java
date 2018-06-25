package classes;

public class Position {
	float X = 0;
	float Y = 0;

	public Position(float newX, float newY) {
		this.setX(newX);
		this.setY(newY);
	}

	public float getX() {
		return this.X;
	}

	public float getY() {
		return this.Y;
	}

	public void setX(float newX) {
		this.X = newX;
	}

	public void setY(float newY) {
		this.Y = newY;
	}
}
