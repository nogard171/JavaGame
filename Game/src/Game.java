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
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import network.Client;
import network.Locker;
import network.Server;
import objects.*;
import util.FrameRate;

public class Game extends JFrame implements Runnable {

	public Game() {
		loadClasses();
	}

	int width = 832;
	int height = 640;
	FrameRate frameRate;
	private GraphicsDevice graphicsDevice;
	private DisplayMode currentDisplayMode;
	private BufferStrategy bs;
	private Thread gameThread;
	boolean fullscreen = false;
	boolean serverStatus = false;
	// this is the clients status, if it connected to a server or not
	boolean clientStatus = false;

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

	public void run() {
		running = true;
		System.out.println("game running");
		frameRate.initialize();
		onSetup();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		while (running) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			gameLoop(nsPerFrame / 1.0E9);
			lastTime = curTime;
		}
	}

	public void onSetup() {
		onTextureLoading();

		
		// set the chat as keylistener
		try {
			Locker.player.setTexture(ImageIO.read(new URL(
					"http://204.237.93.81/resources/images/playerset.png")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Locker.username = "testing";
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

	// NPC_AI npc = new NPC_AI();
	public void loadClasses() {

		frameRate = new FrameRate();
	}

	public void onTextureLoading() {
		// NPC_AI npcA = new NPC_AI();
		// Locker.npcs.add(npcA);

		try {
			Locker.player.setTexture(ImageIO.read(new URL(
					"http://204.237.93.81/resources/images/playerset.png")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			backupLoad();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			backupLoad();
			e.printStackTrace();
		}
		Locker.username = "testing";
	}
	public void backupLoad()
	{
		try {
			Locker.player.setTexture(ImageIO.read(new URL(
					"http://localhost/resources/images/playerset.png")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onUpdate(double d) {

		processInput(d);
	}

	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;

	public void processInput(double delta) {
		// if right arrow is pressed, add to the players x position
		right = input.isKeyDown(KeyEvent.VK_RIGHT);
		// if left arrow is pressed, minus to the players x position
		left = input.isKeyDown(KeyEvent.VK_LEFT);
		// if up arrow is pressed, minus to the players y position
		up = input.isKeyDown(KeyEvent.VK_UP);
		// if down arrow is pressed, add to the players y position
		down = input.isKeyDown(KeyEvent.VK_DOWN);

		if (left || right || up || down) {
			Locker.proticol = "move";
			Locker.sendLine = delta+","+left + "," + right + "," + up + "," + down;
		}
		// start the server, then connect a client to it.
		if (input.isKeyDown(KeyEvent.VK_F2)) {
			// set server to a new Server
			server = new Server();
			// check if the server is null
			if (server != null && serverStatus) {

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

	public void networkingData() {
		// if sendline has data send it to the server.
		if (Locker.sendLine != "" && clientStatus) {
			sendMessage(Locker.proticol + ":" + Locker.username + ","
					+ Locker.sendLine);
			// set sendline back to nothing
			Locker.sendLine = "";
		}
		// if receiveline has something add it into the chat.
		if (Locker.recieveLine != "") {
			// chat.Lines.add(Locker.recieveLine);
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
		
		Locker.player.draw(g);
		/*for (Player player : Locker.players) {
			player.draw(g);
		}*/
		repaint();
	}

	public void onClose() {
		System.out.println("Closing");
	}

	static BufferedImage texture;

}
