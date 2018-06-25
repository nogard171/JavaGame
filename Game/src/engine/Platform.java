package engine;

import org.lwjgl.LWJGLException;

import data.DataHub;

public class Platform {

	public void run() throws LWJGLException {
		DataHub.window.create();
		this.initilize();
		DataHub.window.run();
		while (!DataHub.window.isActive()) {
			DataHub.window.update();
			this.update();
			DataHub.window.render();
			this.render();
			DataHub.window.sync();
		}
		DataHub.window.destroy();
	}

	public void initilize() {
		Initilizer.initilize();
		
	}

	public void update() {
		Updater.update();

	}

	public void render() {
		Renderer.render();
	}
}
