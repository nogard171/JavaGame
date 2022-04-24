package classes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;

import data.EngineData;
import utils.Window;

public class View {
	private static Vector2f position = new Vector2f(0, 0);
	private static Rectangle bounds = new Rectangle(0, 0, 0, 0);
	private static Point viewIndex;

	public void update() {
		viewIndex = getChunkIndex();
		bounds.width = Window.getWidth();
		bounds.height = Window.getHeight();
	}

	public void move(float x, float y) {
		position.x += x;
		position.y += y;
	}

	public static Point getChunkIndex() {
		float cartX = (Window.getWidth() / 2) - position.x;
		float cartY = (Window.getHeight() / 2) - position.y;
		float isoX = cartX / 2 + (cartY);
		float isoY = cartY - cartX / 2;
		int indexX = (int) Math.floor((float) isoX / (float) 32);
		int indexY = (int) Math.floor((float) isoY / (float) 32);

		int chunkX = (int) Math.floor(indexX / EngineData.chunkSize.getWidth());
		int chunkY = (int) Math.floor(indexY / EngineData.chunkSize.getDepth());
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
		return position.x;
	}

	public static float getY() {
		return position.y;
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}
}
