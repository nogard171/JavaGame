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

	public void Init()
	{
		super.Init();

		Quad grass = new Loader().loadQuadFromFile("res/quads/grass.quad");

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
					int newX = x * 64;
					int newZ = z * 64;
					int finalX = (newX / 2) - (newZ / 2);
					int finalZ = -(newZ / 4) - (newX / 4);
					Cube cube = new Cube();
					cube.setQuad(grass);
					
					cube.setPosition(new Vector3f(finalX, finalZ, 0));
					cubes.put(x+","+z, cube);
			}

		}

	}

	Random random = new Random();
	HashMap<String, Cube> cubes = new HashMap<String, Cube>();

	String[] unMoveableTypes =
	{ "bedrock" };

	public boolean isUnMoveableType(String newType)
	{
		boolean result = false;
		for (String type : unMoveableTypes)
		{
			if (type.toLowerCase() == newType.toLowerCase())
			{
				result = true;
			}
		}
		return result;
	}

	@Override
	public void Update(int delta)
	{
		super.Update(delta);

		
		
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
	public void Resized()
	{
		super.Resized();
	}

	Vector2f camera = new Vector2f(-400, -400);

	Quad quad = null;
	Quad newGrid = null;

	@Override
	public void Render()
	{
		super.Render();
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

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
					Cube object = cubes.get(x+","+z);
					if (object != null)
					{
						GL11.glPushMatrix();
						GL11.glTranslatef(object.getPosition().getX() + object.getOffset().x,
								object.getPosition().getY() + object.getOffset().y, object.getPosition().getZ());

						GL11.glBegin(GL11.GL_TRIANGLES);
						glCallList(object.getQuad().getDisplayID());
						GL11.glEnd();

						GL11.glPopMatrix();
					}
				
			}
		}

		GL11.glPopMatrix();
	}

	@Override
	public void Destroy()
	{
		super.Destroy();
	}

	public static void main(String[] argv)
	{
		Game displayExample = new Game();
		displayExample.start();

	}
}