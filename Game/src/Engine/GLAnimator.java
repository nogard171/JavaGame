package Engine;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimator extends GLComponent {
	GLFrame[][] frames = new GLFrame[1][1];
	GLSize size = new GLSize(32,64);
	public void loadFrames(String filename)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/"+filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public void Run() {
		
	}
}
