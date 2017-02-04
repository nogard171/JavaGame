import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Loader
{
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
		for (int i = 0;i<raw.getIndices().length;i++)
		{
			int indice = raw.getIndices()[i];
			int colorIndice = raw.getColorIndices()[i];
			int textureIndice = raw.getTextureIndices()[i];
			Vector2f textureCoord = raw.getTextureVectorsByIndice(textureIndice);
			Vector3f vec = raw.getVectorsByIndice(indice);
			Color color = raw.getColorByIndice(colorIndice);
			GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
			GL11.glTexCoord2f(textureCoord.getX(),textureCoord.getY());
			GL11.glVertex3f(vec.getX(), vec.getY(), vec.getZ());
		}
	}
}
