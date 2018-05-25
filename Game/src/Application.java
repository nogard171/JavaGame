
import javax.swing.SwingUtilities;

import game.Main;

public class Application
{
	// the grid boolean, used for render things as lines.

	public static void main(final String[] argv)
	{
		final Main app = new Main();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				app.CreateWindow();
			}
		});
	}
}