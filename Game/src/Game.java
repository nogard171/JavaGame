import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {
	Type[] types = { Type.GRASS, Type.DIRT };
	// ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	HashMap<Type, Sprite> sprites = new HashMap<Type, Sprite>();

	public void Init() {
		super.Init();
		for (int t = 0; t < types.length; t++) {
			Sprite newSprite = new Sprite();
			newSprite.type = types[t];
			sprites.put(types[t], newSprite);
		}
	}

	public void setTexture(Sprite sprite, String string) {
		try {
			sprite.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(string));
		} catch (RuntimeException ioe) {
			System.out.println(ioe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	float speed = 0;

	@Override
	public void Update(int delta) {
		super.Update(delta);

		if (keyboard.keyPressed(Keyboard.KEY_A)) {
			camx += 10;
		}
		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			camx -= 10;
		}

		if (keyboard.keyPressed(Keyboard.KEY_W)) {
			camy += 10;
		}
		if (keyboard.keyPressed(Keyboard.KEY_S)) {
			camy -= 10;
		}

		// System.out.println("Level:" + level);
		super.keyboard.endPoll();
	}

	int camx = 0;
	int camy = 0;

	@Override
	public void Resized() {
		super.Resized();
	}

	// Sprite sprite = new Sprite();
	public void Render() {
		super.Render();
		GL11.glPushMatrix();
		GL11.glTranslatef(camx, camy, 0);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 2; z++) {
					int newX = (x*32)-(y*32);
					int newY = (y*16)+(x*16);
					GL11.glPushMatrix();
					GL11.glTranslatef(newX,newY, 0);
					if(x==0)
					{
						RenderSprite(Type.DIRT);
					}
					else if(x==4)
					{
						RenderSprite(Type.DIRT);
					}
					else
					{
						RenderSprite(Type.GRASS);
					}

					GL11.glPopMatrix();
				}
			}
		}
		GL11.glPopMatrix();
		GL11.glColor3f(1, 1, 1);
	}

	private void RenderSprite(Type type) {
		sprites.get(type).Render();
		;
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}