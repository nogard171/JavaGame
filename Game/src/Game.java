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

		GLQuad grass = new Loader().loadGLQuadFromFile("res/quads/grass.quad");
		GLQuad tree = new Loader().loadGLQuadFromFile("res/quads/tree.quad");
		GLQuad grid = new Loader().loadGLQuadFromFile("res/quads/grid.quad");

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
					gLObject.type = "grass";
					gLObject.setPosition(new Vector3f(finalX, finalZ, 0));
					objects.add(gLObject);
			}
		}
		
		GLObject gLObject2 = new GLObject();
		gLObject2.type = "tree";
		gLObject2.setQuad(tree);
		gLObject2.setPosition(new Vector3f(getObjectByType("grass").get(0).getPosition().getX(),getObjectByType("grass").get(0).getPosition().getY()+24,0));
		objects.add(gLObject2);
		
		
		GLObject gLObject3 = new GLObject();
		gLObject3.type = "hover";
		gLObject3.setQuad(grid);
		gLObject3.setPosition(new Vector3f(0,0,0));
		objects.add(gLObject3);

	}

	Random random = new Random();

	ArrayList<GLObject> objects = new ArrayList<GLObject>();

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		for(GLObject object:this.objects)
		{
			if(object.getBounds().contains(this.getMousePosition()))
			{
				getObjectByType("hover").get(0).setPosition(new Vector3f(object.getPosition().getX(),object.getPosition().getY(),0));
			}
		}
		
		super.keyboard.endPoll();
	}
	
	public ArrayList<GLObject> getObjectByType(String type)
	{
		ArrayList<GLObject> newObjects = new ArrayList<GLObject>();
		
		for(GLObject object:this.objects)
		{
			if(object.type.toLowerCase()==type.toLowerCase())
			{
				newObjects.add(object);
			}
		}
		
		return newObjects;
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

		for(GLObject object:this.objects)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(object.getPosition().getX() + object.getOffset().x,
					object.getPosition().getY() + object.getOffset().y, object.getPosition().getZ());

			super.renderQuad(object.getQuad());

			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}

	@Override
	public void onDestroy()
	{
		
		
	}

	
}