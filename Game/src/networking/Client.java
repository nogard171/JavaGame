package networking;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import objects.Action;
import objects.Direction;
import objects.Object;
import objects.Player;
import objects.Type;
import util.*;

public class Client extends Thread {
	public void disconnect() {

	}

	public String username = "";
	public boolean connected = false;
	public Socket client = null;
	String serverName = "localhost";
	boolean logged = false;
	int port = 2222;
	private ImageLoader imgLoader;

	public void run() {

		imgLoader = new ImageLoader();
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
				 System.out.println(command);
					if (command.startsWith("player:")) {
						String user = command.substring(
								command.indexOf(':') + 1, command.length());
						Locker.recieveLine = user + " has logged in.";
						if (!playerExist(user) && !user.equals(this.username)) {
							Locker.recieveLine = user + " added to players";
							Player player = new Player();
							player.networked = true;
							player.setTexture(imgLoader
									.getImageFromResources("\\resources\\images\\playerset.png"));
							player.setName(user);
							Locker.players.add(player);
						}
					}
					if (command.startsWith("map:")) {
						String[] mapData = command.substring(
								command.indexOf(':') + 1, command.length())
								.split("~");
						String[] tiles = mapData[1].split(";");
						for (int i = 0; i < tiles.length; i++) {
							String[] tileData = tiles[i].split(",");
							Object obj = new Object();
							obj.index = i;
							obj.lowerType = Type.parse(tileData[0]);
							obj.upperType = Type.parse(tileData[1]);
							obj.setTexture(Type.getTexture(Locker.map.texture,
									obj.upperType), Type.getTexture(
									Locker.map.texture, obj.lowerType));
							obj.isVisible = Boolean.parseBoolean(tileData[2]);
							obj.passable = Boolean.parseBoolean(tileData[3]);
							obj.harvested = Boolean.parseBoolean(tileData[4]);
							obj.bounds = new Rectangle(
									Integer.parseInt(tileData[5]),
									Integer.parseInt(tileData[6]),
									Integer.parseInt(tileData[7]),
									Integer.parseInt(tileData[8]));
							obj.upperBounds = new Rectangle(
									Integer.parseInt(tileData[9]),
									Integer.parseInt(tileData[10]),
									Integer.parseInt(tileData[11]),
									Integer.parseInt(tileData[12]));
							Locker.map.tiles.set(i, obj);
						}
						String[] objects = mapData[2].split(";");
						for (int i = 0; i < objects.length; i++) {
							String[] tileData = objects[i].split(",");
							Object obj = new Object();
							obj.index = i;
							obj.lowerType = Type.parse(tileData[0]);
							obj.upperType = Type.parse(tileData[1]);
							obj.setTexture(Type.getTexture(Locker.map.texture,
									obj.upperType), Type.getTexture(
									Locker.map.texture, obj.lowerType));
							obj.isVisible = Boolean.parseBoolean(tileData[2]);
							obj.passable = Boolean.parseBoolean(tileData[3]);
							obj.harvested = Boolean.parseBoolean(tileData[4]);
							obj.bounds = new Rectangle(
									Integer.parseInt(tileData[5]),
									Integer.parseInt(tileData[6]),
									Integer.parseInt(tileData[7]),
									Integer.parseInt(tileData[8]));
							obj.upperBounds = new Rectangle(
									Integer.parseInt(tileData[9]),
									Integer.parseInt(tileData[10]),
									Integer.parseInt(tileData[11]),
									Integer.parseInt(tileData[12]));
							Locker.map.arrayObjects.set(i, obj);
						}
					}
					if (command.startsWith("harvest:")) {
						String user = command.substring(command.indexOf(':'),
								command.length());
						int index = Integer.parseInt(command.substring(
								command.indexOf(',') + 1, command.length()));
						Locker.map.arrayObjects.get(index).harvested = true;
					}
					if (command.startsWith("message:")) {
						String user = command.substring(
								command.indexOf(':') + 1, command.indexOf(','));
						String message = command.substring(
								command.indexOf(',') + 1, command.length());
						System.out.println(user + ": " + message);
						Locker.recieveLine = user + ":" + message;
					}
					if (command.startsWith("attack:")) {
						String[] data = command.substring(
								command.indexOf(':') + 1, command.length())
								.split(",");
						System.out.println(data[1].equals(Locker.player.getName()));
						if(data[1].equals(Locker.player.getName()))
						{
							Locker.player.takeDamge(Integer.parseInt(data[2]));
						if(Locker.player.getHealth()<=0)
						{
							Locker.player.setHealth(0, (float) Locker.player.getMaxHealth());
						}
						}
					}
					if (command.startsWith("move:")) {
						String user = command.substring(
								command.indexOf(':') + 1, command.indexOf(','));
						String[] data = command.substring(
								command.indexOf(':') + 1, command.length())
								.split(",");
						if (getPlayerIndex(user) >= 0) {
							Player player = Locker.players
									.get(getPlayerIndex(user));
							player.setPosition(Integer.parseInt(data[1]),
									Integer.parseInt(data[2]));
							player.moving = Boolean.parseBoolean(data[3]);
							player.direction = Direction.parseDirection(data[4]);
							player.action = Action.parseAction(data[5]);
							player.setHealth(Float.parseFloat(data[6]),
									Float.parseFloat(data[7]));
							player.setMana(Float.parseFloat(data[8]),
									Float.parseFloat(data[9]));
							player.weaponBounds = new Rectangle(Integer.parseInt(data[10]),Integer.parseInt(data[11]),10,10);
						}

						// System.out.println("player moving: " + data[0]);
					}
					if (command.startsWith("remove:")) {
						String user = command.substring(
								command.indexOf(':') + 1, command.length());
						if (user != username && getPlayerIndex(user) != -1) {
							Locker.players.remove(getPlayerIndex(user));
						}
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

	public boolean playerExist(String username) {
		for (int i = 0; i < Locker.players.size(); i++) {
			if (Locker.players.get(i).getName().toLowerCase()
					.equals(username.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public int getPlayerIndex(String username) {
		for (int i = 0; i < Locker.players.size(); i++) {
			if (Locker.players.get(i).getName().toLowerCase()
					.equals(username.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}

	public static String getInput(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		String text = in.readUTF();
		// in.close();
		return text;
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
