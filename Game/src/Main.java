import java.util.Scanner;

import network.GLClient;
import network.GLData;
import network.GLMessage;
import network.GLProtocol;
import network.GLServer;
import network.SocketHandler;

public class Main {
	
	public static void main(String[] args)
	{		
		new Game().Start();	
		
		/*GLServer server = new GLServer();
		GLClient client = new GLClient();
		
		 Scanner keyboard = new Scanner(System.in);
	        boolean exit = false;
	        while (!exit) {
	        	System.out.print("#");
	            String input = keyboard.nextLine();
	            if(input != null) {
	            	System.out.println();
	                if ("exit".equals(input)) {
	                    GLMessage message= new GLMessage();
	                	message.protocol = GLProtocol.CLOSE_CONNECTION;
	                	message.from = "server";
	                	message.message = "You have been disconnected from the server.";
	                    System.out.println("Exit program");	                    
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
	                }else if (input.startsWith("close#")) {
	                	GLMessage message= new GLMessage();
	                	message.protocol = GLProtocol.CLOSE_CONNECTION;
	                	message.ClientID = Integer.parseInt(input.split("#")[1].replace(" ", ""));
	                	message.from = "server";
	                	message.message = "You have been disconnected from the server.";
	                	server.broadcastGLData((GLData)message);
	                }else if (input.startsWith("close all")) {	                	
	                	GLMessage message= new GLMessage();
	                	message.protocol = GLProtocol.CLOSE_CONNECTION;
	                	message.from = "server";
	                	message.message = "You have been disconnected from the server.";
	                	server.broadcastGLData((GLData)message);
	                }else if (input.startsWith("list")) {	    
	                	for(SocketHandler socket: server.sockets)
	                	{
	                		System.out.println("Client# " + socket.ID+" @ " + socket.getIP());
	                	}
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
	        keyboard.close();*/
	}
}