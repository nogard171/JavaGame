import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends GLDisplay {

	@Override
	public void Setup() {
		super.Setup();

		shader = new GLShader("basic.vert", "basic.frag");
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	Texture texture;

	GLShader shader;

	@Override
	public void Update() {
		super.Update();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			view.x+=10;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			view.x-=10;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			view.y-=10;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			view.y+=10;
		}
		
	}

	Vector2f view = new Vector2f(0,0);
	
	@Override
	public void Render() {
		super.Render();

		shader.Run();

		float[] colorData = { 1, 1, 1, 1 };
		shader.sendUniform4f("vertColor", colorData);
		if (texture != null) {
			shader.sendTexture("myTexture", texture.getTextureID());
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(view.getX(), view.getY(), 0);
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					
					int newX = (x * 32) - (z * 32);
					int newY = (y*32);
					int newZ= (z * 16) + (x * 16);
					RenderCube(newX, newY, newZ);
				}
			}
		}
		GL11.glPopMatrix();
	}

	public void RenderCube(float x, float y, float z) {
		z-=y;
		GL11.glBegin(GL11.GL_QUADS);
		// left
		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(32 + x, 0 - z);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(0 + x, 16 - z);

		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex2f(0 + x, 48 - z);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32 + x, 32 - z);

		// top
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(32 + x, 32 - z);

		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(64 + x, 48 - z);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32 + x, 64 - z);

		GL11.glTexCoord2f(0, 0.5f);
		GL11.glVertex2f(0 + x, 48 - z);

		// right
		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(32 + x, 0 - z);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(64 + x, 16 - z);

		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex2f(64 + x, 48 - z);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32 + x, 32 - z);

		GL11.glEnd();
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
