package engine;

public class GLRectangle {
	float X = 0;
	float Y = 0;
	float Width = 0;
	float Height = 0;

	public GLRectangle() {

	}

	public GLRectangle(float newX, float newY, float newWidth, float newHeight) {
		this.setX(newX);
		this.setY(newY);
		this.setWidth(newWidth);
		this.setHeight(newHeight);
	}

	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}

	public float getWidth() {
		return Width;
	}

	public void setWidth(float width) {
		Width = width;
	}

	public float getHeight() {
		return Height;
	}

	public void setHeight(float height) {
		Height = height;
	}

	public boolean inside(GLRectangle rect) {
		boolean inside = false;
		if (this.getX() < rect.getX() && this.getX() + this.getWidth() > rect.getX() && this.getY() < rect.getY()
				&& this.getY() + this.getHeight() > rect.getY()) {
			inside = true;
		}
		return inside;
	}
}
