package engine;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLMaterial extends GLComponent {
	private int textureID = -1;
	private GLSize frameSize = new GLSize(32, 32);

	private GLSize textureSize = new GLSize(32, 32);
	private GLColor color = new GLColor(255, 255, 255);

	public GLMaterial(String textureFile) {
		this.setName("material");
		this.setTexture(textureFile);
	}

	public void setTexture(String filename) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setTextureID(texture.getTextureID());
		this.setTextureSize(new GLSize((int) texture.getImageWidth(), (int) texture.getImageHeight()));
	}

	public GLSize getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(GLSize frameSize) {
		this.frameSize = frameSize;
	}

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
