import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
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
			texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("resources/images/grass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}

	public void Render() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		texture.bind();
		GL11.glColor3f(1, 1, 1);
		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(32, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(32, 32);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, 32);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		RenderTextBox();
		if (debug) {
			RenderDebug();
		}
	}

	public void RenderTextBox() {
		int height = 20;
		int width = 100;
		int x = 100;
		int y = 100;

		GL11.glTranslatef(x, y, 0);
		GL11.glPushMatrix();

		GL11.glColor3f(0, 0, 0);
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
		GL11.glPopMatrix();

		GL11.glTranslatef(-x, -y, 0);
	}

	public void RenderDebug() {
		int height = 20;
		int width = 100;
		int x = 150;
		int y = 150;

		GL11.glTranslatef(x, y, 0);
		/*
		 * GL11.glPushMatrix(); GL11.glColor4f(1, 1, 1, 0.5f);
		 * GL11.glBegin(GL11.GL_QUADS); GL11.glVertex2f(0, 0);
		 * GL11.glVertex2f(width, 0); GL11.glVertex2f(width, height);
		 * GL11.glVertex2f(0, height); GL11.glEnd();
		 * 
		 * GL11.glColor4f(0, 0, 0, 0.5f); GL11.glBegin(GL11.GL_QUADS);
		 * GL11.glVertex2f(1, 1); GL11.glVertex2f(width - 1, 1);
		 * GL11.glVertex2f(width - 1, height - 1); GL11.glVertex2f(1, height -
		 * 1); GL11.glEnd(); GL11.glPopMatrix();
		 */

		drawString("A B C D E F G H I J K L M O N P Q R S T U V W X Y Z", 0, 0, Color.green);

		GL11.glColor3f(0, 0, 0);
		renderString("A B C D E F G H I J K L M O N P Q R S T U V W X Y Z", 0, 20,48, Color.green);
		GL11.glTranslatef(-x, -y, 0);
	}

	public void renderString(String s, int x, int y, int fontSize, Color color) {
		int fontWidth = fontSize/6;
		for (char c : s.toCharArray()) {
			switch (c) {
			case 'A':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, y+fontWidth);
				GL11.glVertex2f(x+fontWidth,y+fontWidth);
				GL11.glVertex2f(x+fontWidth,y+fontSize+(fontSize/8));
				GL11.glVertex2f(x+0, y+fontSize+(fontSize/8));

				GL11.glVertex2f(x+fontSize-fontWidth, y+fontWidth);
				GL11.glVertex2f(x+fontSize, y+fontWidth);
				GL11.glVertex2f(x+fontSize, y+fontSize+(fontSize/8));
				GL11.glVertex2f(x+fontSize-fontWidth,y+ fontSize+(fontSize/8));

				GL11.glVertex2f(x+fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+y);

				GL11.glVertex2f(x+fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, (fontSize/2)+fontWidth+y);
				GL11.glVertex2f(x+fontWidth, (fontSize/2)+fontWidth+y);
				GL11.glEnd();
				x+=fontSize;
				break;
			case 'B':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, fontWidth+y);
				GL11.glVertex2f(x+fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontSize+y);
				GL11.glVertex2f(x+0, fontSize+y);

				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontSize, fontWidth+y);
				GL11.glVertex2f(x+fontSize, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize/2+y);
				
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth + (fontSize/2)+y);
				GL11.glVertex2f(x+fontSize, fontWidth + (fontSize/2)+y);
				GL11.glVertex2f(x+fontSize, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize+y);

				GL11.glVertex2f(x+fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+y);

				GL11.glVertex2f(x+fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, (fontSize/2)+fontWidth+y);
				GL11.glVertex2f(x+fontWidth, (fontSize/2)+fontWidth+y);
				
				
				GL11.glVertex2f(x+fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+fontSize+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+fontSize+y);
				
				GL11.glEnd();
				x+=fontSize;
				break;
			case 'C':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, fontWidth+y);
				GL11.glVertex2f(x+fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontSize+y);
				GL11.glVertex2f(x+0, fontSize+y);

				GL11.glVertex2f(x+fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+y);
				
				GL11.glVertex2f(x+fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+fontSize+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+fontSize+y);
				
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontSize, fontWidth+y);
				GL11.glVertex2f(x+fontSize, y+(fontSize/4)+1);
				GL11.glVertex2f(x+fontSize-fontWidth,y+(fontSize/4)+1);
				
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize-(fontSize/4)+y);
				GL11.glVertex2f(x+fontSize, fontSize-(fontSize/4)+ y);
				GL11.glVertex2f(x+fontSize, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize+y);
				
				GL11.glEnd();
				x+=fontSize;
				break;
			case 'D':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, fontWidth+y);
				GL11.glVertex2f(x+fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontSize+y);
				GL11.glVertex2f(x+0, fontSize+y);
				
				GL11.glVertex2f(x+fontSize-(fontSize/4), fontWidth+y);
				GL11.glVertex2f(x+fontWidth+fontSize-(fontSize/4), fontWidth+y);
				GL11.glVertex2f(x+fontWidth+fontSize-(fontSize/4),fontSize+y);
				GL11.glVertex2f(x+fontSize-(fontSize/4), fontSize+y);

				GL11.glVertex2f(x+fontWidth-(fontSize/8), 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth-(fontSize/8), 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth-(fontSize/8), fontWidth+y);
				GL11.glVertex2f(x+fontWidth-(fontSize/8),fontWidth+y);
				
				GL11.glVertex2f(x+fontWidth-(fontSize/8), fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth-(fontSize/8), fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth-(fontSize/8), fontWidth+fontSize+y);
				GL11.glVertex2f(x+fontWidth-(fontSize/8),fontWidth+fontSize+y);
				
				GL11.glEnd();
				x+=fontSize;
				break;
			case 'E':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, fontWidth+y);
				GL11.glVertex2f(x+fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontSize+y);
				GL11.glVertex2f(x+0, fontSize+y);

				GL11.glVertex2f(x+fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+y);

				GL11.glVertex2f(x+fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth, (fontSize/2)+fontWidth+y);
				GL11.glVertex2f(x+fontWidth, (fontSize/2)+fontWidth+y);
				
				
				GL11.glVertex2f(x+fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontSize+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+fontSize+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+fontSize+y);
				
				GL11.glEnd();
				x+=fontSize;
				break;
			case 'F':
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x+0, fontWidth+y);
				GL11.glVertex2f(x+fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontSize+y+1);
				GL11.glVertex2f(x+0, fontSize+y+1);

				GL11.glVertex2f(x+fontWidth-1, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, 0+y);
				GL11.glVertex2f(x+fontSize-fontWidth, fontWidth+y);
				GL11.glVertex2f(x+fontWidth,fontWidth+y);

				GL11.glVertex2f(x+fontWidth/2, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth-2, fontSize/2+y);
				GL11.glVertex2f(x+fontSize-fontWidth-2, (fontSize/2)+fontWidth+y);
				GL11.glVertex2f(x+fontWidth, (fontSize/2)+fontWidth+y);
				
				GL11.glEnd();
				x+=fontSize;
				break;
			case ' ':
				GL11.glBegin(GL11.GL_QUADS);
				for(int i = 0;i<fontSize;i+=2)
				{
					GL11.glVertex2f(x+i, fontSize+y);
					GL11.glVertex2f(x+1+i, fontSize+y);
					GL11.glVertex2f(x+1+i, fontWidth+fontSize+y);
					GL11.glVertex2f(x+i,fontWidth+fontSize+y);
				}
				
				
				GL11.glEnd();
				x+=fontSize;
				break;
			}
		}
	}

	public static void drawString(String s, int x, int y, Color color) {
		GL11.glColor3f(color.r, color.g, color.b);
		int startX = x;
		GL11.glBegin(GL11.GL_POINTS);
		for (char c : s.toCharArray()) {
			if (c == 'A') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'B') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 5);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				x += 8;
			} else if (c == 'C') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'D') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'E') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'F') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'G') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 5);
				GL11.glVertex2f(x + 7, y + 5);

				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 7);

				x += 8;
			} else if (c == 'H') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == 'I') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 3, y + i);
				}
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 0);
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'J') {
				for (int i = 0; i <= 7; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);
				// GL11.glVertex2f(x + 1, y + 1);
				x += 8;
			} else if (c == 'K') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 6, y);
				GL11.glVertex2f(x + 5, y + 1);
				GL11.glVertex2f(x + 4, y + 2);
				GL11.glVertex2f(x + 3, y + 3);
				GL11.glVertex2f(x + 2, y + 4);
				GL11.glVertex2f(x + 2, y + 5);

				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 4, y + 5);
				GL11.glVertex2f(x + 5, y + 6);
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 7, y + 8);
				x += 8;
			} else if (c == 'L') {
				for (int i = 0; i < 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'M') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 4, y + 3);

				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 4, y + 3);
				x += 8;
			} else if (c == 'N') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y + 2);
				GL11.glVertex2f(x + 2, y + 2);
				GL11.glVertex2f(x + 3, y + 3);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 5, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 6);
				x += 8;
			} else if (c == 'O') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'P') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 6, y + 2);
				GL11.glVertex2f(x + 6, y + 3);
				x += 8;
			} else if (c == 'Q') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					if (i != 7)
						GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					if (i != 6)
						GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 4, y + 5);
				GL11.glVertex2f(x + 5, y + 6);
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 7, y + 8);
				x += 8;
			} else if (c == 'r') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 6, y + 6);

				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 5, y + 2);
				GL11.glVertex2f(x + 6, y + 1);
				GL11.glVertex2f(x + 7, y);
				x += 8;
			} else if (c == 's') {
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);
				GL11.glVertex2f(x + 1, y + 5);
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y);
				}
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 1, y + 2);
				x += 8;
			} else if (c == 't') {
				for (int i = 0; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				x += 7;
			} else if (c == 'u') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 0);
				}
				x += 8;
			} else if (c == 'v') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 6, y + i);
				}
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 5, y + 1);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				x += 7;
			} else if (c == 'w') {
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == 'x') {
				for (int i = 1; i <= 7; i++)
					GL11.glVertex2f(x + i, y + i);
				for (int i = 7; i >= 1; i--)
					GL11.glVertex2f(x + i, y + 8 - i);
				x += 8;
			} else if (c == 'y') {
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 4, y + 1);
				GL11.glVertex2f(x + 4, y + 2);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 4, y + 4);

				GL11.glVertex2f(x + 3, y + 5);
				GL11.glVertex2f(x + 2, y + 6);
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 8);

				GL11.glVertex2f(x + 5, y + 5);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 8);
				x += 8;
			} else if (c == 'z') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + i);
				}
				GL11.glVertex2f(x + 6, y + 7);
				x += 8;
			} else if (c == '1') {
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 8; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				GL11.glVertex2f(x + 3, y + 7);
				x += 8;
			} else if (c == '2') {
				for (int i = 1; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 1, y + 7);
				GL11.glVertex2f(x + 1, y + 6);

				GL11.glVertex2f(x + 6, y + 7);
				GL11.glVertex2f(x + 6, y + 6);
				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				x += 8;
			} else if (c == '3') {
				for (int i = 1; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 6, y + i);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '4') {
				for (int i = 2; i <= 8; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 1);
				}
				for (int i = 0; i <= 4; i++) {
					GL11.glVertex2f(x + 4, y + i);
				}
				x += 8;
			} else if (c == '5') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + i, y + 8);
				}
				for (int i = 4; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				GL11.glVertex2f(x + 1, y + 1);
				GL11.glVertex2f(x + 2, y);
				GL11.glVertex2f(x + 3, y);
				GL11.glVertex2f(x + 4, y);
				GL11.glVertex2f(x + 5, y);
				GL11.glVertex2f(x + 6, y);

				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);

				GL11.glVertex2f(x + 6, y + 4);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 4);
				GL11.glVertex2f(x + 3, y + 4);
				GL11.glVertex2f(x + 2, y + 4);
				x += 8;
			} else if (c == '6') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y);
				}
				for (int i = 2; i <= 5; i++) {
					GL11.glVertex2f(x + i, y + 4);
					GL11.glVertex2f(x + i, y + 8);
				}
				GL11.glVertex2f(x + 7, y + 1);
				GL11.glVertex2f(x + 7, y + 2);
				GL11.glVertex2f(x + 7, y + 3);
				GL11.glVertex2f(x + 6, y + 4);
				x += 8;
			} else if (c == '7') {
				for (int i = 0; i <= 7; i++)
					GL11.glVertex2f(x + i, y + 8);
				GL11.glVertex2f(x + 7, y + 7);
				GL11.glVertex2f(x + 7, y + 6);

				GL11.glVertex2f(x + 6, y + 5);
				GL11.glVertex2f(x + 5, y + 4);
				GL11.glVertex2f(x + 4, y + 3);
				GL11.glVertex2f(x + 3, y + 2);
				GL11.glVertex2f(x + 2, y + 1);
				GL11.glVertex2f(x + 1, y);
				x += 8;
			} else if (c == '8') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				x += 8;
			} else if (c == '9') {
				for (int i = 1; i <= 7; i++) {
					GL11.glVertex2f(x + 7, y + i);
				}
				for (int i = 5; i <= 7; i++) {
					GL11.glVertex2f(x + 1, y + i);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + i, y + 0);
				}
				for (int i = 2; i <= 6; i++) {
					GL11.glVertex2f(x + i, y + 4);
				}
				GL11.glVertex2f(x + 1, y + 0);
				x += 8;
			} else if (c == '.') {
				GL11.glVertex2f(x + 1, y);
				x += 2;
			} else if (c == ',') {
				GL11.glVertex2f(x + 1, y);
				GL11.glVertex2f(x + 1, y + 1);
				x += 2;
			} else if (c == '\n') {
				y -= 10;
				x = startX;
			} else if (c == ' ') {
				x += 8;
			}
		}
		GL11.glEnd();
	}

	TrueTypeFont font;
	boolean debug = false;

	public void Update(int delta, Point mousePosition) {

		// System.out.println("X:" + mousePosition.getX() + " Y:" +
		// mousePosition.getY());

	}
}
