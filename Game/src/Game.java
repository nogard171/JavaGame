import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
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

public class Game extends JPanel implements Runnable {
	int x = 0;
	static int width = 800;
	static int height = 600;
	private static Thread gameThread;

	public Game() {

		input = new InputHandler(this);
		frameRate = new FrameRate();
	}

	@Override
	public void paint(Graphics g) {
		setIgnoreRepaint(true);
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, 30, 30);
		g2d.drawOval(0, 50, 30, 30);
		g2d.fillRect(50, 0, 30, 30);
		g2d.drawRect(50, 50, 30, 30);

		processInput(0);
		if (game_state == Game_States.GAME) { //
			onGameUpdate(0);
			onGamePaint(g2d);
		} else if (game_state == Game_States.TITLE_SCREEN) {
			onTitleUpdate(0);
			onTitlePaint(g2d);
		}
		g.dispose();
	}

	Game_States game_state = Game_States.GAME;

	public void loadClasses() {
		// domidpoint();
		frameRate = new FrameRate();
	}

	FrameRate frameRate;
	// this declares the input object
	static InputHandler input;
	public Client network = new Client();
	ArrayList<Entity> objects = new ArrayList<Entity>();
	Entity crystal = new Entity(300, 300);

	public void onSetup() {
		OBJLoader loader = new OBJLoader();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("tileset.png"));
		} catch (IOException e) {
		}
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Entity ground = new Entity(img);
				ground.x = (x * 32) - (y * 32);
				ground.y = (y * 16) + (x * 16);
				ground.x += 300;
				ground.y += 100;

				ground.obj = loader.getOBJ("ground.obj");
				objects.add(ground);
			}
		}

		crystal.obj = loader.getOBJ("tree.obj");
		// y = crystal.y;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Mini Tennis");
		Game game = new Game();
		frame.add(game);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		// game.loop();
		gameThread = new Thread(game);
		gameThread.start();
	}

	boolean running = false;
	double delta = 0;

	@Override
	public void run() {
		if (network == null) {
			network = new Client();
		}
		// network.start();

		running = true;
		System.out.println("game running");
		// frameRate.initialize();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		onSetup();
		// TODO Auto-generated method stub
		while (true) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			delta = nsPerFrame / 1.0E9;
			lastTime = curTime;
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
				if (login) {
					game_state = Game_States.GAME;
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

	int y = 0;
	boolean bounce = false;

	public void onGamePaint(Graphics g) {
		// g.drawString("test", 100, 100);
		int size = objects.size();
		for (int i = 0; i < size; i++) {
			objects.get(i).LowDensityDraw(g);
		}
		// crystal.draw(g);

	}

	public void onClose() {
		System.out.println("Closing");
	}
}