package networking;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import util.ImageLoader;
import util.*;

public class Client extends Thread
{
	public void disconnect()
	{
		
	}
	public String username = "";
	public boolean connected = false;
	 public Socket client = null;
     String serverName = "localhost";
     boolean logged = false;
     int port = 2222;
	private ImageLoader imgLoader;
	   public void run()
	   {		  
		   
		   imgLoader= new ImageLoader();
	      try
	      {
	    	  System.out.println("Connecting to " + serverName
	                  + " on port " + port);
	    	  client = new Socket(serverName, port);
	    	  System.out.println("Just connected to "
	           + client.getRemoteSocketAddress());
	    	  if(client.isConnected())
	    	  {
	    		  this.connected = true;
	    	  
	    	  String greetings = getInput(client);
	    	  
	    	  if(greetings.startsWith("Enter Username"))
	    	  {
	    		  System.out.println(greetings);
	    		  
	    		  sendMessage(client, "LOG:"+username);
	    		  String text = getInput(client);
	    		  if(text.contains("login"))
	    		  {
	    			  logged = true;
	    			  System.out.println("logged in.");
	    		  }
	    	  }
	    	  while(true)
	    	  {
	    		String command = getInput(client);
	    		System.out.println(command);
	    		if(command.startsWith("player:"))
	    		{
	    			String user = command.substring(command.indexOf(':')+1,command.length());
	    			Locker.recieveLine = user + " has logged in.";
	    			if(!playerExist(user)&&!user.equals(this.username))
	    			{
	    				Locker.recieveLine = user +" added to players";
	    				/*Player player = new Player();
	    				player.networked = true;
	    				player.setTexture(imgLoader.getImageFromResources("\\resources\\images\\playerset.png"));
	    				player.setName(user);
	    				Locker.players.add(player);	*/
	    			}
	    		}
	    		if(command.startsWith("message:"))
	    		{
	    			String user = command.substring(command.indexOf(':')+1,command.indexOf(','));
	    			String message = command.substring(command.indexOf(',')+1,command.length());
					System.out.println(username + " has said: " +  message);
					Locker.recieveLine = user+":"+message;
	    		}
	    		
	    		if(command.startsWith("move:"))
	    		{
	    			String user = command.substring(command.indexOf(':')+1,command.indexOf(','));
	    			String[] data = command.substring(command.indexOf(':')+1,command.length()).split(",");
	    			if(getPlayerIndex(user)>=0)
	    			{
	    				/*Player player = Locker.players.get(getPlayerIndex(user));
	    				player.setPosition(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
	    				//player.setFramePoints(Integer.parseInt(data[3]), Integer.parseInt(data[4]));
	    				player.moving = Boolean.parseBoolean(data[3]);
	    				player.direction = Direction.pareDirection(data[4]);
	    				player.action = Integer.parseInt(data[5]);
	    				player.setHealth(Float.parseFloat(data[6]),Float.parseFloat(data[7]));
	    				player.setMana(Float.parseFloat(data[8]),Float.parseFloat(data[9]));*/
	    			}
	    			System.out.println("player moving"+data[0]+data[1]);
	    		}
	    		if(command.startsWith("remove:"))
	    		{
	    			String user = command.substring(command.indexOf(':')+1,command.length());
	    			//Locker.players.remove(getPlayerIndex(user));
	    		}
	    	  }
	    	  }
	      }catch(IOException e)
	      {
	    	  
	    	  try {
	    		  client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         e.printStackTrace();
	      }
	   } 
	   public boolean playerExist(String username)
	   {
		   /*for(int i=0;i<Locker.players.size();i++)
		   {
			   if(Locker.players.get(i).getName().toLowerCase().equals(username.toLowerCase())){
				   return true;
			   }
		   }*/
		   return false;
	   }
	   public int getPlayerIndex(String username)
	   {
		/*   for(int i=0;i<Locker.players.size();i++)
		   {
			   if(Locker.players.get(i).getName().toLowerCase().equals(username.toLowerCase())){
				   return i;
			   }
		   }*/
		   return -1;
	   }
	   public static String getInput(Socket client) throws IOException {
			DataInputStream in = new DataInputStream(client.getInputStream());
			String text= in.readUTF();
			//in.close();
			return text;
		}

		public static void sendMessage(Socket client, String text) throws IOException {
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF(text);
			//out.close();
		}
}
