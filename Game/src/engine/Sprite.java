package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class Sprite
{
	private int Width = 32;
	private int Height = 32;
	private int Texture = -1;
	private Color[] colors =
	{ new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255) };

	public void Render()
	{
		if (Texture != -1)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Texture);
		}

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(colors[0].getRed() / 255, colors[0].getGreen() / 255, colors[0].getBlue() / 255);
		GL11.glVertex2i(0, 0);

		GL11.glVertex2i(this.getWidth(), 0);

		GL11.glVertex2i(this.getWidth(), this.getHeight());

		GL11.glVertex2i(0, this.getHeight());

		GL11.glEnd();
	}

	public int getHeight()
	{
		return Height;
	}

	public void setHeight(int height)
	{
		Height = height;
	}

	public int getTexture()
	{
		return Texture;
	}

	public void setTexture(int texture)
	{
		Texture = texture;
	}

	public int getWidth()
	{
		return Width;
	}

	public void setWidth(int width)
	{
		Width = width;
	}
}
