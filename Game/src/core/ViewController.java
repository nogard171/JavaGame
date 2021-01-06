package core;

public class ViewController {
	public static void update() {
		GameData.view.x = (int) GameData.player.getX();
		GameData.view.y = (int) GameData.player.getY();
	}
}
