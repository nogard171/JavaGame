import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends Window {
	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
	float rotation = 0;
	
	public void Init()
	{
		super.Init();
		 hud = new HUD(this.displayWidth, this.displayWidth);
	}

	public void Update(int delta) {
		super.Update(delta);
		hud.Update(delta);
		// rotate quad
		rotation += 0.15f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			x -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			x += 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			y -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			y += 0.35f * delta;
		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > super.displayWidth)
			x = super.displayWidth;
		if (y < 0)
			y = 0;
		if (y > super.displayHeight)
			y = super.displayHeight;
	}

	HUD hud = null;

	public void Render() {
		super.Render();
		
		// draw quad
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x - 50, y - 50);
		GL11.glVertex2f(x + 50, y - 50);
		GL11.glVertex2f(x + 50, y + 50);
		GL11.glVertex2f(x - 50, y + 50);
		GL11.glEnd();
		GL11.glPopMatrix();
		
		hud.Render();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();
	}
}