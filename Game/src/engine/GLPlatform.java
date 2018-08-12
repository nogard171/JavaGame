package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class GLPlatform {
	GLWindow window;
	public Input input;
	public FPSEngine fps;

	public void run() throws LWJGLException {
		window = new GLWindow();
		input = new Input();
		fps = new FPSEngine();

		window.create();
		this.initilize();
		window.run();
		fps.initilize();

		while (!window.isActive()) {
			window.update();
			this.update();
			window.render();
			this.render();
			window.sync();
		}
		window.destroy();
	}

	public void initilize() {
	}

	public void update() {
		fps.update();
	}

	public void render() {
	}
}
