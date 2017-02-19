import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SpriteLoader
{
	public static Sprite getSprite(String imageLocation)
	{
		Sprite sprite = new Sprite();
		
		int dlid = GL11.glGenLists(1);		
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(32, 0);
		GL11.glVertex2i(32, 32);
		GL11.glVertex2i(0, 32);
		
		GL11.glEnd();
		
		GL11.glEndList();
		sprite.setDLID(dlid);
		
		try {
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imageLocation));
			// load texture from PNG file
			sprite.setTID(texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sprite;
	}
}
