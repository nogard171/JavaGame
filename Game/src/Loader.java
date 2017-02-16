import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Loader
{
	public static Quad loadQuad()
	{
		Quad quad = new Quad();

		int dlid = GL11.glGenLists(1);

		GL11.glNewList(dlid, GL11.GL_COMPILE);

		preRenderRawQuad(new RawQuad());

		GL11.glEndList();

		quad.setDisplayList(dlid);

		

		return quad;
	}
	public static Quad loadQuadByType(Type type)
	{
		RawQuad raw = new RawQuad();
		raw.textureCoords = Type.getTextureCoords(type);
		Quad quad = new Quad();

		int dlid = GL11.glGenLists(1);

		GL11.glNewList(dlid, GL11.GL_COMPILE);

		preRenderRawQuad(raw);

		GL11.glEndList();

		quad.setDisplayList(dlid);

		

		return quad;
	}

	public static void preRenderRawQuad(RawQuad raw)
	{
		for (byte indice : raw.getIndices())
		{
			Vector2f vertice = raw.getVerticeFromIndice(indice);
			Vector2f coords = raw.textureCoords[indice];
			GL11.glTexCoord2f(coords.getX(), coords.getY());
			GL11.glVertex2f(vertice.getX(), vertice.getY());
		}
	}
}
