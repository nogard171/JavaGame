import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import network.Chat;
import network.Client;
import network.Locker;
import network.Server;
import objects.*;
import util.FrameRate;
import util.InputHandler;
import util.TextureHandler;

public class Game extends JFrame implements Runnable {

	public Game() {
		loadClasses();
	}

	int width = 800;
	int height = 600;
	FrameRate frameRate;
	private GraphicsDevice graphicsDevice;
	private DisplayMode currentDisplayMode;
	private BufferStrategy bs;
	private Thread gameThread;
	boolean fullscreen = false;
	boolean serverStatus = false;
	// this is the clients status, if it connected to a server or not
	boolean clientStatus = false;
	int oldWidth = 0;
	int oldHeight = 0;
	// this declares the input object
	InputHandler input;
	// the server and client vars
	Server server;
	Client client;

	public void createAndShowGUI() {
		setIgnoreRepaint(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Test title");
		setBackground(Color.white);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		graphicsDevice = ge.getDefaultScreenDevice();

		if (fullscreen) {
			setUndecorated(true);
			setFullscreen();
			// graphicsDevice.setDisplayMode(getDisplayMode());
		} else {
			setWindow();
		}

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void setFullscreen() {
		currentDisplayMode = graphicsDevice.getDisplayMode();
		width = graphicsDevice.getDisplayMode().getWidth();
		height = graphicsDevice.getDisplayMode().getHeight();
		if (!graphicsDevice.isFullScreenSupported()) {
			System.err.println("ERROR: Not Supported.");
		}
		// set the inputhandler to this
		input = new InputHandler(this);
		chat = new Chat(this);

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					shutdown();
				}
			}
		});
		graphicsDevice.setFullScreenWindow(this);

		// recenter window
		setLocationRelativeTo(null);

		createBufferStrategy(2);
		bs = getBufferStrategy();
	}

	public void setWindow() {
		Canvas canvas = new Canvas();
		canvas.setSize(width, height);
		// set the inputhandler to this
		input = new InputHandler(canvas);
		chat = new Chat(canvas);
		getContentPane().add(canvas);
		pack();
		setVisible(true);
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
	}

	public void shutdown() {
		try {
			running = false;
			System.out.println("Game Loop Stopped.");
			if (fullscreen) {
				gameThread.join();
				graphicsDevice.setDisplayMode(this.currentDisplayMode);
				graphicsDevice.setFullScreenWindow(null);
				System.out.println("Display Restored.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public DisplayMode getDisplayMode() {
		return new DisplayMode(800, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
	}

	boolean running = false;
	boolean login = false;

	public void run() {
		login = false;
		System.out.println("game running");
		frameRate.initialize();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		while (login) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			loginLoop(nsPerFrame / 1.0E9);
			lastTime = curTime;
		}
		
		running = true;
		onSetup();
		curTime = System.nanoTime();
		lastTime = curTime;
		while (running) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			gameLoop(nsPerFrame / 1.0E9);
			lastTime = curTime;
		}
	}

	public void onSetup() {
		onTextureLoading();

		Locker.player.setName(Locker.username);
	}

	public void loginLoop(double d) {
		Login login = new Login();
		do {
			do {
				Graphics g = null;
				if (bs == null) {
					g = createImage(width, height).getGraphics();
				} else {
					g = bs.getDrawGraphics();
				}
				g.clearRect(0, 0, getWidth(), getHeight());
				login.onUpdate(d);
				login.onPaint(g);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	public void gameLoop(double d) {

		do {
			do {
				Graphics g = null;
				if (bs == null) {
					g = createImage(width, height).getGraphics();
				} else {
					g = bs.getDrawGraphics();
				}
				g.clearRect(0, 0, getWidth(), getHeight());
				onUpdate(d);
				onPaint(g);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	public void loadClasses() {

		frameRate = new FrameRate();
	}

	public void onTextureLoading() {

		Locker.player.setTexture(TextureHandler
				.textureLoad("/Game/resources/images/playerset.png"));
	}

	public void onUpdate(double d) {
		frameRate.calculate();
		Locker.clientWidth = width;
		Locker.clientHeight = height;
		processInput(d);
	}

	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	boolean shift = false;
	boolean space = false;
	boolean spaceDown = false;

	public void processInput(double delta) {
		// if right arrow is pressed, add to the players x position
		right = input.isKeyDown(KeyEvent.VK_RIGHT);
		// if left arrow is pressed, minus to the players x position
		left = input.isKeyDown(KeyEvent.VK_LEFT);
		// if up arrow is pressed, minus to the players y position
		up = input.isKeyDown(KeyEvent.VK_UP);
		// if down arrow is pressed, add to the players y position
		down = input.isKeyDown(KeyEvent.VK_DOWN);
		// if shift is pressed
		shift = input.isKeyDown(KeyEvent.VK_SHIFT);
		space = input.isKeyDown(KeyEvent.VK_SPACE);

		if (left || right || up || down || shift || space) {
			Locker.proticol = "move";
			Locker.sendLine = delta + "/s" + left + "/s" + right + "/s" + up
					+ "/s" + down + "/s" + shift + "/s" + space;
		}
		// start the server, then connect a client to it.
		if (input.isKeyDown(KeyEvent.VK_I)) {

		}
		// start the server, then connect a client to it.
		if (input.isKeyDown(KeyEvent.VK_F2)) {
			// set server to a new Server
			server = new Server();
			if (!isPortInUse(Locker.serverName, Locker.port)) {
				// check if the server is null
				if (server != null && serverStatus) {
					System.out.println("Server could not be started");
				} else {

					// start the server thread
					server.start();
					// delay 1 second
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// toggle serverstatus
				serverStatus = !serverStatus;
				// set the client to a new client
				client = new Client();
				client.master = true;
			}
			else
			{
				// set the client to a new client
				client = new Client();
			}			
			// check if the client is null
			if (client != null && client.isAlive()) {
				// disconnect the client if it's connected.
				client.disconnect();
			} else {
				// set the client's username to the local player one
				client.username = Locker.username;
				// start the client thread
				client.start();
				// delay 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if the client is connected set clientstatus to it.
				if (client.connected) {
					clientStatus = client.connected;
				}
			}
		}
		// start the client if the server is not started.
		if (input.isKeyDown(KeyEvent.VK_F3) && !serverStatus) {

			// set the client to a new client
			client = new Client();
			// check if the client is null
			if (client != null && client.isAlive()) {
				// disconnect the client if it's connected.
				client.disconnect();
			} else {
				// set the client's username to the local player one
				client.username = Locker.username;
				// start the client thread
				client.start();
				// delay 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if the client is connected set clientstatus to it.
				if (client.connected) {
					clientStatus = client.connected;
				}
			}
		}
		// if escape is pressed continue
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			// call countDownExit function
			countDownExit(5);
		}
		// check for networking data.
		networkingData();
	}

	private boolean isPortInUse(String host, int port) {
		// Assume no connection is possible.
		boolean result = false;

		try {
			(new Socket(host, port)).close();
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public void networkingData() {
		// if sendline has data send it to the server.
		if (Locker.sendLine != "" && clientStatus) 
		{
			if(Locker.proticol.startsWith("message"))
			{
			sendMessage(Locker.proticol + "/p" +Locker.sendTime+"/s"+ Locker.username + "/s"
					+ Locker.sendLine);
			}
			else
			{
				sendMessage(Locker.proticol + "/p" + Locker.username + "/s"
						+ Locker.sendLine);
			}
			// set sendline back to nothing
			Locker.sendLine = "";
		}
		// if receiveline has something add it into the chat.
		if (Locker.recieveLine != "") {
			if (Locker.recieveLine.startsWith("chat/p")) {
				String[] data = Locker.recieveLine.substring(
						Locker.recieveLine.indexOf("/p") + 2,
						Locker.recieveLine.length()).split("/s");
				chat.position = new Point(Integer.parseInt(data[0]),
						Integer.parseInt(data[1]));
			} else {
				chat.Lines.add(Locker.recieveLine);
			}
			// set the receiveline to nothing
			Locker.recieveLine = "";
		}
	}

	// the sendmessage function to send text to the server
	public void sendMessage(String message) {
		try {
			client.sendMessage(client.client, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// count down to 0 from the inputted time
	public void countDownExit(int time) {
		// ouput text
		System.out.println("Closing in " + time + " Seconds...");
		try {
			// loop the number of times
			for (int i = time; i > 0; i--) {
				// output current time
				System.out.println(i);
				// paused the entire thread for 1 second
				Thread.sleep(1000);
			}
			// output closed
			System.out.println("Closed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// exit the current application
		System.exit(0);
	}

	public void onPaint(Graphics g) {
		paintPlayers(g);
		drawTitleBar(g);
		chat.draw(g);
		repaint();
	}

	private void paintPlayers(Graphics g) {
		// TODO Auto-generated method stub
		try {
			for (Player player : Locker.players) {
				player.setTexture(TextureHandler
						.textureLoad("/Game/resources/images/playerset.png"));

				if (player.positionY <= Locker.player.positionY) {
					player.draw(g);
				}
			}
		} catch (ConcurrentModificationException cme) {

		}

		try {
			Locker.player.draw(g);
			for (Player player : Locker.players) {
				player.setTexture(TextureHandler
						.textureLoad("/Game/resources/images/playerset.png"));
				if (player.positionY > Locker.player.positionY) {
					player.draw(g);
				}
			}
		} catch (ConcurrentModificationException cme) {

		}
	}

	Chat chat;

	public void drawTitleBar(Graphics g) {

		if (clientStatus) {

			g.setColor(new Color(64, 64, 64, 128));
			g.fillRect(0, 0, 200, 45);
			g.setColor(Color.black);
			g.drawRect(0, 0, 200, 45);

			g.setColor(Color.white);
			g.drawString("You are connected to:", 10, 10);
			g.drawString(Locker.ipAddress, 10, 25);
			g.drawString(frameRate.getFrameRate(), 10, 40);
		}
		g.setColor(Color.black);
		g.drawString("stamina:" + Locker.player.getHealth(), 100, 100);
	}

	public void onClose() {
		System.out.println("Closing");
	}

	static BufferedImage texture;

}
