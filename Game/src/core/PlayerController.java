package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import utils.FPS;

public class PlayerController {

	public static void update() {
		float speed = FPS.getDelta() * 0.15f;
		Vector2f velocity = new Vector2f(0, 0);

		boolean forward = Input.isKeyDown(Keyboard.getKeyIndex(GameData.config.getProperty("control.forward")));
		boolean backward = Input.isKeyDown(Keyboard.getKeyIndex(GameData.config.getProperty("control.backward")));
		boolean straftLeft = Input.isKeyDown(Keyboard.getKeyIndex(GameData.config.getProperty("control.left")));
		boolean straftRight = Input.isKeyDown(Keyboard.getKeyIndex(GameData.config.getProperty("control.right")));

		if (straftLeft) {
			velocity.x = -speed;
		}
		if (forward) {
			velocity.y = -speed;
		}
		if (backward) {
			velocity.y = speed;
		}
		if (straftRight) {
			velocity.x = speed;
		}
		GameData.player.move(velocity);
	}
}
