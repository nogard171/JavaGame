import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;

public class Main extends GLWindow {
	// Entry point for the application
	public static void main(String[] args) {
		new Main();
	}
	
	
	public void setup() {
		super.setup();

		
		this.addQuad(new Quad());
	}

	@Override
	public void render()
	{	super.render();
	
	}
}