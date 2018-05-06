import java.awt.Point;

public class GLMath {

	public static float textureXVectorToFloat(int value) {
		return (float) value / (float) new GLDataHub().texture.getImageWidth();
	}

	public static float textureYVectorToFloat(int value) {
		return (float) value / (float) new GLDataHub().texture.getImageHeight();
	}

	public static float textureXVectorToFloat(float value) {
		return textureXVectorToFloat((int) value);
	}

	public static float textureYVectorToFloat(float value) {
		return textureYVectorToFloat((int) value);
	}

	public boolean polygonContainsPoint(Point[] points, Point point) {
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = points.length - 1; i < points.length; j = i++) {
			if ((points[i].y > point.y) != (points[j].y > point.y)
					&& (point.x < (points[j].x - points[i].x) * (point.y - points[i].y) / (points[j].y - points[i].y)
							+ points[i].x)) {
				result = !result;
			}
		}
		return result;
	}
}
