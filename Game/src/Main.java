import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import network.Locker;


public class Main {

	public static void main(String[] args)
	{
		final Game game = new Game();
		 SwingUtilities.invokeLater(new Runnable() {     
	            private Object infoMessage="please login";
				private String titleBar="login";
				private boolean login;

				@Override
	            public void run() {
	               //the main game as jframe
	            	//System.out.println("hello world!");
					String user=null;
	            	while(user==""||user==null)
	            	{
	            		user = JOptionPane.showInputDialog("enter username:");
	            		System.out.println(user);
	            		if(user==null)
	            		{
	            			System.exit(1);
	            		}
	            	}
	            	Locker.username = user;
	            	game.createAndShowGUI();
	            }
	        });
	}
	
}
