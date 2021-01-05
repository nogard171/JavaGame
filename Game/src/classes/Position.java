package classes;

import org.lwjgl.util.vector.Vector2f;

public class Position extends Vector2f {

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(Vector2f velocity) {
		if (velocity.x != 0 || velocity.y != 0) {
			this.x += velocity.x;
			this.y += velocity.y;
		}
	}
}
