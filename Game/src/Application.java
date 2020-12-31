
import javax.swing.SwingUtilities;

import threads.GameThread;

public class Application
{
	public static void main(final String[] argv)
	{
		GameThread game = new GameThread();
		game.start();
	}
}