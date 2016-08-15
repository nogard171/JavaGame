import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;

public class GLWindow {
	private final String WINDOW_TITLE = "The Quad: glDrawElements";
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	ArrayList<Quad> quads = new ArrayList<Quad>();

	public void addQuad(Quad object) {
		quads.add(object);		
	}
	public GLWindow() {
		// Initialize OpenGL (Display)
		this.setupOpenGL();

		this.setup();

		while (!Display.isCloseRequested()) {
			// Do a single loop (logic/render)
			// this.update();
			this.render();

			// Force a maximum FPS of about 60
			Display.sync(60);
			// Let the CPU synchronize with the GPU if GPU is tagging behind
			Display.update();
		}

		// Destroy OpenGL (Display)
		this.destroyOpenGL();
	}

	public void update() {

	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		
		for (Quad quad : quads) {
			if (quad.initilized) {
				quad.Render();
			}
			else
			{
				quad.setup();
				quad.initilized = true;
			}
		}
	}

	public void setup() {
		
	}

	public void setupOpenGL() {
		// Setup an OpenGL context with API version 3.2
		try {
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withProfileCore(true)
					.withForwardCompatible(true);

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(WINDOW_TITLE);
			Display.create(pixelFormat, contextAtrributes);

			GL11.glViewport(0, 0, WIDTH, HEIGHT);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

		// Map the internal OpenGL coordinate system to the entire screen
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	public void destroy() {
		for (Quad quad : quads) {
			if (quad.initilized) {
				quad.cleanup();
			}
		}
	}

	public void destroyOpenGL() {
		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0);

		this.destroy();

		Display.destroy();
	}

}
