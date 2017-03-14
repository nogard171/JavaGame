import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Graphics
{
	DisplayMode displayMode;
	int _defaultWidth = 800;
	int _defaultHeight = 600;
	int _width = 800;
	int _height = 600;
	int _fps = 120;
	HashMap<String,GLImage> images = new HashMap<String, GLImage>();
	
	public static void main(String[] argv)
	{
		Game game = new Game();
		game.start();
	}
	
	public void start()
	{
		displayMode = new DisplayMode(_defaultWidth,_defaultHeight);
		try
		{
			Display.setDisplayMode(displayMode);
			// set the display resizable if the resizable is true
			Display.setResizable(true);
			// create the display.
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.resized();
		this.enableGL();
		while (!Display.isCloseRequested())
		{
			if (Display.wasResized())
			{
				this.resized();
			}
			this.update();
			this.render(this);
			Display.update();
			Display.sync(this._fps);
		}
		this.destroy();
	}
	
	public void resized()
	{
		if(Display.getWidth()!=this._defaultWidth&&Display.getHeight()!=this._defaultHeight)
		{
			this._height = Display.getHeight();
			this._width = Display.getWidth();
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this._width, 0,this._height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		
	}
	public void enableGL()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics g)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void destroy()
	{
		Display.destroy();
		System.exit(0);
	}
	
	public void drawImage(String imageLocation,int x, int y, int width, int height)
	{		
		GLImage image = images.get(imageLocation);
		if(image==null)
		{
			int displayID = GL11.glGenLists(1);
			GL11.glNewList(displayID, GL11.GL_COMPILE);
			
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2i(0, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2i(width, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2i(width, height);
			GL11.glTexCoord2f(0 ,0);
			GL11.glVertex2i(0, height);
			
			GL11.glEnd();
			
			GL11.glEndList();
			int textureID = -1;
			try {
				textureID = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imageLocation)).getTextureID();
			} catch (IOException e) {
				e.printStackTrace();
			}
			image = new GLImage(displayID,textureID);
			images.put(imageLocation, image);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, image.getTextureID());
		GL11.glCallList(image.getDisplayID());
		
		GL11.glPopMatrix();
		
		
		
	}
}
