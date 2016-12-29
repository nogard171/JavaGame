import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class Cube
{
	private int displayListHandle = -1;
	public Color color = new Color(44, 176, 55);
	public Color bottomColor = new Color(97, 63, 16);

	int[] facesToRender =
	{ 1, 1, 1, 1, 1, 1 };

	public Cube(Color newColor, Color newBottomColor)
	{
		this.color = newColor;
		this.bottomColor = newBottomColor;
	}

	public void Render()
	{
		if (displayListHandle >= 0)
		{
			GL11.glCallList(displayListHandle);
		} else
		{
			displayListHandle = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(displayListHandle, GL11.GL_COMPILE);

			RenderCube(1);

			// End the recording of the current display list.
			GL11.glEndList();
		}
	}

	public void RenderCube(float size)
	{
		final float HALF = size / 2;

		GL11.glBegin(GL11.GL_QUADS);

		// top
		if (facesToRender[0] == 1)
		{

			GL11.glColor3f(((float) color.getRed() / 255), ((float) color.getGreen() / 255),
					((float) color.getBlue() / 255));

			GL11.glVertex3f(-HALF, size - HALF, -HALF);
			GL11.glVertex3f(-HALF, size - HALF, size - HALF);
			GL11.glVertex3f(size - HALF, size - HALF, size - HALF);
			GL11.glVertex3f(size - HALF, size - HALF, -HALF);
		}
		// east
		if (facesToRender[1] == 1)
		{
			GL11.glColor3f((float) bottomColor.getRed() / 255, (float) bottomColor.getGreen() / 255,
					(float) bottomColor.getBlue() / 255);
			GL11.glVertex3f(-HALF, -HALF, -HALF);
			GL11.glVertex3f(-HALF, size - HALF, -HALF);
			GL11.glVertex3f(size - HALF, size - HALF, -HALF);
			GL11.glVertex3f(size - HALF, -HALF, -HALF);
		}

		// west
		if (facesToRender[2] == 1)
		{
			GL11.glColor3f((float) bottomColor.getRed() / 255, (float) bottomColor.getGreen() / 255,
					(float) bottomColor.getBlue() / 255);

			GL11.glVertex3f(-HALF, -HALF, size - HALF);
			GL11.glVertex3f(size - HALF, -HALF, size - HALF);
			GL11.glVertex3f(size - HALF, size - HALF, size - HALF);
			GL11.glVertex3f(-HALF, size - HALF, size - HALF);
		}
		// bottom
		if (facesToRender[3] == 1)
		{
			Float bottomColorOffset = 0.1f;
			GL11.glColor3f(((float) bottomColor.getRed() / 255) - bottomColorOffset,
					((float) bottomColor.getGreen() / 255) - bottomColorOffset,
					((float) bottomColor.getBlue() / 255) - bottomColorOffset);

			GL11.glVertex3f(-HALF, -HALF, -HALF);
			GL11.glVertex3f(size - HALF, -HALF, -HALF);
			GL11.glVertex3f(size - HALF, -HALF, size - HALF);
			GL11.glVertex3f(-HALF, -HALF, size - HALF);
		}
		// south
		if (facesToRender[4] == 1)
		{
			GL11.glColor3f((float) bottomColor.getRed() / 255, (float) bottomColor.getGreen() / 255,
					(float) bottomColor.getBlue() / 255);

			GL11.glVertex3f(-HALF, -HALF, -HALF);
			GL11.glVertex3f(-HALF, -HALF, size - HALF);
			GL11.glVertex3f(-HALF, size - HALF, size - HALF);
			GL11.glVertex3f(-HALF, size - HALF, -HALF);
		}
		// north
		if (facesToRender[5] == 1)
		{
			GL11.glColor3f((float) bottomColor.getRed() / 255, (float) bottomColor.getGreen() / 255,
					(float) bottomColor.getBlue() / 255);

			GL11.glVertex3f(size - HALF, -HALF, -HALF);
			GL11.glVertex3f(size - HALF, size - HALF, -HALF);
			GL11.glVertex3f(size - HALF, size - HALF, size - HALF);
			GL11.glVertex3f(size - HALF, -HALF, size - HALF);
		}
		GL11.glEnd();
	}
}
