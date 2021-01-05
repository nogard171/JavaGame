package threads;

import java.awt.Rectangle;

import core.GameData;
import core.Window;
import utils.Loader;
import utils.Telemetry;

public class BaseThread extends Thread {
	private boolean isRunning = true;

	public void run() {
		init();
		while (isRunning) {
			update();
			render();
			finalizeRender();
			if (Window.isCloseRequested()) {
				isRunning = false;
			}
		}
		destroy();
	}

	public void init() {
		Loader.loadConfig("game.config");
		Window.create();
		GameData.view = new Rectangle(0, 0, Window.getWidth(), Window.getHeight());
	}

	public void update() {
		Window.update();
		Telemetry.update();
		if (Window.wasResized()) {
			GameData.view = new Rectangle(0, 0, Window.getWidth(), Window.getHeight());
		}
	}

	public void render() {
		Window.render();
	}

	public void finalizeRender() {
		Telemetry.render();
		Window.finalizeRender();
	}

	public void destroy() {
		Telemetry.destroy();
		Window.destroy();
	}
}
