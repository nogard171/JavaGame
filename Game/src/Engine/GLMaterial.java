package Engine;

public class GLMaterial extends GLComponent {
	private GLColor color = new GLColor(255, 255, 255);

	public GLMaterial() {
		this.setName("material");
	}

	public GLColor getColorAsFloats() {
		return new GLColor(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255);
	}

	public void setColor(GLColor glColor) {
		this.color = glColor;
	}
}
