
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

	State state = State.SPECTATOR;
	
	HashMap<Type, Cube> cubes = new HashMap<Type, Cube>();

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

		// OBJLoader objLoader = new OBJLoader();
		// objLoader.loadOBJ(mod, "Avent");
		// mod.Setup();

		cubes.put(Type.GRASS, new Cube(new Color(44, 176, 55), new Color(97, 63, 16)));
		cubes.put(Type.DIRT, new Cube(new Color(97, 63, 16), new Color(97, 63, 16)));

	}
	Entity entity = new Entity();
	

	@Override
	public void Render()
	{
		super.Render();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(entity.getX(), entity.getY(), entity.getZ());
		cubes.get(entity.getType()).Render();
		GL11.glPopMatrix();
		
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