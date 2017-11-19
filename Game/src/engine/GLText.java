package engine;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLText {
	float fontSize = 1;
	
	public GLText()
	{
		this.setupLetters();
	}
	public void renderChar(char c) {
		GL11.glBegin(GL11.GL_LINES);
		switch(c)
		{
			case 'A':
				GL11.glVertex2f(0,0-1);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f((fontSize/2),fontSize);
				GL11.glVertex2f((fontSize/2),0-1);
				
				GL11.glVertex2f(0,(fontSize/2));
				GL11.glVertex2f((fontSize/2),(fontSize/2));
				break;
			case 'B':
				GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2)-1,fontSize);

				GL11.glVertex2f((fontSize/2),(fontSize/2)-1);
				GL11.glVertex2f((fontSize/2),0);

				GL11.glVertex2f((fontSize/2),(fontSize/2)+1);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f(0,(fontSize/2));
				GL11.glVertex2f((fontSize/2),(fontSize/2));
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2)-1,0);
				break;
			case 'C':
				GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2),0);
				break;
			case 'D':
				GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2)-1,fontSize);
				
				GL11.glVertex2f((fontSize/2),fontSize);
				GL11.glVertex2f((fontSize/2),0);
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2)-1,0);
				break;
			case 'E':
				GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f(0,(fontSize/2));
				GL11.glVertex2f((fontSize/2)-2,(fontSize/2));
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2),0);
				break;
			case 'F':
				GL11.glVertex2f(0,0-1);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f(0,(fontSize/2));
				GL11.glVertex2f((fontSize/2)-2,(fontSize/2));
				break;
			case 'G':
				GL11.glVertex2f(0,0);
				GL11.glVertex2f(0,fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f((fontSize/2),(fontSize/2));
				GL11.glVertex2f((fontSize/2),0);
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2)-1,0);
				break;
			case 'H':
				GL11.glVertex2f(0,0-1);
				GL11.glVertex2f(0,fontSize+1);
				
				GL11.glVertex2f((fontSize/2),fontSize+1);
				GL11.glVertex2f((fontSize/2),0-1);
				
				GL11.glVertex2f(0,(fontSize/2));
				GL11.glVertex2f((fontSize/2),(fontSize/2));
				break;
			case 'I':
				GL11.glVertex2f((fontSize/4),0);
				GL11.glVertex2f((fontSize/4),fontSize);
				
				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f(0,0);
				GL11.glVertex2f((fontSize/2),0);
				break;
			case 'J':
				GL11.glVertex2f((fontSize/4),0);
				GL11.glVertex2f((fontSize/4),fontSize);

				GL11.glVertex2f(0,fontSize);
				GL11.glVertex2f((fontSize/2),fontSize);
				
				GL11.glVertex2f((fontSize/4),0);
				GL11.glVertex2f(0,0);
				
				break;
		}
		GL11.glEnd();
	}
	Map<String, Vector2f[]> letters = new HashMap<String,Vector2f[]>();
	

	public void setupLetters()
	{
		
		letters.put("A",
				new Vector2f[] {
						new Vector2f(0, 0), new Vector2f(0, 23),
						new Vector2f(1, 24), new Vector2f(8, 24),
						new Vector2f(8, 23), new Vector2f(8, 0),
						new Vector2f(1, 12), new Vector2f(8, 12)
				}
		);
	}
	
	
	public void renderCharArray(char c) {
		GL11.glBegin(GL11.GL_LINES);
		Vector2f[] vectors = letters.get("A");
		
		for(int i=0;i<vectors.length;i++)
		{
			GL11.glVertex2f(vectors[i].x*fontSize,vectors[i].y*(fontSize/2));
		}
		
		GL11.glEnd();
	}
	//this.renderChar(text.charAt(c));
	
	
	public void renderString(String text, int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x-(this.fontSize/2)-2, y,0);
		for(int c =0;c<text.length();c++)
		{
			GL11.glTranslatef((this.fontSize-4), 0,0);
			//this.renderChar(text.charAt(c));
			this.renderCharArray(text.charAt(c));
		}
		GL11.glPopMatrix();
	}
}
