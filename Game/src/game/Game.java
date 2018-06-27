package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import classes.Sprite;
import classes.SpriteFrame;
import engine.Platform;
import utils.Loader;

public class Game extends Platform {

	@Override
	public void run() throws LWJGLException {
		super.run();
	}

	Sprite sprite;

	@Override
	public void initilize() {
		super.initilize();

		sprite = Loader.loadSprite("grass.png", 32, 32);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
	}

	@Override
	public void update() {
		super.update();

		Display.setTitle("FPS:" + super.fps.getFPS());
	}

	@Override
	public void render() {
		super.render();
		

		SpriteFrame currentFrame = sprite.getFrame(0, 0);

		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 30; y++) {
				//GL11.glPushMatrix();
				//GL11.glTranslatef(x * 32, y * 32, 0);
				GL11.glCallList(currentFrame.getDisplayListID());
				//GL11.glPopMatrix();
			}
		}

	}
}