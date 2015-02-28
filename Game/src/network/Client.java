package network;


import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import objects.Player;



public class Client extends Thread {
	public void disconnect() {

	}

	public String username = "";
	public boolean connected = false;
	public Socket client = null;
	String serverName = "204.237.93.81";
	boolean logged = false;
	int port = 80;

	public void run() {

		try {
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			client = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			if (client.isConnected()) {
				this.connected = true;

				String greetings = getInput(client);

				if (greetings.startsWith("Enter Username")) {
					System.out.println(greetings);

					sendMessage(client, "LOG:" + username + "," + master);
					String text = getInput(client);
					if (text.contains("login")) {
						logged = true;
						System.out.println("logged in.");
					}
				}
				while (true) {
					String command = getInput(client);
					//System.out.println(command);
					if (command.startsWith("message:")) {
						String user = command.substring(
								command.indexOf(':') + 1, command.indexOf(','));
						String message = command.substring(
								command.indexOf(',') + 1, command.length());
						System.out.println(user + ": " + message);
						Locker.recieveLine = user + ":" + message;
					}
					if (command.startsWith("chara:")) {
						String[] data = command.substring(command.indexOf(':')+1,command.length()).split(",");
						if(Locker.username.toLowerCase().equals(data[0]))
						{
							System.out.println("moving player");
							Locker.player.setPosition(new Point(Integer.parseInt(data[1]),Integer.parseInt(data[2])));
						}
						else
						{
							System.out.println("moving another player");
							Locker.players.get(this.getPlayerIndex(data[0])).setPosition(new Point(Integer.parseInt(data[1]),Integer.parseInt(data[2])));
						}
					}
					if(command.startsWith("player:"))
		    		{
		    			String user = command.substring(command.indexOf(':')+1,command.length());
		    			Locker.recieveLine = user + " has logged in.";
		    			if(!user.equals(Locker.username))
		    			{
		    				Locker.recieveLine = user +" added to players";
		    				Player player = new Player();
		    				player.setName(user);
		    				Locker.players.add(player);	
		    			}
		    		}
					if(command.startsWith("remove:"))
		    		{
		    			String user = command.substring(command.indexOf(':')+1,command.length());
		    			Locker.players.remove(this.getPlayerIndex(user));
		    		}
				}
			}
		} catch (IOException e) {

			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	

	public static String getInput(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		String text = in.readUTF();
		// in.close();
		return text;
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
	public static void sendMessage(Socket client, String text)
			throws IOException {
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF(text);
		// out.close();
	}

	boolean master = false;

	public void setMaster(boolean b) {
		// TODO Auto-generated method stub
		master = true;
	}
}
