import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Loader
{
	public static GLRawQuad loadRaw(String file)
	{
		GLRawQuad raw = new GLRawQuad();

		ArrayList<Vector2f> newVectors = new ArrayList<Vector2f>();
		ArrayList<Byte> newIndices = new ArrayList<Byte>();
		ArrayList<Color> newVectorColors = new ArrayList<Color>();
		ArrayList<Vector2f> newVectorTetxures = new ArrayList<Vector2f>();
		String texture = "";
		ArrayList<Integer> boundIndices = new ArrayList<Integer>();
		ArrayList<Byte> newBones = new ArrayList<Byte>();

		Random ran = new Random();

		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			for (String line; (line = br.readLine()) != null;)
			{

				String[] breakDown = line.split(" ");
				String descripter = breakDown[0];
				if (descripter.startsWith("//"))
				{

				} else if (descripter.equals("v"))
				{
					float x = Float.parseFloat(breakDown[1]);
					float y = Float.parseFloat(breakDown[2]);
					newVectors.add(new Vector2f(x, y));
				} else if (descripter.equals("i"))
				{
					newIndices.add(Byte.parseByte(breakDown[1]));
					newIndices.add(Byte.parseByte(breakDown[2]));
					newIndices.add(Byte.parseByte(breakDown[3]));
				} else if (descripter.equals("ic"))
				{
					float r = Float.parseFloat(breakDown[1]);
					float g = Float.parseFloat(breakDown[2]);
					float b = Float.parseFloat(breakDown[3]);

					newVectorColors.add(new Color(r, g, b));
				} else if (descripter.equals("vt"))
				{
					newVectorTetxures.add(new Vector2f(Float.parseFloat(breakDown[1]), Float.parseFloat(breakDown[2])));
				} else if (descripter.equals("materal"))
				{
					texture = breakDown[1];
				}else if (descripter.equals("b"))
				{
					newBones.add(Byte.parseByte(breakDown[1]));
				}
				else if (descripter.equals("bounds"))
				{
					int boundsCount = breakDown.length;
					for(int b=1;b<boundsCount;b++)
					{
						boundIndices.add(Integer.parseInt(breakDown[b]));
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		raw.textureLocation = texture;
		raw.vectors = new Vector2f[newVectors.size()];
		raw.indices = new byte[newIndices.size()];
		raw.indiceColor = new Color[newVectorColors.size()];
		raw.vectorTextures = new Vector2f[newVectorTetxures.size()];
		raw.bones = new byte[newBones.size()];

		for (int v = 0; v < raw.vectors.length; v++)
		{
			raw.vectors[v] = newVectors.get(v);
		}

		for (int i = 0; i < raw.indices.length; i++)
		{
			raw.indices[i] = newIndices.get(i);
		}

		for (int i = 0; i < raw.bones.length; i++)
		{
			raw.bones[i] = newBones.get(i);
		}

		for (int ic = 0; ic < raw.indiceColor.length; ic++)
		{
			raw.indiceColor[ic] = newVectorColors.get(ic);
		}

		for (int it = 0; it < raw.vectorTextures.length; it++)
		{
			raw.vectorTextures[it] = newVectorTetxures.get(it);
		}
		
		Polygon newBounds = new Polygon();
		
		for(int b = 0;b<boundIndices.size();b++)
		{
			Vector2f vec = newVectors.get((int)boundIndices.get(b));
			newBounds.addPoint((int)vec.x,(int)vec.y);
		}
		
		raw.bounds =newBounds;
		
		return raw;
	}
	
	public static GLQuad loadGLQuadFromFile(String file)
	{
		
		ArrayList<Vector2f> newVectors = new ArrayList<Vector2f>();
		ArrayList<Byte> newIndices = new ArrayList<Byte>();
		ArrayList<Color> newVectorColors = new ArrayList<Color>();
		ArrayList<Vector2f> newVectorTetxures = new ArrayList<Vector2f>();
		String rawFile = "";

		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			for (String line; (line = br.readLine()) != null;)
			{

				String[] breakDown = line.split(" ");
				String descripter = breakDown[0];
				if (descripter.startsWith("//"))
				{

				}else if (descripter.equals("vt"))
				{
					newVectorTetxures.add(new Vector2f(Float.parseFloat(breakDown[1]), Float.parseFloat(breakDown[2])));
				} else if (descripter.equals("raw"))
				{
					rawFile = breakDown[1];
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		GLRawQuad raw = loadRaw(rawFile);
		
		raw.vectorTextures = new Vector2f[newVectorTetxures.size()];
		for (int it = 0; it < raw.vectorTextures.length; it++)
		{
			raw.vectorTextures[it] = newVectorTetxures.get(it);
		}
		
		GLQuad gLQuad = generateQuad(raw);
		gLQuad.setBounds(raw.bounds);
		return gLQuad;
	}

	public static GLQuad generateQuad(GLRawQuad raw)
	{
		GLQuad gLQuad = new GLQuad();
		if (raw.textureLocation != "")
		{
			try
			{
				// load texture from PNG file
				gLQuad.setTextureID(TextureLoader
						.getTexture("PNG", ResourceLoader.getResourceAsStream(raw.textureLocation)).getTextureID());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}


		gLQuad.setDisplayID(GL11.glGenLists(1));

		GL11.glNewList(gLQuad.getDisplayID(), GL11.GL_COMPILE);
		int index = 0;
		for (byte indice : raw.indices)
		{

			Vector2f vec = raw.vectors[indice];

			
			if (raw.vectorTextures.length != 0)
			{
				Vector2f vectorTexture = raw.vectorTextures[indice];
				GL11.glTexCoord2f(vectorTexture.x, vectorTexture.y);
			}
			if (raw.indiceColor.length != 0)
			{
				Color vectorColor = raw.indiceColor[(int) (index / 3)];
				GL11.glColor3f(vectorColor.r, vectorColor.g, vectorColor.b);
			}
			GL11.glVertex2i((int) vec.x, (int) vec.y);
			index++;
		}

		GL11.glEndList();
		return gLQuad;
	}
}
