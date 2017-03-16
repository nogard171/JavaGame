import static org.lwjgl.opengl.GL11.glCallList;

import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow
{
	public static void main(String[] argv)
	{
		Game displayExample = new Game();
		displayExample.start();
	}
	
	@Override
	public void onInitilization()
	{
		super.onInitilization();

		GLQuad grass = new Loader().loadQuadFromFile("res/quads/grass.quad");

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
					int newX = x * 64;
					int newZ = z * 64;
					int finalX = (newX / 2) - (newZ / 2);
					int finalZ = -(newZ / 4) - (newX / 4);
					GLObject gLObject = new GLObject();
					gLObject.setQuad(grass);
					
					gLObject.setPosition(new Vector3f(finalX, finalZ, 0));
					gLObjects.put(x+","+z, gLObject);
			}

		}

	}

	Random random = new Random();
	HashMap<String, GLObject> gLObjects = new HashMap<String, GLObject>();


	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		
		
		super.keyboard.endPoll();
	}

	public Point getMousePosition()
	{
		Point mouse = new Point((int) (super.mouse.getPosition().getX() + camera.x),
				(int) (super.mouse.getPosition().getY() + camera.y));

		return mouse;
	}

	Color color = new Color(128, 128, 128);
	float step = 0;

	@Override
	public void onResized()
	{
		super.onResized();
	}

	Vector2f camera = new Vector2f(-400, -400);

	GLQuad gLQuad = null;
	GLQuad newGrid = null;

	@Override
	public void onRender()
	{
		super.onRender();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);

		/*
		 * for(int x=0;x<10;x++) { for(int z=0;z<10;z++) { int newX = x*64; int
		 * newZ = z*64; GL11.glPushMatrix();
		 * GL11.glTranslatef((newX/2)-(newZ/2), -(newZ/4)-(newX/4), 0);
		 * 
		 * GL11.glBegin(GL11.GL_TRIANGLES);
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, quad.getTextureID());
		 * glCallList(quad.getDisplayID()); GL11.glEnd();
		 * 
		 * GL11.glPopMatrix(); }
		 * 
		 * }
		 */

		for (int x = 0; x < 5; x++)
		{
			for (int z = 0; z < 5; z++)
			{
					GLObject object = gLObjects.get(x+","+z);
					if (object != null)
					{
						GL11.glPushMatrix();
						GL11.glTranslatef(object.getPosition().getX() + object.getOffset().x,
								object.getPosition().getY() + object.getOffset().y, object.getPosition().getZ());

						super.renderQuad(object.getQuad());

						GL11.glPopMatrix();
					}
				
			}
		}

		GL11.glPopMatrix();
	}

	@Override
	public void onDestroy()
	{
		
		
	}

	
}