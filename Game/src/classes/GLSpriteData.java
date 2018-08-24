package classes;

public class GLSpriteData {
	public String name = "";
	public String source = "";
	public GLSize size = new GLSize(0, 0);
	public GLPosition texturePosition = new GLPosition(0, 0);
	public GLSize textureSize = new GLSize(0, 0);

	GLPosition[] vectors;
	byte[] indices;
	GLPosition[] textureVectors;
	byte[] textureIndices;

	public GLSpriteData() {
	}

	public GLSpriteData(int width, int height, float textureX, float textureY, float textureWidth,
			float textureHeight) {
		this.size = new GLSize(width, height);

		this.texturePosition = new GLPosition(textureX, textureY);
		this.textureSize = new GLSize(textureWidth, textureHeight);
	}
}
