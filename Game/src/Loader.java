import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Loader
{

	public Voxel loadVoxel(String file, Vector3f position)
	{
		Voxel voxel = new Voxel();
		voxel.setPosition(position);
		ArrayList<Vector3f> vecs = new ArrayList<Vector3f>();
		ArrayList<Byte> indices = new ArrayList<Byte>();
		ArrayList<Vector3f> norms = new ArrayList<Vector3f>();
		ArrayList<Color> colors = new ArrayList<Color>();
		ArrayList<Byte> indiceColors = new ArrayList<Byte>();
		
		String line;
		try (InputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);)
		{
			while ((line = br.readLine()) != null)
			{
				String[] data = line.split(" ");
				String operator = data[0];
				if (line.startsWith("//"))
				{
				} else if (operator.equals("v"))
				{

					vecs.add(new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]),
							Float.parseFloat(data[3])));
				} else if (operator.equals("i"))
				{
					indices.add(Byte.parseByte(data[1]));
					indices.add(Byte.parseByte(data[2]));
					indices.add(Byte.parseByte(data[3]));
				} else if (operator.equals("n"))
				{
					norms.add(new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]),
							Float.parseFloat(data[3])));
				} else if (operator.equals("c"))
				{
					colors.add(
							new Color(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));
				} else if (operator.equals("ic"))
				{
					indiceColors.add(Byte.parseByte(data[1]));
					indiceColors.add(Byte.parseByte(data[2]));
					indiceColors.add(Byte.parseByte(data[3]));
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector3f[] newVecs = new Vector3f[vecs.size()];
		for (int v = 0; v < vecs.size(); v++)
		{
			newVecs[v] = vecs.get(v);
		}

		Vector3f[] newNorms = new Vector3f[norms.size()];
		for (int n = 0; n < norms.size(); n++)
		{
			newNorms[n] = norms.get(n);
		}

		Byte[] newIndices = new Byte[indices.size()];
		for (int i = 0; i < indices.size(); i++)
		{
			newIndices[i] = indices.get(i);
		}
		
		Byte[] newIndiceColors = new Byte[indiceColors.size()];
		for (int i = 0; i < indiceColors.size(); i++)
		{
			newIndiceColors[i] = indiceColors.get(i);
		}
		
		Color[] newColors = new Color[colors.size()];
		for (int i = 0; i < colors.size(); i++)
		{
			newColors[i] = colors.get(i);
		}
		
		int dlID = GL11.glGenLists(1);

		// Start recording the new display list.
		GL11.glNewList(dlID, GL11.GL_COMPILE);
		for (int i = 0; i < newIndices.length; i++)
		{
			Byte indice = newIndices[i];
			Vector3f vec = newVecs[indice];
			Byte indiceColor = newIndiceColors[i];
			Color color = newColors[indiceColor];
			GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
			GL11.glVertex3f(vec.getX()+position.getX(), vec.getY()+position.getY(), vec.getZ()+position.getZ());
		}
		// End the recording of the current display list.
		GL11.glEndList();
		
		voxel.setDlID(dlID);
		
		return voxel;
	}

	public Cube generateCube(String name)
	{
		Cube cube = new Cube();

		int dlID = GL11.glGenLists(1);

		// Start recording the new display list.
		GL11.glNewList(dlID, GL11.GL_COMPILE);

		Render(new RawCube());

		// End the recording of the current display list.
		GL11.glEndList();

		cube.setDlID(dlID);

		return cube;
	}

	public void Render(RawCube raw)
	{
		for (int i = 0; i < raw.getIndices().length; i++)
		{
			int indice = raw.getIndices()[i];
			int colorIndice = raw.getColorIndices()[i];
			int textureIndice = raw.getTextureIndices()[i];
			Vector2f textureCoord = raw.getTextureVectorsByIndice(textureIndice);
			Vector3f vec = raw.getVectorsByIndice(indice);
			Color color = raw.getColorByIndice(colorIndice);
			GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
			GL11.glTexCoord2f(textureCoord.getX(), textureCoord.getY());
			GL11.glVertex3f(vec.getX(), vec.getY(), vec.getZ());
		}
	}
}
