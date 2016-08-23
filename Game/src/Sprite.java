import java.awt.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Sprite {
	int width = 32;
	int height = 32;
	Color color = new Color(0, 255, 0);
	int render_Type = GL11.GL_QUADS;
	Point origin = new Point(0, 0);

	public Sprite(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	public Sprite() {
		this.width = 32;
		this.height = 32;
	}

	public void setSize(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	int rot = 0;

	public void Render(int x, int y) {
		// rot++;

		GL11.glPushMatrix();
		GL11.glTranslatef(x + origin.x, y - origin.y, 0);

		GL11.glRotatef(rot, 0, 0, 1);
		GL11.glTranslatef(-origin.x, origin.y, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0, 0, 0);
		GL11.glVertex2f(-1, -1);
		GL11.glVertex2f(this.width + 1, -1);
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

		GL11.glBegin(render_Type);
		GL11.glColor3f(255, 0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(this.width, 0);
		GL11.glVertex2f(this.width, -8);
		GL11.glVertex2f(0, -8);
		GL11.glEnd();

		GL11.glTranslatef(origin.x, -origin.y, 0);
		GL11.glTranslatef(-x - origin.x, -y + origin.y, 0);
		GL11.glPopMatrix();
	}
}
