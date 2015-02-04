import javax.swing.SwingUtilities;


public class Game {

	public static void main(String[] args)
	{
		 SwingUtilities.invokeLater(new Runnable() {     
	            @Override
	            public void run() {
	               //the main game as jframe
	            	System.out.println("hello world!");
	            }
	        });
		
		while(true);
	}
	
}
