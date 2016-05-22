
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import Objects.ObjectType;

public class Tile extends Sprite {
	Texture texture = null;
	public int x = 0;
	public int y = 0;
	private boolean border;
	public ObjectType type = ObjectType.OTHER;
	public ObjectType topType = ObjectType.OTHER;

	public Tile(int x2, int y2, int width, int height) {
		this.x = x2;
		this.y = y2;
		this.width = width;
		this.height = height;
	}

	public int index = -1;

	public Tile(int size, int x2, int y2, int width, int height) {
		// TODO Auto-generated constructor stub
		index = size;
		this.x = x2;
		this.y = y2;
		this.width = width;
		this.height = height;
	}

	public void Render() {
		if (texture != null) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 1, 1);
			texture.bind();

		} else {
			GL11.glColor3f(1, 0, 0);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(this.x, this.y, 0);

		GL11.glBegin(GL11.GL_QUADS);
		if (texture != null) {

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(width, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, height);
		} else {
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(width, 0);
			GL11.glVertex2f(width, height);
			GL11.glVertex2f(0, height);
		}
		GL11.glEnd();
		if (topTexture != null) {
			GL11.glColor3f(1, 1, 1);
			topTexture.bind();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(128, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(128,128);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, 128);
			GL11.glEnd();
		}
		if (isSolid) {
			GL11.glColor3f(1, 0, 0);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(width, 0);
			GL11.glVertex2f(width, height);
			GL11.glVertex2f(0, height);
			GL11.glEnd();
		}

		GL11.glTranslatef(-this.x, -this.y, 0);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	boolean isSolid = false;
}
