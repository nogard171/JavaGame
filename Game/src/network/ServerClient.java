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
				Player player = new Player();
				player.setName(username);
				Locker.players.add(player);
			}
			sendMessage(clientSocket, "login");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			broadcast("player/p" + username);

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
					if (message[1].startsWith(".die")) {
						getPlayer(message[0]).isDead = true;
						getPlayer(message[0]).setHealth(0);
					} else {
						broadcast("message/p" + message[0] + "/s" + message[1]);
					}
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
					if (Locker.players.get(getPlayerIndex(data[0])).isDead) {

					} // else {
					/*if (!playerExist(data[0])) {
						Locker.recieveLine = data[0] + " added to players";
						System.out.println(data[0] + " added to players");
						Player player = new Player();
						player.setName(data[0]);
						Locker.players.add(player);
					}*/

					moveChara(data[0], Double.parseDouble(data[1]),
							Boolean.parseBoolean(data[2]),
							Boolean.parseBoolean(data[3]),
							Boolean.parseBoolean(data[4]),
							Boolean.parseBoolean(data[5]),
							Boolean.parseBoolean(data[6]));
					if (Boolean.parseBoolean(data[7])) {
						action(Double.parseDouble(data[1]), data[0]);
					}

					Player player = getPlayer(data[0]);
					broadcast("chara/p" + data[0] + "/s" + player.positionX
							+ "/s" + player.positionY + "/s" + player.frameX
							+ "/s" + player.frameY);
				}
				// }
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
	int spaceCount = 0;

	public void action(double d, String data) {
		Player attacker = Locker.players.get(getPlayerIndex(data));
		if (attacker.frameY == 0) {
			// System.out.println("action down");
			Player p = getPlayerAt(new Point((int) attacker.positionX,
					(int) attacker.positionY + actionDistance));
			attack(d, attacker, p);
		} else if (attacker.frameY == 1) {

			// System.out.println("action left");
			Player p = getPlayerAt(new Point((int) attacker.positionX
					- actionDistance, (int) attacker.positionY));
			attack(d, attacker, p);
		} else if (attacker.frameY == 2) {

			// System.out.println("action right");
			Player p = getPlayerAt(new Point((int) attacker.positionX
					+ actionDistance, (int) attacker.positionY));
			attack(d, attacker, p);
		} else if (attacker.frameY == 3) {

			// System.out.println("action up");
			Player p = getPlayerAt(new Point((int) attacker.positionX,
					(int) attacker.positionY - actionDistance));
			attack(d, attacker, p);
		}

	}

	public void attack(double delta, Player attacker, Player p) {
		if (p != null && attacker.getName() != p.getName()) {

			float attack = (float) (50 * delta);
			if (p.getHealth() <= 0) {
				broadcast("dead/p" + attacker.getName() + "/s" + p.getName());
				p.isDead = true;
			} else {
				p.minusHealth(attack);
				// p.minusStamina(attack)
				sendTo(p.getName(),
						"attack/p" + attacker.getName() + "/s" + p.getName()
								+ "/s" + attack);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (getPlayerIndex(this.username) >= 0
				&& !Locker.players.get(getPlayerIndex(this.username)).isDead) {
			onUpdate();
			try {

				sendMessage(
						clientSocket,
						"stat/p"
								+ Locker.players.get(
										getPlayerIndex(this.username))
										.getName()
								+ "/s"
								+ Locker.players.get(
										getPlayerIndex(this.username))
										.getHealth()
								+ "/s"
								+ Locker.players.get(
										getPlayerIndex(this.username))
										.getMaxHealth()
								+ "/s"
								+ Locker.players.get(
										getPlayerIndex(this.username))
										.getStamina()
								+ "/s"
								+ Locker.players.get(
										getPlayerIndex(this.username))
										.getMaxStamina());

				// sendMessage(clientSocket, "stat/p" + player.getName() + "/s"+
				// player.getStamina());
			} catch (IOException e1) { // TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}
	}

	public void onUpdate() {
		Locker.players.get(getPlayerIndex(this.username)).update();
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
			if (Locker.players.get(i).getName() != username
					&& new Rectangle(position.x, position.y, 32, 32)
							.intersects(new Rectangle((int) Locker.players
									.get(i).positionX, (int) Locker.players
									.get(i).positionY, 32, 32))) {
				return Locker.players.get(i);
			}
		}
		/*
		 * if (new Rectangle(position.x, position.y, 32, 32) .intersects(new
		 * Rectangle((int) player.positionX, (int) player.positionY, 32, 32))) {
		 * return player; }
		 */
		return null;
	}

	float speed = 10;
	private float clientWidth;
	private float clientHeight;

	private void moveChara(String string, double delta, boolean left,
			boolean right, boolean up, boolean down, boolean shift) {
		// TODO Auto-generated method stub
		/*
		 * if (string.equals(username) && !player.isDead) { player.frameX +=
		 * speed * delta; if (left) { player.positionX -= player.velocity.x *
		 * delta; player.frameY = 1;
		 * 
		 * if (player.positionX <= 0) { player.positionX += player.velocity.x *
		 * delta; } } else if (right) { player.positionX += player.velocity.x *
		 * delta; player.frameY = 2;
		 * 
		 * if (player.positionX + 32 >= clientWidth) { player.positionX -=
		 * player.velocity.x * delta; } } else if (up) { player.positionY -=
		 * player.velocity.y * delta; player.frameY = 3;
		 * 
		 * if (player.positionY <= 0) { player.positionY += player.velocity.y *
		 * delta; } } else if (down) { player.positionY += player.velocity.y *
		 * delta; player.frameY = 0;
		 * 
		 * if (player.positionY + 32 >= clientHeight) { player.positionY -=
		 * player.velocity.y * delta; } } if (shift && player.getStamina() > 0)
		 * { player.velocity = new Point(300, 300); player.minusStamina(50 *
		 * delta); } else { player.velocity = new Point(100, 100); } if
		 * (player.frameX > 2) { player.frameX = 0; } } if (this.isServer) {
		 */
		Player p = Locker.players.get(getPlayerIndex(username));
		if (!p.isDead) {
			p.frameX += speed * delta;
			if (left) {
				p.positionX -= p.velocity.x * delta;
				p.frameY = 1;

				if (p.positionX <= 0) {
					p.positionX += p.velocity.x * delta;
				}
			} else if (right) {
				p.positionX += p.velocity.x * delta;
				p.frameY = 2;

				if (p.positionX + 32 >= clientWidth) {
					p.positionX -= p.velocity.x * delta;
				}
			} else if (up) {
				p.positionY -= p.velocity.y * delta;
				p.frameY = 3;

				if (p.positionY <= 0) {
					p.positionY += p.velocity.y * delta;
				}
			} else if (down) {
				p.positionY += p.velocity.y * delta;
				p.frameY = 0;

				if (p.positionY + 32 >= clientHeight) {
					p.positionY -= p.velocity.y * delta;
				}
			}
			if (shift && p.getStamina() > 0) {
				p.velocity = new Point(300, 300);
				p.minusStamina(50 * delta);
			} else {
				p.velocity = new Point(100, 100);
			}
			if (p.frameX > 2) {
				p.frameX = 0;
			}
		}
		// }
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
		for (int i = 0; i < Locker.players.size(); i++) {
			if (Locker.players.get(i).getName().toLowerCase()
					.equals(username.toLowerCase())) {
				return Locker.players.get(i);
			}
		}
		return null;

	}

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
