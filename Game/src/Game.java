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

		Quad grass = new Loader().loadQuadFromFile("res/quads/grass.raw");
		Quad bedrock = new Loader().loadQuadFromFile("res/quads/bedrock.raw");
		Quad water = new Loader().loadQuadFromFile("res/quads/water.raw");

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
				int newX = x * 64;
				int newZ = z * 64;
				int finalX = (newX / 2) - (newZ / 2);
				int finalZ = -(newZ / 4) - (newX / 4);
				Cube cube = new Cube();
				cube.type = "bedrock";
				cube.setQuad(bedrock);
				cube.setPosition(new Vector3f(finalX, finalZ, 0));
				cubes.put(x+",0,"+z, cube);
			}

		}

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
				for (int y = 1; y < 2; y++)
				{
					int newX = x * 64;
					int newZ = z * 64;
					int finalX = (newX / 2) - (newZ / 2);
					int finalZ = -(newZ / 4) - (newX / 4) + (y * 32);
					Cube cube = new Cube();
					cube.setQuad(grass);
					
					cube.setPosition(new Vector3f(finalX, finalZ, 0));
					cubes.put(x+","+ y+","+z, cube);
				}
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

		for (int x = 0; x < 10; x++)
		{
			for (int z = 0; z < 10; z++)
			{
				for (int y = 0; y < 2; y++)
				{
					Cube object = cubes.get(x+","+ y+","+z);
					if (object != null)
					{
						if (isUnMoveableType(object.type))
						{

						} else
						{
							Point point = new Point((int) (getMousePosition().getX() - object.getPosition().getX()),
									(int) (getMousePosition().getY() - object.getPosition().getY()));
							if (object.getQuad().getBounds().contains(point))
							{
								object.setOffset(0, 24);

							} else
							{
								object.setOffset(0, 0);
							}
							
							

						}
					}
				}
			}
		}
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
				for (int y = 0; y < 2; y++)
				{
					Cube object = cubes.get(x+","+ y+","+z);
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