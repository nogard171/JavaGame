package Engine;

public class GLMaterial extends GLComponent {
	private int textureID = -1;
	private GLSize textureSize = new GLSize(32, 32);
	private GLColor color = new GLColor(255, 255, 255);

	public GLSize getTextureSize() {
		return textureSize;
	}

	public void setTextureSize(GLSize textureSize) {
		this.textureSize = textureSize;
	}

	public GLMaterial() {
		this.setName("material");
	}

	public GLColor getColorAsFloats() {
		return new GLColor(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255);
	}

	public void setColor(GLColor glColor) {
		this.color = glColor;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
}
