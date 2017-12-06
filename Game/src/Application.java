
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
import org.lwjgl.util.vector.Vector2f;
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
	Texture texture;
	ShaderProgram shader = new ShaderProgram();
	float step = 0;
	@Override
	public void Setup()
	{
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/textures/tileset.jpg"));
			step = (float)32/(float)texture.getImageWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(step);
		shader.createProgram();
	}
	Chunk chunk = new Chunk();
	@Override
	public void Render()
	{
		super.Render();
		shader.Start();
		shader.sendUniform1f("myTexture", texture.getTextureID());
		
		chunk.renderChunk(step);
		
		
		
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