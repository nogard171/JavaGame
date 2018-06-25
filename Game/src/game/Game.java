package game;

import org.lwjgl.LWJGLException;

import engine.Initilizer;
import engine.Platform;
import engine.Renderer;
import engine.Updater;

public class Game extends Platform {
	GameInitilizer initilizer;
	GameUpdater updater;
	GameRenderer renderer;

	@Override
	public void run() throws LWJGLException {
		this.initilizer = new GameInitilizer();
		this.updater = new GameUpdater();
		this.renderer = new GameRenderer();
		
		super.run();
	}

	@Override
	public void initilize() {
		super.initilize();
		this.initilizer.initilize();
	}

	@Override
	public void update() {
		super.update();
		this.updater.update();
	}

	@Override
	public void render() {
		super.render();
		this.renderer.render();
	}
}