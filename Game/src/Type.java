import org.lwjgl.util.vector.Vector2f;

public enum Type
{
	GRASS, DIRT, SAND, BLANK, WATER;
	public static Vector2f[] getTextureCoords(Type type)
	{
		Vector2f[] vec =
		{ new Vector2f(0f, 0), new Vector2f(0f, 0.25f), new Vector2f(0.25f, 0), new Vector2f(0.25f, 0.25f) };

		switch (type)
		{
			case GRASS:
				vec = new Vector2f[]{ new Vector2f(0.25f, 0), new Vector2f(0.25f, 0.25f), new Vector2f(0.5f, 0), new Vector2f(0.5f, 0.25f) };
			break;
			case DIRT:
				vec = new Vector2f[]{ new Vector2f(0.5f, 0), new Vector2f(0.5f, 0.25f), new Vector2f(0.75f, 0), new Vector2f(0.75f, 0.25f) };
			break;
			case SAND:
				vec = new Vector2f[]{ new Vector2f(0.75f, 0), new Vector2f(0.75f, 0.25f), new Vector2f(1f, 0), new Vector2f(1f, 0.25f) };
			break;
		}
		return vec;
	}
}
