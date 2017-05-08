
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

	protected int textureID = -1;
	float size = 1;
	float degress = 95;
	float startDegress = 85;
	int chunk_size = 32;
	@Override
	public void Setup()
	{
		
		for (int x = 0; x < chunk_size; x++)
		{
			for (int z = 0; z < chunk_size; z++)
			{
				for (int y = 0; y < chunk_size; y++)
				{
					Voxel voxel = new Loader().loadVoxel("resources/voxel/voxel.obj",
							new Vector3f(x * 1.01F, y * 1.01F, z * 1.01F));
					voxels.add(voxel);
					//voxels.put(x+","+y+","+z, voxel);
				}
			}
		}

	}

	Voxel cube = null;
	ArrayList<Voxel> voxels = new ArrayList<Voxel>();
	//HashMap<String,Voxel> voxels = new HashMap<String,Voxel>();
	ArrayList<Voxel> displayedVoxels = new ArrayList<Voxel>();
	

	@Override
	public void Render()
	{
		super.Render();
		for (Voxel voxel : voxels)
		{
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glCallList(voxel.getDlID());
			GL11.glEnd();
		}

	}

	float viewDistance = 500;

	@Override
	public void Update(double delta)
	{
		super.Update(delta);

		if (keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			System.exit(0);
		}
		
	}

	public double distanceToDouble(Vector3f point, Vector3f secoundPoint)
	{
		return Math.sqrt(Math.pow(point.getX() - secoundPoint.getX(), 2)
				+ Math.pow(point.getY() - secoundPoint.getY(), 2) + Math.pow(point.getZ() - secoundPoint.getZ(), 2));
	}

	public double distanceTo(Vector3f point, Vector3f secoundPoint)
	{
		return Math.sqrt(Math.pow(secoundPoint.getX() - point.getY(), 2)
				+ Math.pow(secoundPoint.getY() - point.getY(), 2) + Math.pow(secoundPoint.getZ() - point.getZ(), 2));
	}
}