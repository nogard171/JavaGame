import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args)
	{
		final Game game = new Game();
		game.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				game.onClose();
			}
		});
		 SwingUtilities.invokeLater(new Runnable() {     
	            @Override
	            public void run() {
	               //the main game as jframe
	            	//System.out.println("hello world!");
	            	game.createAndShowGUI();
	            }
	        });
		
		while(true);
	}
	
}
