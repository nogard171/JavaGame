
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
	int width = 32;
	int height = 32;
	Color color = new Color(255,255,255);
	int spriteMode = GL11.GL_TRIANGLES;
	public Point origin = new Point(0, 0);
	Texture texture = null;

	Vector2f[] vectors = { new Vector2f(0, 0), new Vector2f(1, 0), new Vector2f(1, 1), new Vector2f(0, 1) };
	int[][] faces = { { 0, 1, 2 }, { 0, 3, 2 } };

	public Sprite(int new_Width, int new_Height) {
		this.setSize(new_Width, new_Height);
	}

	public void setColor(Color newColor) {
		this.color = newColor;

	}

	public Sprite() {
		this.setSize(32, 32);
	}

	public void setSize(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	public void setOrigin(int x, int y) {
		this.origin = new Point(x, y);
	}

	private int displayListHandle = -1;
	boolean render_Update = false;

	public void Render() {
		if (this.displayListHandle < 0 || render_Update) {
			// Generate one (!) display list.
			// The handle is used to identify the
			// list later.
			displayListHandle = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(displayListHandle, GL11.GL_COMPILE);

			// Render a single cube
			PreRender();

			// End the recording of the current display list.
			GL11.glEndList();
		} else {
			GL11.glCallList(displayListHandle);
		}
	}

	public void PreRender() {
		if (texture != null) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		GL11.glColor3f(color.r, color.g, color.b);
		for (int f = 0; f < faces.length; f++) {
			GL11.glBegin(spriteMode);
			for (int fv = 0; fv < faces[f].length; fv++) {
				Vector2f vec = vectors[faces[f][fv]];
				Vector2f tex = null;
				if (texture != null) {
					GL11.glTexCoord2f(vec.x, vec.y);
				}
				GL11.glVertex2f(vec.x * this.width, vec.y * this.height);
			}
			GL11.glEnd();
		}
		if (texture != null) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}
	public void Destroy()
	{
		texture.release();
	}
}
