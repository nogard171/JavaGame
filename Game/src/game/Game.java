package game;

import engine.GLWindow;

public class Game {

	GLWindow win;

	public void start() {
		this.setup();
		boolean windowClosed = false;
		while (!windowClosed) {
			this.update();
			this.render();
		}
		this.destroy();
	}

	private void setup() {
		this.win = new GLWindow();
		this.win.create();
	}

	private void update() {
		this.win.update();
	}

	private void render() {
		this.win.render();
	}

	private void destroy() {
		this.win.destroy();
	}

}
