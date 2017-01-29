
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Application extends GLWindow
{
	// the grid boolean, used for render things as lines.

	public static void main(final String[] argv)
	{
		final Application app = new Application();
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				app.CreateWindow();
			}
		});

	}

	@Override
	public void Setup()
	{
		cube = new Loader().generateCube("test");
	}

	Cube cube = null;

	@Override
	public void Render()
	{
		super.Render();
		if (cube!=null)
		{
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, cube.getTextureID());
			GL11.glCallList(cube.getDlID());
			GL11.glEnd();
		}
	}

	@Override
	public void Update(double delta)
	{
		super.Update(delta);

		if (keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			System.exit(0);
		}

	}
}