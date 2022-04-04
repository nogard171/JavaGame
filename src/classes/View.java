package classes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import utils.Window;

public class View {
	private static Rectangle bounds = new Rectangle(0, 0, 0, 0);
	private static Point viewIndex;

	public void update() {
		viewIndex = getChunkIndex();
		bounds.width = Window.getWidth();
		bounds.height = Window.getHeight();
	}

	public void move(float x, float y) {
		bounds.x += (int) x;
		bounds.y += (int) y;
	}

	public static Point getChunkIndex() {
		int cartX = (Window.getWidth() / 2) - bounds.x;
		int cartY = (Window.getHeight() / 2) - bounds.y;
		int isoX = cartX / 2 + (cartY);
		int isoY = cartY - cartX / 2;
		int indexX = (int) Math.floor((float) isoX / (float) 32);
		int indexY = (int) Math.floor((float) isoY / (float) 32);

		int chunkX = (int) Math.floor(indexX / 16);
		int chunkY = (int) Math.floor(indexY / 16);
		if (indexX < 0) {
			chunkX--;
		}
		if (indexY < 0) {
			chunkY--;
		}

		return new Point(chunkX, chunkY);
	}

	public static Point getIndex() {
		return viewIndex;
	}

	public static float getX() {
		return bounds.x;
	}

	public static float getY() {
		return bounds.y;
	}

	public void setPosition(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}
}
