import org.lwjgl.util.vector.Vector2f;

public class Material {
	Type type = Type.BLANK;
	Vector2f[] vec = { new Vector2f(0, 0), new Vector2f(0, 1), new Vector2f(0, 1), new Vector2f(0, 1),
			new Vector2f(0, 1), new Vector2f(0, 1)

	};

	public Material(Type newType) {
		this.type = newType;
	}
}
