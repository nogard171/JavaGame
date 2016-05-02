import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class TextBox extends Entity {
	public TextBox(int i, int j) {
		this.x = i;
		this.y = j;
		this.width = 100;
		this.height = 16;
	}

	Text textDisplay = new Text();
	public String text = "";

	@Override
	public void Render() {
		GL11.glTranslatef(x, y, 0);
		
		GL11.glPushMatrix();
		
		if (this.isHovered) {
			GL11.glColor3f(0.75f, 0.75f, 0.75f);
		} else {
			GL11.glColor3f(0, 0, 0);
		}		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(width, 0);
		GL11.glVertex2f(width, height);
		GL11.glVertex2f(0, height);
		GL11.glEnd();

		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(1, 1);
		GL11.glVertex2f(width - 1, 1);
		GL11.glVertex2f(width - 1, height - 1);
		GL11.glVertex2f(1, height - 1);
		GL11.glEnd();
		int fontSize = 8;
		textDisplay.Render(this.text, 0,0,8, Color.black);
		
		if (this.Focus)
		{
		    time++;
		    GL11.glBegin(GL11.GL_QUADS);
		    if (time > 25 && time < 50)
		    {
		    	GL11.glColor3f(1,1,1);
		    }
		    else if (time >= 50)
		    {
		    	time = 0;
		    }
		    else
		    {
		    	GL11.glColor3f(0,0,0);
		    }
		    int textWidth =(this.text.length()*(fontSize));
			GL11.glVertex2f(2+textWidth, 2);
			GL11.glVertex2f(4+textWidth, 2);
			GL11.glVertex2f(4+textWidth, height-2);
			GL11.glVertex2f(2+textWidth, height-2);
			GL11.glEnd();
		}
		GL11.glPopMatrix();
		GL11.glTranslatef(-x, -y, 0);
	}

	public void addChar(String keyChar) {
		this.text += keyChar;
		
	}
	public void backSpace() {
		if(this.text.length()>0)
		{
			this.text = this.text.substring(0, this.text.length()-1);			
		}		
	}
}
