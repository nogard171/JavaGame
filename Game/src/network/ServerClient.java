package network;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.Timer;

import objects.Direction;
import objects.Player;
import util.FrameRate;

public class ServerClient extends Thread implements ActionListener {

	private DataInputStream is = null;
	private PrintStream os = null;
	public Socket clientSocket = null;
	private final ServerClient[] threads;
	private int maxClientsCount;
	public String username = "";
	public int index = -1;
	boolean isOnline = false;
	boolean isServer = false;
	private long curTime;
	private Object nsPerFrame;
	private long lastTime;

	public ServerClient(Socket clientSocket, ServerClient[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public String getInput(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		String text = in.readUTF();
		// in.close();
		return text;
	}

	public void sendMessage(Socket client, String text) throws IOException {
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF(text);
		// out.close();
	}

	public void run() {
		System.out.println("Client Connected");

		int maxClientsCount = this.maxClientsCount;
		ServerClient[] threads = this.threads;
		try {
			sendMessage(clientSocket, "Enter Username/p");
			String name = getInput(clientSocket);
			if (name.startsWith("LOG/p")) {
				String[] data = name.substring(name.indexOf("/p") + 2,
						name.length()).split("/s");
				username = data[0];
				isServer = Boolean.parseBoolean(data[1]);
				clientWidth = Integer.parseInt(data[2]);
				clientHeight = Integer.parseInt(data[3]);
				// name.length()));
				this.isOnline = true;
				player.setName(username);
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
						threads[i].sendMessage(threads[i].clientSocket,
								"player/p" + username);
					}
				}
			}

			// System.out.println(name.substring(4,name.length()) +
			// " entered the world.");
			for (int i = 0; i < maxClientsCount; i++) {
				if (threads[i] != null && threads[i].clientSocket != null
						&& threads[i].username != username) {
					System.out.println(threads[i].username);
					sendMessage(clientSocket, "player/p" + threads[i].username);
				}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isServer) {
				sendMessage(clientSocket,
						"message/pSystem/sHello again Server.");
			} else {
				sendMessage(clientSocket,
						"message/pSystem/sWelcome to the world.");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Timer timer = new Timer(10, this);
			timer.start();
			while (true) {
				String command = getInput(clientSocket);
				// System.out.println(command);
				if (command.startsWith("shutdown/p")) {
					break;
				}
				if (command.startsWith("message/p")) {
					String[] message = command.substring(
							command.indexOf("/p") + 2, command.length()).split(
							"/s");
					broadcast("message/p" + message[0] + "/s" + message[1]);
				}
				if (command.startsWith("player/p")) {
					String user = command.substring(command.indexOf("/p") + 2,
							command.length());
					Locker.recieveLine = user + " has logged in.";
					if (!playerExist(user)) {
						Locker.recieveLine = user + " added to players";
						Player player = new Player();
						player.setName(user);
						Locker.players.add(player);
					}
				}
				if (command.startsWith("dim/p")) {
					String[] data = command.substring(
							command.indexOf("/s") + 2, command.length()).split(
							"/s");
					clientWidth = Integer.parseInt(data[1]);
					clientHeight = Integer.parseInt(data[2]);
				}
				if (command.startsWith("move/p")) {
					String[] data = command.substring(
							command.indexOf("/p") + 2, command.length()).split(
							"/s");
					// System.out.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]);
					moveChara(data[0], Double.parseDouble(data[1]),
							Boolean.parseBoolean(data[2]),
							Boolean.parseBoolean(data[3]),
							Boolean.parseBoolean(data[4]),
							Boolean.parseBoolean(data[5]),
							Boolean.parseBoolean(data[6]));
					if (Boolean.parseBoolean(data[7])) {
						action();
					}

					broadcast("chara/p" + data[0] + "/s" + player.positionX
							+ "/s" + player.positionY + "/s" + player.frameX
							+ "/s" + player.frameY);
				}
			}
			sendMessage(clientSocket, "Bye " + name);

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
			// sendMessage(clientSocket, "OK");
			for (int i = 0; i < maxClientsCount; i++) {
				if (threads[i] != null && threads[i].clientSocket != null
						&& threads[i].username != this.username) {
					threads[i].sendMessage(threads[i].clientSocket, "remove/p"
							+ username);
				}
			}
			clientSocket = null;
			Locker.recieveLine = username + " has exited the world.";
			System.out.println(username + " has exited the world.");
			clientSocket.close();

		} catch (IOException e) {
			try {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i].clientSocket != null
							&& threads[i].username != this.username) {
						threads[i].sendMessage(threads[i].clientSocket,
								"remove/p" + username);
					}
				}

				Locker.recieveLine = username + " has exited the world.";
				System.out.println(username + " has exited the world.");
				clientSocket.close();
				clientSocket = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	int actionDistance = 16;

	public void action() {
		if (player.frameY == 0) {
			// System.out.println("action down");
			Player p = getPlayerAt(new Point((int) player.positionX,
					(int) player.positionY + actionDistance));
			attack(p);
		} else if (player.frameY == 1) {

			// System.out.println("action left");
			Player p = getPlayerAt(new Point((int) player.positionX
					- actionDistance, (int) player.positionY));
			attack(p);
		} else if (player.frameY == 2) {

			// System.out.println("action right");
			Player p = getPlayerAt(new Point((int) player.positionX
					+ actionDistance, (int) player.positionY));
			attack(p);
		} else if (player.frameY == 3) {

			// System.out.println("action up");
			Player p = getPlayerAt(new Point((int) player.positionX,
					(int) player.positionY - actionDistance));
			attack(p);
		}
	}

	public void attack(Player p) {
		if (p != null) {

			// System.out.println("action on player:" + p.getName());
			p.minusStamina(10);
			sendTo(p.getName(), "attack/p" + p.getName() + "/s" + 10);
			// System.out.println("player stamina:" + p.getStamina());
		}
	}

	public void actionPerformed(ActionEvent e) {
		onUpdate();
		for (int i = 0; i < Locker.players.size(); i++) {
			if (Locker.players.get(i).getName().toLowerCase()
					.equals(username.toLowerCase())) {
				System.out.println("player:" + Locker.players.get(i).getName()
						+ "/" + i);
			}
		}
		/*
		 * try { sendMessage(clientSocket, "stat/p"
		 * +player.getName()+"/s"+player.getStamina()); } catch (IOException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
	}

	public void onUpdate() {
		player.update();
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

	public Player getPlayerAt(Point position) {
		for (int i = 0; i < Locker.players.size(); i++) {
			System.out.println(Locker.players.get(i).getName());
			if (new Rectangle(position.x, position.y, 32, 32)
					.intersects(new Rectangle(
							(int) Locker.players.get(i).positionX,
							(int) Locker.players.get(i).positionY, 32, 32))) {
				return Locker.players.get(i);
			}
		}
		if (new Rectangle(position.x, position.y, 32, 32)
				.intersects(new Rectangle((int) player.positionX,
						(int) player.positionY, 32, 32))) {
			return player;
		}
		return null;
	}

	float speed = 10;
	private float clientWidth;
	private float clientHeight;

	private void moveChara(String string, double delta, boolean left,
			boolean right, boolean up, boolean down, boolean shift) {
		// TODO Auto-generated method stub
		if (string.equals(username)) {
			player.frameX += speed * delta;
			if (left) {
				player.positionX -= player.velocity.x * delta;
				player.frameY = 1;

				if (player.positionX <= 0) {
					player.positionX += player.velocity.x * delta;
				}
			} else if (right) {
				player.positionX += player.velocity.x * delta;
				player.frameY = 2;

				if (player.positionX + 32 >= clientWidth) {
					player.positionX -= player.velocity.x * delta;
				}
			} else if (up) {
				player.positionY -= player.velocity.y * delta;
				player.frameY = 3;

				if (player.positionY <= 0) {
					player.positionY += player.velocity.y * delta;
				}
			} else if (down) {
				player.positionY += player.velocity.y * delta;
				player.frameY = 0;

				if (player.positionY + 32 >= clientHeight) {
					player.positionY -= player.velocity.y * delta;
				}
			}
			if (shift && player.getStamina() > 0) {
				player.velocity = new Point(300, 300);
				player.minusStamina(50 * delta);
			} else {
				player.velocity = new Point(100, 100);
			}
			if (player.frameX > 2) {
				player.frameX = 0;
			}
		}
	}

	public void sendTo(String user, String command) {
		for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] != null && threads[i].clientSocket != null
					&& threads[i].username.equals(user)) {
				try {
					threads[i].sendMessage(threads[i].clientSocket, command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public Player getPlayer(String user) {
		for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] != null && threads[i].clientSocket != null
					&& threads[i].username.equals(user)) {
				return Locker.players.get(i);
			} else {
				return null;
			}
		}
		return null;

	}

	Player player = new Player();

	public void syncBroadcast(String command) {
		synchronized (this) {
			for (int i = 0; i < maxClientsCount; i++) {
				if (threads[i] != null && threads[i].clientSocket != null) {
					try {
						threads[i]
								.sendMessage(threads[i].clientSocket, command);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void broadcast(String command) {
		for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] != null && threads[i].clientSocket != null) {
				try {
					threads[i].sendMessage(threads[i].clientSocket, command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
