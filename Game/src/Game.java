import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

import org.lwjgl.util.Rectangle;
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

public class Game extends GLWindow {

	public void Init() {
		super.Init();

		//setTexture(hud.test, "/res/img/window.png");
		this.addSprite("GRASS","/res/img/grass.png");
	}
	HashMap<String,Sprite> sprites = new HashMap<String,Sprite>();
	public Texture getTexture(String string) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(string));
		} catch (RuntimeException ioe) {
			System.out.println(ioe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		return texture;
	}

	public void addSprite(String name,String sprite)
	{
		Sprite newSprite = new Sprite();
		newSprite.texture = this.getTexture(sprite);
		sprites.put(name,newSprite);		
	}

	@Override
	public void Update(int delta) {
		super.Update(delta);
		float speed = delta/2;

		if(keyboard.keyPressed(Keyboard.KEY_A))
		{
			position.x +=speed;
		}
		if(keyboard.keyPressed(Keyboard.KEY_D))
		{
			position.x -=speed;
		}
		if(keyboard.keyPressed(Keyboard.KEY_W))
		{
			position.y +=speed;
		}
		if(keyboard.keyPressed(Keyboard.KEY_S))
		{
			position.y -=speed;
		}
		super.keyboard.endPoll();
	}

	@Override
	public void Resized() {
		super.Resized();		
	}

	int time = 0;
	// int move = 0;
	Random ran = new Random();
	boolean move_test2 = false;
	Vector2f position = new Vector2f(0,0);
	@Override
	public void Render() {
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(position.x, position.y,0);
		for(int x = 0;x<10;x++)
		{
			for(int y = 0;y<10;y++)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(x*32, y*32, 0);
				sprites.get("GRASS").Render();
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();
	}
	@Override
	public void Destroy()
	{
		super.Destroy();
		for (Sprite sprite : sprites.values()) {
		    sprite.Destroy();
		}
	}
	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}