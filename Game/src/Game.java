import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
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
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Objects.*;
import events.Action;
import util.*;
import util.Client;

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
	// this declares the input object
	InputHandler input;
	public Client network = new Client();

	public void createAndShowGUI() {
		setIgnoreRepaint(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Test title");
		setBackground(Color.white);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
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
	boolean login = false;

	public void run() {
		if (network == null) {
			network = new Client();
		}
		network.start();

		running = true;
		System.out.println("game running");
		frameRate.initialize();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		onSetup();
		while (running) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			gameLoop(nsPerFrame / 1.0E9);
			lastTime = curTime;
		}
	}

	public void onSetup() {
		onTextureLoading();

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
				processInput(d);
				if (game_state == Game_States.GAME) {
					onGameUpdate(d);
					onGamePaint(g);
				} else if (game_state == Game_States.TITLE_SCREEN) {
					onTitleUpdate(d);
					onTitlePaint(g);
				}
				else if (game_state == Game_States.LOADING) {
					if(network.map.tiles.size()<=0)
					{
						network.SendData(data);
					}
				}
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	Game_States game_state = Game_States.TITLE_SCREEN;

	public void loadClasses() {
		// domidpoint();
		frameRate = new FrameRate();
	}

	public void onTextureLoading() {

	}

	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	boolean shift = false;
	boolean space = false;
	boolean spaceDown = false;
	boolean tab = false;

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
		tab = input.isKeyDown(KeyEvent.VK_TAB);
		input.poll();
		// if escape is pressed continue
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			// call countDownExit function
			countDownExit(5);
		}
		
		if (input.isKeyDown(KeyEvent.VK_ENTER)) {
			// call countDownExit function
			network.login("alex", "test");
		}
		// check for networking data.
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

	int tab_Pressed = 0;

	public void onTitleUpdate(double d) {
		if (tab) {
			if (username.Focus && tab_Pressed <= 0) {
				username.Focus = false;
				password.Focus = true;

			} else if (password.Focus && tab_Pressed <= 0) {
				username.Focus = true;
				password.Focus = false;
			}
			tab_Pressed++;
		} else {
			tab_Pressed = 0;
		}

		username.click(input);
		username.input(input);
		password.click(input);
		password.input(input);
		btn.click(input);
		btn.onClick(new Action() {
			public void actionPerformed() {
				Boolean login = network.login(username.getText(), password.getText());
				if(login)
				{
					game_state = Game_States.LOADING;
				}
			}
		});
	}

	TextBox username = new TextBox((this.width / 2) - 50, this.height / 2);
	TextBox password = new TextBox((this.width / 2) - 50, this.height / 2 + 30, true);
	Button btn = new Button("Login", (this.width / 2) - 20, this.height / 2 + 60);
	Label title = new Label("Title", (this.width / 2) - 50, 100, new Font("Arial", Font.PLAIN, 40));
	Label error = new Label("Title", (this.width / 2) - 50, (this.height / 2) - 20);

	public void onTitlePaint(Graphics g) {
		if (network.error != "") {
			error.setColor(Color.red);
			error.setText(network.error);
			error.Draw(g);
		}
		title.Draw(g);
		username.Draw(g);
		password.Draw(g);
		btn.Draw(g);
		repaint();
	}

	public void onGameUpdate(double d) {
		frameRate.calculate();

	}

	public void onGamePaint(Graphics g) {
		g.drawString("test", 100, 100);
		repaint();
	}

	public void onClose() {
		System.out.println("Closing");
	}

}
