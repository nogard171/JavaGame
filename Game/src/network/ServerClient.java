package network;


import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import objects.Player;

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
        	
			while (true) {
				String command = getInput(clientSocket);
				//System.out.println(command);
				
				if(command.startsWith("shutdown:"))
				{
					break;
				}
				if(command.startsWith("message:"))
				{
					String message = command.substring(command.indexOf(':')+1,command.length());
					//System.out.println(username + " has said: " +  message);
				}	
				if(command.startsWith("player:"))
	    		{
	    			String user = command.substring(command.indexOf(':')+1,command.length());
	    			Locker.recieveLine = user + " has logged in.";
	    			if(!playerExist(user)&&!user.equals(this.username))
	    			{
	    				Locker.recieveLine = user +" added to players";
	    				Player player = new Player();
	    				player.setName(user);
	    				Locker.players.add(player);	
	    			}
	    		}
				if(command.startsWith("move:"))
				{
					String[] data = command.substring(command.indexOf(':')+1,command.length()).split(",");
					//System.out.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]);
					moveChara(data[0],Boolean.parseBoolean(data[1]),Boolean.parseBoolean(data[2]),Boolean.parseBoolean(data[3]),Boolean.parseBoolean(data[4]));
					
					//if(data[0].equals(username))
					//{
						broadcast("chara:" + data[0]+","+player.position.x+","+player.position.y);
					/*}
					else
					{
						sendTo(data[0],"chara:" + data[0]+","+player.position.x+","+player.position.y);
					}*/
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
	public boolean playerExist(String username)
	   {
		   for(int i=0;i<Locker.players.size();i++)
		   {
			   if(Locker.players.get(i).getName().toLowerCase().equals(username.toLowerCase())){
				   return true;
			   }
		   }
		   return false;
	   }
	   public int getPlayerIndex(String username)
	   {
		   for(int i=0;i<Locker.players.size();i++)
		   {
			   if(Locker.players.get(i).getName().toLowerCase().equals(username.toLowerCase())){
				   return i;
			   }
		   }
		   return -1;
	   }
	private void moveChara(String string, boolean b, boolean c, boolean d, boolean e) {
		// TODO Auto-generated method stub
		if(string.equals(username))
		{
			if(b)
			{
				player.position.x-=5;
			}
			if(c)
			{
				player.position.x+=5;
			}
			if(d)
			{
				player.position.y-=5;
			}
			if(e)
			{
				player.position.y+=5;
			}
		}
	}
	public void sendTo(String user,String command)
	{
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientSocket != null&&threads[i].username.equals(user)) {
                try {
					threads[i].sendMessage(threads[i].clientSocket,command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
            }
          
	}
	Player player = new Player();
	public void syncBroadcast(String command)
	{
		synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientSocket != null) {
                try {
					threads[i].sendMessage(threads[i].clientSocket,command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
            }
          }
	}
	public void broadcast(String command)
	{
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientSocket != null) {
                try {
					threads[i].sendMessage(threads[i].clientSocket,command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
            }
          
	}
}
