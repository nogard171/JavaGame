import java.util.Scanner;

import network.Client;
import network.Server;

public class Main {
	
	public static void main(String[] args)
	{		
		//new Game().Start();	
		
		Server server = new Server();
		Client client = new Client();
		
		 Scanner keyboard = new Scanner(System.in);
	        boolean exit = false;
	        while (!exit) {
	            String input = keyboard.nextLine();
	            if(input != null) {
	                if ("quit".equals(input)) {
	                    System.out.println("Exit programm");
	                    exit = true;
	                } else if ("server".equals(input)) {
	                	System.out.println("Starting server");
	                	
	                	server.start();
	                }else if ("client".equals(input)) {
	                	
	                	client.start();
	                	System.out.println("Starting client");
	                }
	            }
	        }
	        keyboard.close();
	}
}