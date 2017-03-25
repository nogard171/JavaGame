import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLGraphics
{
	HashMap<String, Texture> textures = new HashMap<String, Texture>();
	Color color = new Color(255,0,0);
	public void setColor(Color newColor)
	{
		this.color = newColor;
	}
	public void setColor(int r, int g, int b)
	{
		this.color = new Color(r,g,b);
	}
	public void drawRect(int x, int y, int width, int height)
	{
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(color.getRed(),color.getGreen(),color.getBlue());
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		    GL11.glVertex2f(x,y);
		    GL11.glVertex2f(x+width,y);
		    GL11.glVertex2f(x+width,y+height);
		    GL11.glVertex2f(x,y+height);
		GL11.glEnd();
		 GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	public void fillRect(int x, int y, int width, int height)
	{
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(color.getRed(),color.getGreen(),color.getBlue());
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		    GL11.glVertex2f(x,y);
		    GL11.glVertex2f(x+width,y);
		    GL11.glVertex2f(x+width,y+height);
		    GL11.glVertex2f(x,y+height);
		GL11.glEnd();
	}
	public void drawImage(String textureLocation,int x, int y, int width, int height)
	{
		this.setColor(new Color(255,255,255));
		Texture texture = textures.get(textureLocation);
		if(texture==null)
		{
			System.out.println("Add New Texture");
			try
			{
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureLocation));
				textures.put(textureLocation, texture);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(color.getRed(),color.getGreen(),color.getBlue());
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture.getTextureID());
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
		    GL11.glVertex2f(0,0);
		    GL11.glTexCoord2f(1,0);
		    GL11.glVertex2f(width,0);
		    GL11.glTexCoord2f(1,1);
		    GL11.glVertex2f(width,height);
		    GL11.glTexCoord2f(0,1);
		    GL11.glVertex2f(0,height);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
	}
}
