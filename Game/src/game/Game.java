package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import engine.Platform;

public class Game extends Platform {

	@Override
	public void run() throws LWJGLException {

		super.run();
	}

	@Override
	public void initilize() {
		super.initilize();
	}

	@Override
	public void update() {
		super.update();
		
		Display.setTitle("FPS:" + super.fps.getDelta());
	}

	@Override
	public void render() {
		super.render();
	}
}