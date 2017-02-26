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
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2i(32, 0);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2i(32, 32);
		GL11.glTexCoord2f(0, 1);
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
	public static AnimatedSprite getAnimatedSprite(String imageLocation)
	{
		AnimatedSprite sprite = new AnimatedSprite();
		
		try {
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imageLocation));
			// load texture from PNG file
			sprite.setTID(texture.getTextureID());
			
			
			int xCount =(int) (texture.getImageWidth()/sprite.size.getWidth());
			System.out.println("Height:"+texture.getImageHeight());
			int yCount = (int) (texture.getImageHeight()/sprite.size.getHeight());
			
			int[][] DLIDs = new int[xCount][yCount];
			
			float textureStepX = (float)1/(float)xCount;
			float textureStepY = (float)1/(float)yCount;
			
			System.out.println("X Step:"+textureStepY);
			
			for(int x=0;x<xCount;x++)
			{
				for(int y=0;y<yCount;y++)
				{
					int dlid = GL11.glGenLists(1);		
					GL11.glNewList(dlid, GL11.GL_COMPILE);
					
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(x*textureStepX, (y+1)*textureStepY);
					GL11.glVertex2i(0, 0);
					GL11.glTexCoord2f((x+1)*textureStepX, (y+1)*textureStepY);
					GL11.glVertex2i(sprite.size.getWidth(), 0);
					GL11.glTexCoord2f((x+1)*textureStepX, y*textureStepY);
					GL11.glVertex2i(sprite.size.getWidth(), sprite.size.getHeight());
					GL11.glTexCoord2f(x*textureStepX, y*textureStepY);
					GL11.glVertex2i(0, sprite.size.getHeight());
					
					GL11.glEnd();					
					GL11.glEndList();
					DLIDs[x][y] = dlid;
				}
			}
			sprite.setDLIDs(DLIDs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}
}
