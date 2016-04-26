import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args)
	{
		final Game game = new Game();
		 SwingUtilities.invokeLater(new Runnable() {     
	            private Object infoMessage="please login";
				private String titleBar="login";
				private boolean login;

	            public void run() {
	            	game.createAndShowGUI();
	            }
	        });
	}
	
}
