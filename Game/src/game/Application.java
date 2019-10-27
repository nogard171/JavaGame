package game;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;

public class Application {
	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.start();
		} catch (Exception e) {
			Logger LOGGER = Logger.getLogger(e.getClass().getName());
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
}
