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

import java.io.IOException;
import java.util.Random;

public class Loader
{
	int dlid = -1;
	public void loadQuad(Quad quad,String fileName)
	{
		int dlid = GL11.glGenLists(1);		
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		preRenderQuad(new RawQuad());
		GL11.glEndList();
		quad.setDlid(dlid);		
		try {
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/"+fileName));
			if(quad.getSize() ==null)
			{
				quad.setSize(32,32);
			}
			// load texture from PNG file
			quad.setTextureID(texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void preRenderQuad(RawQuad raw)
	{
		
		glBegin(GL11.GL_TRIANGLES);
		for(byte indice:raw.getIndices())
		{
			Point vec = raw.getVertice(indice);
			Vector2f textureVector = raw.getTextureCoords(indice);
			GL11.glTexCoord2f(textureVector.getX(),textureVector.getY());
			glVertex2f(vec.getX(),vec.getY());
		}
    	glEnd();  
	}
	public void preRenderQSprite()
	{
		
		glBegin(GL11.GL_QUADS);		
		GL11.glTexCoord2f();
		glVertex2f(0,0);
		
		glVertex2f(1,0);
		
		glVertex2f(1,1);
		
		glVertex2f(0,1);
		
    	glEnd();  
	}
}
