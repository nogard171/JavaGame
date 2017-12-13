
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
			step = ((float)32/(float)texture.getImageWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(step);
		shader.createProgram();
		
		for(int x=0;x<5;x++)
		{
			for(int y=0;y<5;y++)
			{
				chunks.add(new Chunk(x*16,0,y*16));
			}
		}
	}
	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	@Override
	public void Render()
	{
		super.Render();
		shader.Start();
		shader.sendUniform1f("myTexture", texture.getTextureID());
		for(int i=0;i<chunks.size();i++){
			chunks.get(i).renderChunk(step);
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
		
		if(Mouse.isButtonDown(0))
		{
			System.out.println("test");
		}

	}
}