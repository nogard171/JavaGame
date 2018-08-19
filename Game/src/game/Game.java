package game;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import classes.GLBound;
import classes.GLSize;
import classes.GLTextureData;
import classes.GLView;
import classes.GLWorld;
import engine.GLPlatform;
import utils.Loader;

public class Game extends GLPlatform {

	public static HashMap<String, GLTextureData> textureData = new HashMap<String, GLTextureData>();
	public static GLSize textureSize = new GLSize();

	@Override
	public void run() throws LWJGLException {
		super.run();

	}

	Texture texture;

	@Override
	public void initilize() {
		super.initilize();
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/tex/grass.png"));
			if (texture != null) {
				textureSize = new GLSize(64f/texture.getImageWidth(), 64f/texture.getImageHeight());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		textureData = new Loader().loadTextureData();
	}

	@Override
	public void update() {
		super.update();

		Display.setTitle("FPS:" + super.fps.getFPS());
		
		float amount = 0.5f;
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			view.bound.position.moveX(-amount);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			view.bound.position.moveX(amount);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			view.bound.position.moveY(amount);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			view.bound.position.moveY(-amount);
		}
	}

	GLWorld world = new GLWorld();
	GLView view = new GLView();

	@Override
	public void render() {
		super.render();
		GL11.glPushMatrix();
		GL11.glTranslatef(-view.bound.position.getX(),-view.bound.position.getY(),0);
		// GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		world.render(view);
		GL11.glPopMatrix();
	}
}