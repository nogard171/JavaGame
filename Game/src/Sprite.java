
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Sprite {
	int width = 32;
	int height = 32;
	int[][] vertex = { { 0, 0 }, { 1, 0 }, { 0, 1, }, { 1, 1 } };
	int[][] faces = { { 1, 2, 3 }, { 2, 3, 4 } };
	Color color = new Color(0, 255, 0);
	int render_Type = GL11.GL_QUADS;
	public Point origin = new Point(0, 0);
	Texture texture = null;

	public Sprite(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	public void setColor(Color newColor) {
		this.color = newColor;

	}

	public Sprite() {
		this.width = 32;
		this.height = 32;
	}

	public void setSize(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	public void setOrigin(int x, int y) {
		this.origin = new Point(x, y);
	}

	public void VertexRender() {
		if (texture != null) {
			texture.bind();
		}
		// background

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0, 0, 0);
		GL11.glVertex2f(-1, 1);
		GL11.glVertex2f(this.width + 1, 1);
		GL11.glVertex2f(this.width + 1, 0 - this.height - 1);
		GL11.glVertex2f(-1, 0 - this.height - 1);
		GL11.glEnd();
		/*
		 * 
		 * GL11.glBegin(render_Type); GL11.glColor3f(color.r, color.g, color.b);
		 * 
		 * for (int v = 0; v < faces.length - 1; v++) {
		 * GL11.glVertex2f(vertex[faces[v][0]][0], vertex[faces[v][0]][1]);
		 * GL11.glVertex2f(vertex[faces[v][1]][0], vertex[faces[v][1]][1]);
		 * GL11.glVertex2f(vertex[faces[v][2]][0], vertex[faces[v][2]][1]); }
		 * 
		 * GL11.glEnd();
		 */

		GL11.glBegin(render_Type);
		GL11.glColor3f(color.r, color.g, color.b);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(this.width, 0);
		GL11.glVertex2f(this.width, 0 - this.height);
		GL11.glVertex2f(0, 0 - this.height);
		GL11.glEnd();

		if (texture != null) {
			texture.release();
		}
	}

	public void Render() {
		// rot++;
		if (texture != null) {
			texture.bind();
		}
		// background
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0, 0, 0);
		GL11.glVertex2f(-1, +1);
		GL11.glVertex2f(this.width + 1, +1);
		GL11.glVertex2f(this.width + 1, 0 - this.height - 1);
		GL11.glVertex2f(-1, 0 - this.height - 1);
		GL11.glEnd();

		GL11.glBegin(render_Type);
		GL11.glColor3f(color.r, color.g, color.b);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(this.width, 0);
		GL11.glVertex2f(this.width, 0 - this.height);
		GL11.glVertex2f(0, 0 - this.height);
		GL11.glEnd();

		if (texture != null) {
			texture.release();
		}
	}
}
