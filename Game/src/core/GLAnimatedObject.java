package core;

import java.io.File;
import java.nio.file.FileSystem;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimatedObject extends GLObject {
	private GLFrame[][] frames;
	private GLSize size = new GLSize(32, 32);

	public GLAnimatedObject(GLType newType) {
		super(newType);
	}

	Texture texture;

	public void loadFrames(String filename, GLSize newSize) {
		this.size = newSize;
		if (new File(filename).exists()) {
			String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
			try {
				texture = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(filename));
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			int xCount = (int) (texture.getTextureWidth() / size.getWidth());
			int yCount = (int) (texture.getTextureHeight() / size.getHeight());

			System.out.println("Test: " + xCount + "," + yCount);
			if (texture != null) {
				GL11.glColor4f(1, 1, 1, 1);
				// texture.bind();
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
				renderFrame(new Vector4f(0, 0, 1, 1), this.size);
			} else {
				System.out.println("Error: ");
			}
		} else {
			System.out.println("File does not exist");
		}

	}

	public void renderFrame(Vector4f tex, GLSize size) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(tex.getX(), tex.getY());
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(tex.getX() + tex.getZ(), tex.getY());
		GL11.glVertex2f(size.getWidth(), 0);
		GL11.glTexCoord2f(tex.getX() + tex.getZ(), tex.getY() + tex.getW());
		GL11.glVertex2f(size.getWidth(), size.getHeight());
		GL11.glTexCoord2f(tex.getX(), tex.getY() + tex.getW());
		GL11.glVertex2f(0, size.getHeight());

		GL11.glEnd();
	}

	public GLFrame getFrame(int x, int y) {
		return this.frames[x][y];
	}
}
