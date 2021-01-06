package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import utils.FPS;

public class PlayerController {

	public static void update() {
		float speed = FPS.getDelta() * 0.15f;
		Vector2f velocity = new Vector2f(0, 0);
		if (Input.isKeyDown(Keyboard.KEY_A)) {
			velocity.x = -speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_W)) {
			velocity.y = -speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_S)) {
			velocity.y = speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_D)) {
			velocity.x = speed;
		}
		GameData.player.move(velocity);
	}
}
