
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

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
	protected int textureID = -1;
	@Override
	public void Setup()
	{
		try {
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/textures/tileset.PNG"));
			// load texture from PNG file
			textureID = texture.getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cube = new Loader().generateCube("test");
	}

	
	Cube cube = null;

	@Override
	public void Render()
	{
		super.Render();
		if (cube!=null)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			GL11.glBegin(GL11.GL_TRIANGLES);
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