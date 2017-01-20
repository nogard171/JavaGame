import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Loader
{
	public static Quad GenerateQuad()
	{
		return GenerateQuadWithTexture(null);
	}

	public static Quad GenerateQuadWithTexture(String textureLocation)
	{
		Texture texture = null;
		Quad quad = new Quad();
		int displayListHandle = quad.getDisplayID();
		// Generate one (!) display list.
		// The handle is used to identify the
		// list later.
		displayListHandle = glGenLists(1);

		// Start recording the new display list.
		glNewList(displayListHandle, GL_COMPILE);

		RenderQuad(quad);

		// End the recording of the current display list.
		glEndList();

		quad.setDisplayID(displayListHandle);
		if (textureLocation != null)
		{
			loadTextureIntoQuad(quad, textureLocation);
		}
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		return quad;
	}

	public static void loadTextureIntoQuad(Quad quad, String textureLocation)
	{
		Texture texture = null;
		try
		{
			// load texture from PNG file
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/" + textureLocation));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		quad.setTextureID(texture.getTextureID());
	}

	private static void RenderQuad(Quad quad)
	{
		RawQuad raw = new RawQuad();
		byte[] indices = raw.getIndices();
		byte[] textureIndices = raw.getTextureIndices();
		for (int i = 0; i < indices.length; i++)
		{
			byte indice = indices[i];
			Vector2f vec = raw.getVector(indice);
			byte textureIndice = textureIndices[i];
			Vector2f tex = raw.getTextureVector(textureIndice);
			GL11.glTexCoord2f(tex.getX(), tex.getY());
			GL11.glVertex2f(vec.getX() * quad.getWidth(), vec.getY() * quad.getHeight());
		}
	}
}
