package game;

import org.lwjgl.LWJGLException;

public class Entry {
	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.run();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
