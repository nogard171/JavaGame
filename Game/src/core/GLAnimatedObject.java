package core;

import java.io.File;
import java.nio.file.FileSystem;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimatedObject extends GLObject {
	private GLFrame[][] frames;

	public GLAnimatedObject(GLType newType) {
		super(newType);
	}
	Texture texture;
	public void loadFrames(String filename) {
		if (new File(filename).exists()) {
			String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
			try {
				texture = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(filename));
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			if (texture != null) {
				GL11.glColor4f(1, 0, 0, 1);
				texture.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(32, 0);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(32, 32);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(0, 32);
				GL11.glEnd();

			} else {

				System.out.println("Error: ");
			}
		} else {
			System.out.println("File does not exist");
		}
	}

	public GLFrame getFrame(int x, int y) {
		return this.frames[x][y];
	}
}
