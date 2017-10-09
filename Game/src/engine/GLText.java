package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLText {
	Vector2f position = new Vector2f(100,100);
	int fontSize = 16;
	public void renderChar(char c) {
		GL11.glBegin(GL11.GL_LINES);
		switch(c)
		{
		case 'A':
			
			
			GL11.glVertex2f(position.x,position.y);
			GL11.glVertex2f(position.x,position.y+fontSize);
			
			GL11.glVertex2f(position.x,position.y+fontSize);
			GL11.glVertex2f(position.x+(fontSize/2),position.y+fontSize);
			
			GL11.glVertex2f(position.x+(fontSize/2),position.y+fontSize);
			GL11.glVertex2f(position.x+(fontSize/2),position.y);
			
			GL11.glVertex2f(position.x,position.y+(fontSize/2));
			GL11.glVertex2f(position.x+(fontSize/2),position.y+(fontSize/2));
		}
		GL11.glEnd();
	}
}
