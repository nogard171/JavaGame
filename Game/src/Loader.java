import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Loader
{
	int dlid = -1;

	public void loadQuad(Quad quad, String fileName)
	{
		int dlid = GL11.glGenLists(1);
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		preRenderQuad(new RawQuad());
		GL11.glEndList();
		quad.setDlid(dlid);
		try
		{
			Texture texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/" + fileName));
			if (quad.getSize() == null)
			{
				quad.setSize(32, 32);
			}
			// load texture from PNG file
			quad.setTextureID(texture.getTextureID());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Quad getQuadFromRaw(RawQuad raw)
	{
		Quad quad = new Quad();
		int dlid = GL11.glGenLists(1);
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		preRenderQuad(raw);
		GL11.glEndList();
		quad.setDlid(dlid);
		return quad;
	}

	public Quad getQuadFromModel(String modelName)
	{
		RawQuad raw = new RawQuad();
		String textureName = "";
		String line;
		try (InputStream fis = new FileInputStream("resources/models/" + modelName);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);)
		{

			while ((line = br.readLine()) != null)
			{
				String[] data = line.split(" ");
				String type = data[0];
				if (type.startsWith("texture"))
				{
					textureName = data[1];
					break;
				}
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Quad quad = getQuadFromModel(modelName,textureName);
		
		return quad;
	}
	public Quad getQuadFromModel(String modelName, String textureName)
	{
		RawQuad raw = new RawQuad();
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Byte> indices = new ArrayList<Byte>();
		ArrayList<Byte> colorIndices = new ArrayList<Byte>();
		ArrayList<Vector3f> colors = new ArrayList<Vector3f>();
		ArrayList<Byte> textureIndices = new ArrayList<Byte>();
		ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();
		String line;
		try (InputStream fis = new FileInputStream("resources/models/" + modelName);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);)
		{

			while ((line = br.readLine()) != null)
			{
				String[] data = line.split(" ");
				String type = data[0];
				if (type.startsWith("v"))
				{
					vertices.add(new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]),
							Float.parseFloat(data[3])));
				}
				if (type.startsWith("fc"))
				{
					colorIndices.add(Byte.parseByte(data[1]));
					colorIndices.add(Byte.parseByte(data[2]));
					colorIndices.add(Byte.parseByte(data[3]));
				}
				if (type.startsWith("ti"))
				{
					textureIndices.add(Byte.parseByte(data[1]));
					textureIndices.add(Byte.parseByte(data[2]));
					textureIndices.add(Byte.parseByte(data[3]));
				}
				if (type.startsWith("tc"))
				{
					textureCoords.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
				}
				if (type.startsWith("fi"))
				{
					indices.add(Byte.parseByte(data[1]));
					indices.add(Byte.parseByte(data[2]));
					indices.add(Byte.parseByte(data[3]));
				}
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		raw.indices = null;
		System.out.println("Count:"+indices.size());
		raw.indices = new byte[indices.size()];
		for (int i = 0; i < raw.indices.length; i++)
		{
			raw.indices[i] = indices.get(i);
		}

		raw.colorIndices = new byte[colorIndices.size()];
		for (int i = 0; i < raw.colorIndices.length; i++)
		{
			raw.colorIndices[i] = colorIndices.get(i);
		}
		raw.textureIndices = new byte[textureIndices.size()];
		for (int i = 0; i < raw.textureIndices.length; i++)
		{
			raw.textureIndices[i] = textureIndices.get(i);
		}

		raw.vertices = new Vector3f[vertices.size()];
		for (int i = 0; i < raw.vertices.length; i++)
		{
			raw.vertices[i] = vertices.get(i);
		}

		raw.textureCoords = new Vector2f[textureCoords.size()];
		for (int i = 0; i < raw.textureCoords.length; i++)
		{
			raw.textureCoords[i] = textureCoords.get(i);
		}
		Quad quad = getQuadFromRaw(raw);
		
		if (textureName != "")
		{
			try
			{
				Texture texture = TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream("resources/textures/" + textureName));
				if (quad.getSize() == null)
				{
					quad.setSize(32, 32);
				}
				// load texture from PNG file
				quad.setTextureID(texture.getTextureID());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return quad;
	}

	public void preRenderQuad(RawQuad raw)
	{
		byte index = 0;
		glBegin(GL11.GL_TRIANGLES);
		//System.out.println("Count:"+raw.getIndices().length);
		for (byte indice : raw.getIndices())
		{
			Vector3f vec = raw.getVertice(indice);
			Vector2f textureVector = raw.getTextureCoords(raw.getTextureIndices(index));
			GL11.glTexCoord2f(textureVector.getX(), textureVector.getY());

				Vector3f color = raw.getColors()[raw.getColorIndices(index)];
				GL11.glColor3f(color.getX(), color.getY(), color.getZ());
			
			glVertex2f(vec.getX() - vec.getZ(), vec.getY());
			index++;
		}
		glEnd();
	}
}
