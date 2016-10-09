import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public enum Type {
	UNKNOWN, BLANK, GRASS, DIRT, SAND, WATER, TREE,STONE;
	public static Type[] getTypes()
	{
		Type[] test = new Type[ Type.values().length];
		int i =0;
		for (Type type : Type.values()) {
			test[i] = type;
			i++;
		}		
		return test;
	}
	public static SpriteData getSpriteData(Type type) {
		SpriteData spriteData = new SpriteData();
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 255;
		switch (type) {
		case GRASS:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 64;
			g = 128;
			b = 64;// 64, 128, 64
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		case STONE:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 128;
			g = 128;
			b = 128;// 64, 128, 64
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		case BLANK:
			spriteData.colors = new Color[spriteData.faces.length];
			a = 0;
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b, a);
			}
			return spriteData;
		case UNKNOWN:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 64;
			g = 64;
			b = 64;			
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
			}
			return spriteData;
		case DIRT:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 97;
			g = 63;
			b = 16;// 97, 63, 16
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		case SAND:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 238;
			g = 214;
			b = 175;// 97, 63, 16
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		case WATER:
			spriteData.colors = new Color[spriteData.faces.length];
			r = 57;
			g = 88;
			b = 121;
			a = 224;
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b, a);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		case TREE:
			spriteData.colors = new Color[spriteData.faces.length];
			
			Vector2f[] vertex = { 
					//0
					new Vector2f(1f,-2.5f),
					//1
					new Vector2f(2f,0f),
					//2
					new Vector2f(1f,0.5f),
					//3
					new Vector2f(0f,0f),
					//4
					new Vector2f(0f,0.75f),
					//5
					new Vector2f(1f,1.25f),
					//6
					new Vector2f(2f,0.75f),
					//7
					new Vector2f(0.5f,0.25f),
					//8
					new Vector2f(0.5f,0.5f),
					//9
					new Vector2f(1f,0.75f),
					//10
					new Vector2f(1.5f,0.25f),
					//11
					new Vector2f(1.5f,0.5f)
					};
			int[][] faces = { { 0,1,2}, { 0, 3, 2 },{7,8,9},{7,2,9},{2,10,11},{2,11,9}
					};
			spriteData.vertex = vertex;
			spriteData.faces = faces;
			r = 64;
			g = 128;
			b = 64;// 64, 128, 64
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b, a);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			r = 64;
			g = 128;
			b = 64;// 64, 128, 64
			spriteData.colors[0] = new Color(r-32, g-32, b-32, a);
			spriteData.colors[1] = new Color(r-16, g-16, b-16, a);
			
			r = 97;
			g = 63;
			b = 16;// 97, 63, 16
			for (int c = 2; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b, a);
				if ((c % 2) != 0) {
					r -= 16;
					g -= 16;
					b -= 16;
				}
			}
			return spriteData;
		default:
			return new SpriteData();
		}
	}
}

