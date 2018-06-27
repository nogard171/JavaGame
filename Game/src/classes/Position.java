package classes;

public class Position {
	int X = 0;
	int Y = 0;

	public Position(int newX, int newY) {
		this.setX(newX);
		this.setY(newY);
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	public void setX(int newX) {
		this.X = newX;
	}

	public void setY(int newY) {
		this.Y = newY;
	}
}
