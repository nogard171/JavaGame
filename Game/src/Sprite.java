
import java.awt.color.ColorSpace;
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
	Color color = new Color(0, 255, 0);
	int render_Type = GL11.GL_QUADS;
	public Point origin = new Point(0, 0);
	Texture texture = null;
	SpriteData spriteData = null;
	Type type = Type.GRASS;
	Vector2f[] vertex = null;
	int[][] faces = null;
	Color[] colors = null;
	int[] topFaces = { 0, 1, 2, 5 };

	public Sprite(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
		// this.getSpriteData();
	}

	public void setColor(Color newColor) {
		if (colors != null && colors[0] != newColor && colors[1] != newColor) {
			colors[0] = newColor;
			colors[1] = newColor;
		}
	}

	public Color getColor() {
		this.resetSprite();
		if (spriteData != null) {
			return spriteData.colors[0];
		} else {
			return new Color(0, 255, 0);
		}
	}

	public void resetSprite() {
		if (spriteData == null || spriteData != Type.getSpriteData(this.type)) {
			this.spriteData = Type.getSpriteData(this.type);
		}
		if (this.faces != spriteData.faces) {
			this.faces = spriteData.faces;
		}
		if (this.vertex != spriteData.vertex) {
			this.vertex = spriteData.vertex;
		}
		if (this.colors != spriteData.colors) {
			this.colors = spriteData.colors;
		}
	}

	public Sprite() {
		this.width = 32;
		this.height = 32;
		// this.getSpriteData();
	}

	public void setTransparent(float amount) {
		if (colors != null) {
			for (int c = 0; c < colors.length; c++) {
				colors[c] = new Color(colors[c].r, colors[c].g, colors[c].b, amount);
				System.out.println("Color");
			}
		}
	}

	public void setSize(int new_Width, int new_Height) {
		this.width = new_Width;
		this.height = new_Height;
	}

	public void setOrigin(int x, int y) {
		this.origin = new Point(x, y);
	}

	public void RenderTop() {
		Render(true);
	}
	public void RenderBottom() {
		Render(false);
	}

	public void Render(boolean top) {
		if (vertex == null) {
			this.resetSprite();
		}
		if (texture != null) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			texture.bind();
		}

		GL11.glBegin(GL11.GL_TRIANGLES);
		if (texture != null) {
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(this.width, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(this.width, 0 - this.height);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, 0 - this.height);
		} else {
			for (int f1 = 0; f1 < faces.length; f1++) {
				if (hasValue(topFaces, f1) && top) {
					GL11.glColor4f(colors[f1].r, colors[f1].g, colors[f1].b, colors[f1].a);
					for (int f2 = 0; f2 < faces[f1].length; f2++) {
						GL11.glVertex2f(vertex[faces[f1][f2]].x * this.width, vertex[faces[f1][f2]].y * this.height);
					}
				} else if (!hasValue(topFaces, f1) &&!top) {
					GL11.glColor4f(colors[f1].r, colors[f1].g, colors[f1].b, colors[f1].a);
					for (int f2 = 0; f2 < faces[f1].length; f2++) {
						GL11.glVertex2f(vertex[faces[f1][f2]].x * this.width, vertex[faces[f1][f2]].y * this.height);
					}
				}
			}
		}

		GL11.glEnd();

		if (texture != null) {
			// texture.release();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	public boolean hasValue(int[] array, int value) {
		boolean has = false;
		for (int f1 = 0; f1 < array.length; f1++) {
			if (array[f1] == value) {
				has = true;
				break;
			}
		}
		return has;
	}
}
