import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
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
		this.addQuad(new Quad("test"));
	}
	Quad quad = null;
	@Override
	public void render()
	{	super.render();
	if(quad==null)
	{
		quad = findQuad("test");
	}
	 while(Keyboard.next()) {
         // Only listen to events where the key was pressed (down event)
         if (!Keyboard.getEventKeyState()) continue;
          
         // Switch textures depending on the key released
         switch (Keyboard.getEventKey()) {
         case Keyboard.KEY_1:
        	 quad.textureSelector = 0;
             break;
         case Keyboard.KEY_2:
        	 quad.textureSelector = 1;
             break;
         }
     }
	}
}