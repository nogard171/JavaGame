import static org.lwjgl.opengl.GL11.glCallList;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
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
		
	
	}

	@Override
	public void Update(int delta)
	{
		super.Update(delta);

		/*this code will be for clickable objects.
		 * 
		 * for (Object obj : objects)
		{
			if(obj.getBounds().contains(super.mouse.getPosition())&&super.mouse.mouseButtons[0])
			{
				obj.setColor(0,0,1);
			}
			else if(obj.getBounds().contains(super.mouse.getPosition()))
			{
				obj.setColor(1,0,0);
			}
			else
			{
				obj.setColor(1,1,1);
			}
		}*/
		
		
		super.keyboard.endPoll();
	}
	float step = 0;
	@Override
	public void Resized()
	{
		super.Resized();
	}

	Vector2f camera = new Vector2f(0, 0);

	@Override
	public void Render()
	{
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);

		//render code

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