package classes;

import org.lwjgl.util.vector.Vector2f;

import core.GameData;
import core.Window;

public class Player {
	private Position position = new Position(0, 0);

	public Vector2f getIndex() {
		Vector2f index = new Vector2f(0, 0);
		index.x = (int) ((position.x + (Window.getWidth() / 2) - 16) / 32);
		index.y = (int) ((position.y + (Window.getHeight() / 2) - 16) / 32);
		return index;
	}

	public Vector2f getChunkIndex() {
		Vector2f index = new Vector2f(0, 0);
		index.x = (int) Math.floor((getIndex().getX() / GameData.chunkSize.width));
		index.y = (int) Math.floor((getIndex().getY() / GameData.chunkSize.height));
		return index;
	}

	public void move(Vector2f velocity) {
		position.move(velocity);
	}

	public float getX() {
		return position.getX();
	}

	public float getY() {
		return position.getY();
	}
}
