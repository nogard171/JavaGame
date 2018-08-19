package classes;

public class GLBound {
	public GLPosition position;
	public GLSize size;

	public GLBound(float x, float y, float newWidth, float newHeight) {
		this.position = new GLPosition(x, y);
		this.size = new GLSize(newWidth, newHeight);
	}

	public GLBound() {
	}

	public boolean containsPosition(GLPosition otherPosition) {
		boolean contains = false;

		if (otherPosition.getX() >= this.position.getX()
				&& otherPosition.getX() <= this.position.getX() + this.size.getWidth()) {
			if (otherPosition.getY() >= this.position.getY()
					&& otherPosition.getY() <= this.position.getY() + this.size.getHeight()) {
				contains = true;
			}
		}

		return contains;
	}

	public boolean intersectsBound(GLBound otherBound) {
		boolean contains = false;

		if (otherBound.position.getX() >= this.position.getX()
				&& otherBound.position.getX() <= this.position.getX() + this.size.getWidth()
				|| otherBound.position.getX() + otherBound.size.getWidth() >= this.position.getX()
						&& otherBound.position.getX() + otherBound.size.getWidth() <= this.position.getX()
								+ this.size.getWidth()) {
			if (otherBound.position.getY() >= this.position.getY()
					&& otherBound.position.getY() <= this.position.getY() + this.size.getHeight()
					|| otherBound.position.getY() + otherBound.size.getHeight() >= this.position.getY()
							&& otherBound.position.getY() + otherBound.size.getHeight() <= this.position.getY()
									+ this.size.getHeight()) {
				contains = true;
			}
		}

		return contains;
	}
}
