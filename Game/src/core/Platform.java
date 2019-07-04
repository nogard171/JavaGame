package core;

import org.lwjgl.input.Keyboard;

public class Platform extends Thread {
	private Window displayWindow;

	protected Camera camera;
	protected CameraController cameraController;

	private boolean running = true;

	@Override
	public void run() {
		this.load();
		this.start();
		while (running) {
			this.update(1);
			this.render();
			this.sync();
		}
		this.destroy();
	}

	public void load() {
		this.displayWindow = new Window();
		this.displayWindow.create();

		this.camera = new Camera();
		this.cameraController = new CameraController(this.camera);
	}

	public void start() {
		this.displayWindow.setupOpenGL();

		// this.camera.create();
	}

	public void update(float delta) {
		this.displayWindow.update();

		Keyboard.poll();

		// this.camera.acceptInput((float) delta);
		// this.camera.apply();
		this.cameraController.update(delta);
		this.cameraController.apply();

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			this.running = false;
		}

	}

	public void render() {
		this.displayWindow.render();

	}

	public void sync() {
		this.displayWindow.sync();

	}

	public void destroy() {
		this.displayWindow.destroy();

	}
}
