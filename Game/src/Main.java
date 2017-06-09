import java.util.Scanner;

import network.GLClient;
import network.GLData;
import network.GLMessage;
import network.GLProtocol;
import network.GLServer;

public class Main {
	
	public static void main(String[] args)
	{		
		//new Game().Start();	
		
		GLServer server = new GLServer();
		GLClient client = new GLClient();
		
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
	                }else if (input.startsWith("broadcast:")) {
	                	
	                	GLMessage message= new GLMessage();
	                	message.protocol = GLProtocol.MESSAGE;
	                	message.from = "server";
	                	message.message = input.split(":")[1];
	                	server.broadcastGLData((GLData)message);
	                }
	                else if (input.startsWith("message:")) {
	                	GLMessage message= new GLMessage();
	                	message.protocol = GLProtocol.MESSAGE;
	                	message.from = "client";
	                	message.message = input.split(":")[1];
	                	client.sendGLData((GLData) message);
	                }
	            }
	        }
	        keyboard.close();
	}
}