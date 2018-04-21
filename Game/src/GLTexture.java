import org.lwjgl.util.vector.Vector2f;

public class GLTexture {
	int dl = -1;
	String name = "";
	Vector2f textureCoords = new Vector2f(0, 0);
	Vector2f size = new Vector2f(1, 1);

	public GLTexture(String newName, Vector2f newTextureCoords, Vector2f newSize) {
		this.name = newName;
		this.textureCoords = newTextureCoords;
		this.size = newSize;
	}
	public GLTexture(String newName, Vector2f newTextureCoords) {
		this.name = newName;
		this.textureCoords = newTextureCoords;
	}
}
