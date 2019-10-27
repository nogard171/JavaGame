package engine;

public class GLVector {
	private int x = 0;
	private int y = 0;

	public GLVector(int i, int j) {
		setX(i);
		setY(j);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
