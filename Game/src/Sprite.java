import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Sprite {
	int width = 32;
	int height = 32;
	Color color = new Color(0,255,0);
	int render_Type = GL11.GL_QUADS;
	
	public Sprite(int new_Width, int new_Height)
	{
		this.width = new_Width;
		this.height = new_Height;
	}
	public Sprite()
	{
		this.width = 32;
		this.height = 32;
	}
	public void Render(int x, int y)
	{
		GL11.glColor3f(color.r, color.g , color.b);
		GL11.glBegin(render_Type);
		GL11.glVertex2f(x,y);
		GL11.glVertex2f(x+this.width,y);
		GL11.glVertex2f(x+this.width,y+this.height);
		GL11.glVertex2f(x,y+this.height);
		GL11.glEnd();
	}
}
