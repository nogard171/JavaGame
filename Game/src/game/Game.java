package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import engine.GLPlatform;
import utils.Loader;

public class Game extends GLPlatform {

	@Override
	public void run() throws LWJGLException {
		super.run();
	}

	@Override
	public void initilize() {
		super.initilize();

		// GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
	}

	@Override
	public void update() {
		super.update();

		Display.setTitle("FPS:" + super.fps.getFPS());
	}

	@Override
	public void render() {
		super.render();
		
	}
}