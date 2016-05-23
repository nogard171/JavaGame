package Objects;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Sprite {
	public BufferedImage texture = null;
	BufferedImage topTexture = null;
	int width = 0;
	int height = 0;
	public Color getColor(ObjectType objType) {
		Color color = Color.white;
		switch (objType) {
		case GRASS:
			color = Color.green;
			break;
		case DIRT:
			color = new Color(139, 69, 19);
			break;
		case PLAYER:
			color = new Color(255, 0,0);
			break;
		default:
			break;
		}
		return color;
	}
}
