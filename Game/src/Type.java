import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public enum Type {
	BLANK, GRASS, DIRT, SAND, WATER, TREE;
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
		case BLANK:
			spriteData.colors = new Color[spriteData.faces.length];
			a = 0;
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b, a);
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
			spriteData.shadow = false;
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
			r = 103;
			g = 72;
			b = 24;// 97, 63, 16
			spriteData.vertex[0] = new Vector2f(1f, 0.25f);
			spriteData.vertex[1] = new Vector2f(1.5f, 0.5f);
			spriteData.vertex[2] = new Vector2f(1f, 0.75f);
			spriteData.vertex[3] = new Vector2f(0.5f, 0.5f);
			spriteData.vertex[4] = new Vector2f(0.5f, -0.5f);
			spriteData.vertex[5] = new Vector2f(1f, -0.75f);
			spriteData.vertex[6] = new Vector2f(1.5f, -0.5f);

			spriteData.shadowVertex[0] = new Vector2f(1.5f, 0.5f);
			spriteData.shadowVertex[1] = new Vector2f(2.5f, 0f);
			spriteData.shadowVertex[2] = new Vector2f(1.5f, -0.5f);
			for (int c = 0; c < spriteData.colors.length; c++) {
				spriteData.colors[c] = new Color(r, g, b);
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
