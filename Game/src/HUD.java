import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HUD {
	int displayWidth = 0;
	int displayHeight = 0;
	Texture texture;
	
	public HUD(int newWidth, int newHeight) {
		this.displayWidth = newWidth;
		this.displayHeight = newHeight;
		try {
			texture = TextureLoader.getTexture("JPG", ResourceLoader
					.getResourceAsStream("resources/images/test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Render() {
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		texture.bind();
		GL11.glColor3f(1,1,1);
		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(100, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(100, 100);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, 100);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
	}

	public void Update(int delta) {

	}
}
