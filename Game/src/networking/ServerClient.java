package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import objects.Object;

public class ServerClient extends Thread {

	private DataInputStream is = null;
	private PrintStream os = null;
	public Socket clientSocket = null;
	private final ServerClient[] threads;
	private int maxClientsCount;
	public String username = "";
	public int index = -1;
	boolean isOnline = false;
	boolean isServer = false;

	public ServerClient(Socket clientSocket, ServerClient[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}
	public String getInput(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		String text= in.readUTF();
		//in.close();
		return text;
	}

	public void sendMessage(Socket client, String text) throws IOException {
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF(text);
		//out.close();
	}
	public void run() {
		System.out.println("Client Connected");
		
		int maxClientsCount = this.maxClientsCount;
		ServerClient[] threads = this.threads;
		
		try {
			sendMessage(clientSocket, "Enter Username:");
			String name = getInput(clientSocket);
			System.out.print("test");
			if(name.startsWith("LOG:"))
			{
				username = name.substring(4,name.indexOf(","));
				isServer = Boolean.parseBoolean(name.substring(name.indexOf(",")+1, name.length()));
				//System.out.println(username+","+name.substring(name.indexOf(",")+1, name.length()));
				this.isOnline = true;
			}
			sendMessage(clientSocket, "login");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 synchronized (this) {
		            for (int i = 0; i < maxClientsCount; i++) {
		              if (threads[i] != null && threads[i].clientSocket != null) {
		                threads[i].sendMessage(threads[i].clientSocket,"player:"+username);
		              }
		            }
		          }
			
			//System.out.println(name.substring(4,name.length()) + " entered the world.");
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientSocket != null) {
               sendMessage(clientSocket,"player:" + threads[i].username);
              }
            }
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(isServer)
            {
            	sendMessage(clientSocket,"message:System,Hello again Server.");
            }else
            {
            	sendMessage(clientSocket,"message:System,Welcome to the world.");
            }
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	String mapData = Locker.map.title + "~";
			for (Object tile : Locker.map.tiles) {
				mapData += tile.lowerType + "," + tile.upperType + ","
						+ tile.isVisible + ","+ tile.passable + "," + tile.harvested + "," + tile.bounds.x + ","
						+ tile.bounds.y + "," + tile.bounds.width + ","
						+ tile.bounds.height + "," + tile.upperBounds.x
						+ "," + tile.upperBounds.y + ","
						+ tile.upperBounds.width + ","
						+ tile.upperBounds.height+";";

			}
			mapData +="~";
			for (Object tile : Locker.map.arrayObjects) {
				mapData += tile.lowerType + "," + tile.upperType + ","
						+ tile.isVisible + ","+ tile.passable + "," + tile.harvested + ","+ tile.bounds.x + ","
						+ tile.bounds.y + "," + tile.bounds.width + ","
						+ tile.bounds.height + "," + tile.upperBounds.x
						+ "," + tile.upperBounds.y + ","
						+ tile.upperBounds.width + ","
						+ tile.upperBounds.height+";";
			}       	
        	
        	sendMessage(clientSocket,"map:"+mapData);
			while (true) {
				String command = getInput(clientSocket);
				System.out.println(command);
				synchronized (this) {
		            for (int i = 0; i < maxClientsCount; i++) {
		              if (threads[i] != null && threads[i].clientSocket != null) {
		                threads[i].sendMessage(threads[i].clientSocket,command);
		              }
		            }
		          }
				if(command.startsWith("shutdown:"))
				{
					break;
				}
				if(command.startsWith("message:"))
				{
					String message = command.substring(command.indexOf(':')+1,command.length());
					System.out.println(username + " has said: " +  message);
				}
				if(command.startsWith("harvest:"))
				{
					String user = command.substring(command.indexOf(':'),command.length());
					int index = Integer.parseInt(command.substring(command.indexOf(',')+1,command.length()));
					System.out.println(Locker.map.arrayObjects.get(index).lowerType);
				}
				if(command.startsWith("name:"))
				{
					String message = command.substring(command.indexOf(':'),command.indexOf(',')-1);
					username = message;
				}				
			}
			sendMessage(clientSocket,"Bye " + name);
		
			for (int i = 0; i < maxClientsCount; i++) {
				if (threads[i] == this) {
					threads[i] = null;
				}
			}
			Locker.recieveLine = name + " has exited the world.";
			
			/*
			 * Close the output stream, close the input stream, close the
			 * socket.
			 */
			//sendMessage(clientSocket, "OK");
			for (int i = 0; i < maxClientsCount; i++) {
	              if (threads[i] != null && threads[i].clientSocket != null&&threads[i].username!=this.username) {
	                threads[i].sendMessage(threads[i].clientSocket,"remove:"+username);
	              }
	            }
			clientSocket = null;
	    	Locker.recieveLine = username + " has exited the world.";
	    	System.out.println(username + " has exited the world.");
	    	clientSocket.close();
			  
		} catch (IOException e) {
		    try
		    {
		            for (int i = 0; i < maxClientsCount; i++) {
		              if (threads[i] != null && threads[i].clientSocket != null&&threads[i].username!=this.username) {
		                threads[i].sendMessage(threads[i].clientSocket,"remove:"+username);
		              }
		            }
		    	
		    	Locker.recieveLine = username + " has exited the world.";
		    	System.out.println(username + " has exited the world.");
		    	clientSocket.close();
		    	clientSocket = null;
		    }
		    catch (IOException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
			
		}
	}
	
}
