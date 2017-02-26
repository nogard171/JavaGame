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
		
		
		Sprite sprite = new SpriteLoader().getSprite("res/img/grass.png");
		
		sprites.put(Type.GRASS, sprite);
		
		for(int x=0;x<10;x++)
		{
			for(int y=0;y<10;y++)
			{
				Object obj = new Object();
				obj.setPosition(x*32, y*32);
				objects.add(obj);
			}
		}
		
		animatedSprite = new SpriteLoader().getAnimatedSprite("res/img/guy.png");
		
	
	}

	//HashMap<Type, Sprite> sprites = new HashMap<Type, Sprite>();
	AnimatedSprite animatedSprite = null;
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
		
		if(super.keyboard.keyPressed(Keyboard.KEY_D))
		{
			animatedSprite.animation.setY(2);
		}
		if(super.keyboard.keyPressed(Keyboard.KEY_W))
		{
			animatedSprite.animation.setY(1);
		}
		if(super.keyboard.keyPressed(Keyboard.KEY_S))
		{
			animatedSprite.animation.setY(0);
		}
		if(super.keyboard.keyPressed(Keyboard.KEY_A))
		{
			animatedSprite.animation.setY(3);
		}
		
		if(super.keyboard.keyPressed(Keyboard.KEY_A)||super.keyboard.keyPressed(Keyboard.KEY_S)||super.keyboard.keyPressed(Keyboard.KEY_W)||super.keyboard.keyPressed(Keyboard.KEY_D))
		{
			step+=0.05f;
		}
		
		if(step>=4)
		{
			step=0;
		}
		animatedSprite.animation.setX((int)step);
		super.keyboard.endPoll();
	}
	float step = 0;
	@Override
	public void Resized()
	{
		super.Resized();
	}

	Vector2f camera = new Vector2f(0, 0);
	ArrayList<Object> objects = new ArrayList<Object>();
	HashMap<Type, Sprite> sprites = new HashMap<Type, Sprite>();

	@Override
	public void Render()
	{
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.x, -camera.y, 0);

		for (Object obj : objects)
		{
			Sprite sprite = this.sprites.get(obj.getType());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTID());
			GL11.glPushMatrix();
			GL11.glTranslatef(obj.getPosition().getX(), obj.getPosition().getY(),0);
			GL11.glColor3f(obj.getColor().getRed(),obj.getColor().getGreen(),obj.getColor().getBlue());
			glCallList(sprite.getDLID());
			GL11.glPopMatrix();
		}
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, animatedSprite.getTID());
		glCallList(animatedSprite.getDLID());
		GL11.glPopMatrix();

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