package classes;

public class GLPosition {
	private int x = 0;
	private int y = 0;

	
	public GLPosition(int newX, int newY)
	{
		this.setPosition(newX, newY);
	}
	public int getY() {
		return y;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setPosition(int newX, int newY) {
		this.setX(newX);
		this.setY(newY);
	}
}
