import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Text {
	int[][][] alphabet = {};
	Map<String, int[][]> alphabet2 = new HashMap<String, int[][]>();

	public Text() {

		int[][] A = { 
				{ 0, 1, 1, 1, 1, 1, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 } };
		alphabet2.put("A", A);
		int[][] a = { 
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 1, 1, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 1 }, 
				{ 0, 0, 1, 1, 1, 1, 1 }, 
				{ 0, 1, 0, 0, 0, 0, 1 }, 
				{ 0, 1, 0, 0, 0, 0, 1 }, 
				{ 0, 1, 0, 0, 0, 1, 1 }, 
				{ 0, 0, 1, 1, 1, 0, 1 } };
		alphabet2.put("a", a);
		int[][] B = { 
				{ 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("B", B);
		int[][] b = { 
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("b", b);
		int[][] C = { 
				{ 0, 1, 1, 1, 1, 1, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("C", C);
		int[][] c = { 
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("c", c);
		int[][] D = { 
				{ 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("D", D);
		int[][] d = { 
				{ 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 } };
		alphabet2.put("d", d);
		int[][] E = { 
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1 } };
		alphabet2.put("E", E);
		int[][] e = { 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 0, 0 },
				{ 1, 0, 0, 0, 0, 1, 0 },
				{ 1, 0, 0, 0, 0, 1, 0 },
				{ 1, 1, 1, 1, 1, 1, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 0, 1, 1, 1, 0, 0 } };
		alphabet2.put("e", e);
	}

	public void Render(String s, int x, int y,float fontSize, Color color) {
		y += 3;
		x += 2;
		GL11.glPushMatrix();
		GL11.glColor3f(color.r, color.g, color.b);
		int spacing = 8;
		fontSize = 1/(8/fontSize);
		System.out.println(fontSize);
		for (char c : s.toCharArray()) {
			String character = c + "";
			GL11.glBegin(GL11.GL_QUADS);
			int[][] characterData = alphabet2.get(character);
			if (characterData != null) {
				for (int i = 0; i < characterData.length; i++) {
					for (int j = 0; j < characterData[i].length; j++) {
						if (characterData[i][j] == 1) {
							GL11.glVertex2f((x*fontSize) + (j*fontSize), (y*fontSize) + (i*fontSize));
							GL11.glVertex2f((x*fontSize) + (j*fontSize) + (1*fontSize), (y*fontSize) + (i*fontSize));
							GL11.glVertex2f((x*fontSize) + (j*fontSize) + (1*fontSize), (y*fontSize) + (i*fontSize) + (1*fontSize));
							GL11.glVertex2f((x*fontSize) + (j*fontSize), (y*fontSize) + (i*fontSize) + (1*fontSize));
						}
					}
				}
			}
			x += spacing;
			GL11.glEnd();
		}
		GL11.glPopMatrix();
	}

}
