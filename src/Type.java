import org.lwjgl.util.vector.Vector2f;

public enum Type {
	BLANK(null), AIR(null), GRASS(new Vector2f[] { new Vector2f(0, 0), new Vector2f(0, 1), new Vector2f(0, 1),
			new Vector2f(0, 1), new Vector2f(0, 1), new Vector2f(0, 1)

	}), DIRT(new Vector2f[] { new Vector2f(0, 1), new Vector2f(0, 1), new Vector2f(0, 1), new Vector2f(0, 1),
			new Vector2f(0, 1), new Vector2f(0, 1)

	});
	// declaring private variable for getting values
	private Vector2f[] vec;

	// getter method
	public Vector2f[] getVec() {
		return this.vec;
	}

	// enum constructor - cannot be public or protected
	private Type(Vector2f[] newVec) {
		this.vec = newVec;
	}
}
