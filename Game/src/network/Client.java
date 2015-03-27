package network;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import objects.Player;

public class Client extends Thread {
	public void disconnect() {

	}

	public String username = "";
	public boolean connected = false;
	public Socket client = null;

	boolean logged = false;

	public boolean master = false;

	public void run() {

		System.out.println("Connecting to " + Locker.serverName + " on port "
				+ Locker.port);
		try {
			client = new Socket(Locker.serverName, Locker.port);
		} catch (IOException e) {

			try {
				client = new Socket(Locker.backupServerName, Locker.port);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			Locker.ipAddress = client.getRemoteSocketAddress() + "";
			if (client.isConnected()) {
				this.connected = true;

				String greetings = getInput(client);

				if (greetings.startsWith("Enter Username/p")) {
					sendMessage(client, "LOG/p" + username + "/s" + master
							+ "/s" + Locker.clientWidth + "/s"
							+ Locker.clientHeight);
					String text = getInput(client);
					if (text.contains("login")) {
						logged = true;
						System.out.println("logged in.");
					}
				}
				while (true) {
					String command = getInput(client);
					// System.out.println(command);
					if (command.startsWith("message/p")) {
						String user = command.substring(
								command.indexOf("/p") + 2,
								command.indexOf("/s"));
						String message = command.substring(
								command.indexOf("/s") + 2, command.length());
						Locker.recieveLine = user + ": " + message;
					}
					if (command.startsWith("stat/p")) {
						String[] data = command.substring(
								command.indexOf("/p") + 2, command.length())
								.split("/s");
						Locker.player.setHealth(Float.parseFloat(data[1]));
						Locker.player.setMaxHealth(Float.parseFloat(data[2]));
						Locker.player.setStamina(Float.parseFloat(data[3]));
						Locker.player.setMaxStamina(Float.parseFloat(data[4]));
					}
					if (command.startsWith("chara/p")) {
						String[] data = command.substring(
								command.indexOf("/p") + 2, command.length())
								.split("/s");
						// System.out.println(Locker.username + "/" + data[0]);
						if (Locker.username.toLowerCase().equals(data[0])) {
							Locker.player.setPosition(
									Float.parseFloat(data[1]),
									Float.parseFloat(data[2]));
							Locker.player.frameX = Float.parseFloat(data[3]);
							Locker.player.frameY = Float.parseFloat(data[4]);
						} else {
							Locker.players.get(this.getPlayerIndex(data[0]))
									.setPosition(Float.parseFloat(data[1]),
											Float.parseFloat(data[2]));
							Locker.players.get(this.getPlayerIndex(data[0])).frameX = Float
									.parseFloat(data[3]);
							Locker.players.get(this.getPlayerIndex(data[0])).frameY = Float
									.parseFloat(data[4]);
						}
					}
					if (command.startsWith("player/p")) {
						String user = command.substring(
								command.indexOf("/p") + 2, command.length());
						Locker.recieveLine = user + " has logged in.";
						if (!user.equals(Locker.username)) {
							Locker.recieveLine = user + " has logged in.";
							Player player = new Player();
							player.setName(user);
							Locker.players.add(player);
						}
					}
					if (command.startsWith("attack/p")) {
						String[] data = command.substring(
								command.indexOf("/p") + 2, command.length())
								.split("/s");

						System.out.println(data[0] + " has attacked you");
						Locker.player.minusHealth(Double.parseDouble(data[2]));
					}
					if (command.startsWith("dead/p")) {
						String[] data = command.substring(
								command.indexOf("/p") + 2, command.length())
								.split("/s");
						System.out.println(data[1] + "/" + username);
						if (data[1] == this.username) {
							System.out.println(data[0] + " has killed you");
							Locker.player.isDead = true;
						} else {
							System.out.println(data[0] + " has killed "
									+ data[1]);
							if (getPlayerIndex(data[1]) >= 0) {
								Locker.players.get(getPlayerIndex(data[1])).isDead = true;
							}
						}
					}
					if (command.startsWith("chat/p")) {
						String[] data = command.substring(
								command.indexOf("/p") + 2, command.length())
								.split("/s");
						Locker.recieveLine = "chat/p" + data[1] + "/s"
								+ data[2];

					}
					if (command.startsWith("remove/p")) {
						String user = command.substring(
								command.indexOf("/p") + 2, command.length());
						if (getPlayerIndex(user) >= 0) {
							Locker.players.remove(this.getPlayerIndex(user));
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

	public static String getInput(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		String text = in.readUTF();
		// in.close();
		return text;
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

	public static void sendMessage(Socket client, String text)
			throws IOException {
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF(text);
		// out.close();
	}

	public void setMaster(boolean b) {
		// TODO Auto-generated method stub
		master = true;
	}
}
