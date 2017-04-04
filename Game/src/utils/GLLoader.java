package utils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.GLBound;
import entities.GLSprite;

public class GLLoader
{
	public void loadBound(GLBound bound)
	{
		int displayListHandle = GL11.glGenLists(1);
		 
		// Start recording the new display list.
		GL11.glNewList(displayListHandle, GL11.GL_COMPILE);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glColor3f(0, 0, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f((int)bound.getSize().getWidth(), 0);
		GL11.glVertex2f((int)bound.getSize().getWidth(),(int)bound.getSize().getHeight());
		GL11.glVertex2f(0, (int)bound.getSize().getHeight());
		GL11.glEnd();
		
		GL11.glColor3f(1,1,1);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// End the recording of the current display list.
		GL11.glEndList();
		
		bound.setDisplayID(displayListHandle);
	}
	
	public static void loadGLSprite(GLSprite sprite, String textureLocation)
	{
		Texture tempTexture = loadTexture(textureLocation);
		if (tempTexture != null)
		{
			int textureID = tempTexture.getTextureID();
			int textureWidth = tempTexture.getImageWidth() / (int) sprite.getSize().getWidth();
			int textureHeight = tempTexture.getImageHeight() / (int) sprite.getSize().getHeight();

			float textureXStep = 1 / (float) textureWidth;
			float textureYStep = 1 / (float) textureHeight;
			
			sprite.setTextureID(textureID);
			
			int[][] dlIDs = new int[textureWidth][textureHeight];
			for (int xStep = 0; xStep < textureWidth; xStep++)
			{
				for (int yStep = 0; yStep < textureHeight; yStep++)
				{
					int displayListHandle = GL11.glGenLists(1);
					 
					// Start recording the new display list.
					GL11.glNewList(displayListHandle, GL11.GL_COMPILE);
					
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f((int) xStep * textureXStep, (int) (yStep + 1) * textureYStep);
					GL11.glVertex2f(0, 0);
					GL11.glTexCoord2f((int) (xStep + 1) * textureXStep,
							(int) (yStep + 1) * textureYStep);
					GL11.glVertex2f((int)sprite.getSize().getWidth(), 0);
					GL11.glTexCoord2f((int) (xStep + 1) * textureXStep, (int) (yStep) * textureYStep);
					GL11.glVertex2f((int)sprite.getSize().getWidth(), (int)sprite.getSize().getHeight());
					GL11.glTexCoord2f((int) (xStep) * textureXStep, (int) (yStep) * textureYStep);
					GL11.glVertex2f(0, (int)sprite.getSize().getHeight());
					GL11.glEnd();
					// End the recording of the current display list.
					GL11.glEndList();
					
					dlIDs[xStep][yStep] = displayListHandle;
				}
			}
			sprite.setDisplayIDs(dlIDs);

		} else
		{
			System.out.println("GLSprite could not be loaded.");
		}
	}

	static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static Texture loadTexture(String textureLocation)
	{
		Texture texture = textures.get(textureLocation);
		if (texture == null)
		{
			System.out.println("Add New Texture");
			File f = new File(textureLocation);
			if (f.exists() && !f.isDirectory())
			{
				try
				{
					texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureLocation));
					textures.put(textureLocation, texture);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
			{
				texture = null;
			}
		}
		return texture;
	}
}
