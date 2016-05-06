import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Tile extends Sprite {
	Texture texture = null;
	public int x = 0;
	public int y = 0;
	private boolean border;

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
			GL11.glColor3f(1, 1, 1);
			if (texture != null) {

				GL11.glEnable(GL11.GL_TEXTURE_2D);
				texture.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(this.x, this.y);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(this.x + this.width, this.y);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(this.x + this.width, this.y + this.height);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(this.x, this.y + this.height);
				GL11.glEnd();
			} else {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.x, this.y);
				GL11.glVertex2f(this.x + this.width, this.y);
				GL11.glVertex2f(this.x + this.width, this.y + this.height);
				GL11.glVertex2f(this.x, this.y + this.height);
				GL11.glEnd();
			}

			if (border) {

				GL11.glColor3f(0, 0, 0);
				GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glVertex2f(this.x, this.y);
				GL11.glVertex2f(this.x + this.width, this.y);
				GL11.glVertex2f(this.x + this.width, this.y + this.height);
				GL11.glVertex2f(this.x, this.y + this.height);
				GL11.glEnd();
			}
		
	}
}
