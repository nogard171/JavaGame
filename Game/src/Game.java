import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;

import networking.Client;
import networking.Locker;
import networking.Server;
import objects.*;
import objects.Object;
import util.FrameRate;
import util.ImageLoader;
import util.KeyboardInput;
import util.MouseInput;
import util.ParticleSystem;
import util.Time;

public class Game extends JFrame implements Runnable {

	public Game() {
		loadClasses();
	}

	int width = 832;
	int height = 640;
	FrameRate frameRate;
	KeyboardInput keyboard;
	MouseInput mouse;
	Interface ui;
	Interface menu;
	private GraphicsDevice graphicsDevice;
	private DisplayMode currentDisplayMode;
	private BufferStrategy bs;
	private Thread gameThread;

	public void createAndShowGUI() {
		setIgnoreRepaint(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Test title");
		setBackground(Color.white);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		graphicsDevice = ge.getDefaultScreenDevice();

		if (Locker.fullscreen) {
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
		title.loadClasses(this);
		loading.loadClasses(this);
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
		addKeyListener(keyboard);
		addKeyListener(title.keyboard);
		addMouseListener(mouse);
		addMouseListener(title.mouse);
		addMouseMotionListener(mouse);
		addMouseMotionListener(title.mouse);
		graphicsDevice.setFullScreenWindow(this);

		// recenter window
		setLocationRelativeTo(null);

		createBufferStrategy(2);
		bs = getBufferStrategy();
	}

	Canvas canvas;

	public void setWindow() {
		canvas = new Canvas();
		canvas.setSize(width, height);

		title.loadClasses(canvas);
		loading.loadClasses(canvas);

		canvas.addKeyListener(keyboard);
		canvas.addKeyListener(title.keyboard);
		canvas.addMouseListener(mouse);
		canvas.addMouseListener(title.mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseMotionListener(title.mouse);
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
			if (Locker.fullscreen) {
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
		System.out.println("game running");
		frameRate.initialize();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		while (!title.OK) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			title.gameLoop(nsPerFrame / 1.0E9, bs);
			lastTime = curTime;
		}
		Locker.username = title.username;
		running = true;
		frameRate.initialize();
		onSetup();
		curTime = System.nanoTime();
		lastTime = curTime;
		boolean oneLoad = false;
		while (running) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			gameLoop(nsPerFrame / 1.0E9);
			lastTime = curTime;
		}
	}

	public void onSetup() {
		onTextureLoading();
		frameRate.calculate();
		Locker.player.setName(Locker.username);
		Locker.keys = new KeyBindings(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
				KeyEvent.VK_UP, KeyEvent.VK_DOWN);

		ui.dim = new Dimension(32 * 6, 32 * 7);
		ui.setWindowPosition(new Point(32, (height) - (32 * 7)));
		ui.usesWindow = true;
		ui.setPosition(new Point(0, (height) - (32 * 7)));
		ArrayList<MenuItem> uiItems = new ArrayList<MenuItem>();
		MenuItem bag = new MenuItem();
		bag.description = "This will show the players bags items.";
		bag.setTag("bag");
		bag.setBounds(new Rectangle(0, 0, 32, 32));
		MenuItem chara = new MenuItem();
		chara.description = "This will show the players stats and related info.";
		chara.setTag("chara");
		chara.setBounds(new Rectangle(0, 32, 32, 32));
		MenuItem skills = new MenuItem();
		skills.description = "This will show the players skills.";
		skills.setTag("skills");
		skills.setBounds(new Rectangle(0, 64, 32, 32));
		MenuItem equip = new MenuItem();
		equip.description = "This will show the players equipment.";
		equip.setTag("equip");
		equip.setBounds(new Rectangle(0, 96, 32, 32));
		MenuItem magic = new MenuItem();
		magic.description = "This will show the players magic skills.";
		magic.setTag("magic");
		magic.setBounds(new Rectangle(0, 128, 32, 32));

		MenuItem hideShow = new MenuItem();
		hideShow.backDrop = false;
		hideShow.description = "This will hide the Interface";
		hideShow.setHoverable(false);
		hideShow.setTag("hide");
		hideShow.setBounds(new Rectangle(0, (0 - ui.getPosition().y) + height
				/ 2, 32, 32));
		MenuItem exit = new MenuItem();
		exit.description = "You must click exit twice to quit the game.";
		exit.setTag("exit");
		exit.setBounds(new Rectangle(0, 0 - ui.getPosition().y, 32, 32));

		uiItems.add(bag);
		uiItems.add(chara);
		uiItems.add(skills);
		uiItems.add(equip);
		uiItems.add(magic);
		
		uiItems.add(hideShow);
		uiItems.add(exit);
		ui.onLoad(uiItems);
		frameRate.calculate();
		loading.itemCount = 10-frameRate.frameCount;
	}

	boolean menuShown = false;
	boolean titleScreen = false;
	TitleScreen title;
	Loading loading;

	@SuppressWarnings("deprecation")
	public void gameLoop(double d) {

		do {
			do {
				Graphics g = null;
				if (bs == null) {
					
					g = createImage(width, height).getGraphics();
				} else {
					g = bs.getDrawGraphics();
				}
				if (canvas != null) {
					width = canvas.getWidth();
					height = canvas.getHeight();
				} 		
				g.clearRect(0, 0, width, height);
				onUpdate(d);
				onPaint(g,d);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	// NPC_AI npc = new NPC_AI();
	public void loadClasses() {
		title = new TitleScreen();
		loading = new Loading();
		keyboard = new KeyboardInput(this);
		mouse = new MouseInput();
		ui = new Interface();
		menu = new Interface();
		frameRate = new FrameRate();
	}

	public void onTextureLoading() {
		// NPC_AI npcA = new NPC_AI();
		// Locker.npcs.add(npcA);

		texture = ImageLoader
				.getImageFromResources("\\resources\\image\\tileset.png");
		Locker.player.setTexture(ImageLoader
				.getImageFromResources("\\resources\\image\\playerset.png"));
		for (NPC_AI npc : Locker.npcs) {
			npc.setTexture(ImageLoader
					.getImageFromResources("\\resources\\image\\playerset.png"));

		}
	}

	int season = 0;
	Time time = new Time();

	public void onUpdate(double d) {
		ui.gameDim = new Dimension(width,height);
		Locker.dim = new Dimension(width,height);
		// System.out.println("looping");
		Locker.player.onUpdate(d);
		// TODO Auto-generated method stub
		try
		{
			Locker.map.checkCollision(Locker.player);
		}
		catch(ConcurrentModificationException error)
		{
			
		}
		//Locker.map.checkCollision(npc.character);
		time.onUpdate();
		Locker.map.onUpdate(d);
		for (NPC_AI npc : Locker.npcs) {
			npc.onUpdate(d);
		}
		processInput(d);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int space = 0;

	public void processInput(double delta) {
		if (!Locker.changeKeyBindings) {
			mouse.poll();

			if (!menuShown) {
				if (keyboard.isKeyDown(Locker.keys.Right)) {
					Locker.player.moveRight();
				} else if (keyboard.isKeyDown(Locker.keys.Left)) {
					Locker.player.moveLeft();
				} else if (keyboard.isKeyDown(Locker.keys.Up)) {
					Locker.player.moveUp();
				} else if (keyboard.isKeyDown(Locker.keys.Down)) {
					Locker.player.moveDown();

				}
				if (keyboard.isKeyDown(KeyEvent.VK_SPACE) && space == 0) {
					space++;
					Object obj = Locker.map.getObjectAt(
							new Point(Locker.player.getPosition().x-Locker.map.position.x,
									Locker.player.getPosition().y-Locker.map.position.y),
							Locker.player.direction);
					if (obj != null) {
						Locker.player.preformAction(obj);
					} else {
						Locker.player.preformAction(null);
					}

				} else if (!keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
					space = 0;
				}

				double speed = delta*550;
				if (keyboard.isKeyDown(KeyEvent.VK_D)) {
					Locker.map.position.x-=speed;
					Locker.player.bounds.x-=speed;
				}
				else if (keyboard.isKeyDown(KeyEvent.VK_A)) {
					Locker.map.position.x+=speed;
					Locker.player.bounds.x+=speed;
				}
				else if (keyboard.isKeyDown(KeyEvent.VK_W)) {
					Locker.map.position.y+=speed;
					Locker.player.bounds.y +=speed;
				}
				else if (keyboard.isKeyDown(KeyEvent.VK_S)) {
					Locker.map.position.y-=speed;
					Locker.player.bounds.y -=speed;
				}
				if (keyboard.isKeyDown(KeyEvent.VK_SHIFT)) {
					Locker.player.dash();
				} else {
					Locker.player.stopDashing();
				}
				if (keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
					keyboard.currentKey = -1;
				}

				ui.onInput(mouse);

				if (!keyboard.isKeyDown(KeyEvent.VK_ALT)) {
					 ui.hoverDescription = "";
				}
			} else {
				menu.onInput(mouse);
			}
			if (keyboard.isKeyDown(KeyEvent.VK_F1)) {
				debug = !debug;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (keyboard.isKeyDown(KeyEvent.VK_F2)) {

				server = new Server();
				if (server != null && Locker.serverStatus) {
					Locker.sendLine = "shutdown:" + Locker.player.getName();
				} else {
					server.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Locker.serverStatus = !Locker.serverStatus;
				client = new Client();
				if (client != null && client.isAlive()) {
					client.disconnect();
				} else {
					client.username = Locker.username;

					client.setMaster(true);
					client.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.connected) {

						Locker.clientStatus = client.connected;
					}
				}
			}
			if (keyboard.isKeyDown(KeyEvent.VK_F3) && !Locker.serverStatus) {

				client = new Client();
				if (client != null && client.isAlive()) {
					client.disconnect();
				} else {
					client.username = Locker.username;
					client.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.connected) {

						Locker.clientStatus = client.connected;
					}
				}

			}
			if (keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {
				menuShown = true;
			}

			try {
				for (int i = 0; i < Locker.players.size(); i++) {
					Locker.players
							.get(i)
							.setTexture(
									ImageLoader
											.getImageFromResources("\\resources\\image\\playerset.png"));
					Locker.players.get(i).onUpdate(delta);
				}
			} catch (Exception e) {
				System.out.println("no players to read");
			}
			networkingData();
		} else {
			if (keyboard.currentKey != keyboard.lastKey) {
				if (Locker.changeKey == "left") {
					Locker.keys = new KeyBindings(keyboard.currentKey,
							Locker.keys.Right, Locker.keys.Up, Locker.keys.Down);
				} else if (Locker.changeKey == "right") {
					Locker.keys = new KeyBindings(Locker.keys.Left,
							keyboard.currentKey, Locker.keys.Up,
							Locker.keys.Down);
				} else if (Locker.changeKey == "up") {
					Locker.keys = new KeyBindings(Locker.keys.Left,
							Locker.keys.Right, keyboard.currentKey,
							Locker.keys.Down);
				} else if (Locker.changeKey == "down") {
					Locker.keys = new KeyBindings(Locker.keys.Left,
							Locker.keys.Right, Locker.keys.Up,
							keyboard.currentKey);
				}
				Locker.changeKeyBindings = false;
				Locker.showMessage = "";
			}
		}
	}

	public void showMessage(Graphics g) {
		g.setColor(new Color(0, 0, 0, 128));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(255, 255, 255));
		g.drawString(Locker.showMessage, width / 2,
				height / 2);
	}

	// the server and client vars
	Server server;
	Client client;

	public void networkingData() {
		if (Locker.sendLine != "" && Locker.clientStatus) {
			sendMessage(Locker.proticol + ":" + Locker.player.getName() + ","
					+ Locker.sendLine);
			Locker.sendLine = "";
		}
		if (Locker.recieveLine != "") {
			// System.out.println(Locker.recieveLine);

			debugLines.add(Locker.recieveLine);
			Locker.recieveLine = "";
		}
		// System.out.println("players:"+Locker.players.size());
	}

	public void sendMessage(String message) {
		try {
			client.sendMessage(client.client, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	boolean debug = false;

	public void onPaint(Graphics g, double d) {
		
		frameRate.calculate();
		Locker.map.onPaint(g, this);

		for (Player player : Locker.players) {
			player.draw(g, this);
		}
		for (NPC_AI npc : Locker.npcs) {
			if (npc.bounds.y <= Locker.player.getBounds().y) {
				npc.onPaint(g, this);
			}
		}
		Locker.player.draw(g, null);
		for (NPC_AI npc : Locker.npcs) {
			if (npc.bounds.y > Locker.player.getBounds().y) {
				npc.onPaint(g, this);
			}
		}
		Locker.map.onUpperPaint(g, this);
		ui.onPaint(g, this);

		if (Locker.serverStatus) {
			g.setColor(new Color(0, 0, 0, 96));
			g.fillRect(32, 0, 116, 18);
			g.setColor(Color.white);
			g.drawString("Server Status: " + Locker.serverStatus, 32, 12);
			g.setColor(Color.BLACK);
		} else if (Locker.clientStatus) {
			g.setColor(new Color(0, 0, 0, 96));
			g.fillRect(32, 0, 116, 18);
			g.setColor(Color.white);
			g.drawString("Client Status: " + Locker.clientStatus, 32, 12);
			g.setColor(Color.BLACK);
		}

		if (menuShown) {
			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, width,height);
			menu.onPaint(g, this);
			g.setColor(Color.BLACK);
		}

		if (debug) {
			onDebug(g);
		}
		if (Locker.showMessage != "") {
			showMessage(g);
		}
		if(!loading.LOADED){
			loading.gameLoop(d, bs);
		}	
		repaint();
	}

	Rectangle rec = new Rectangle(0, 20, 50, 10);
	ArrayList<String> debugLines = new ArrayList<String>();

	public void onDebug(Graphics g) {
		int width = 150;
		int height = 30 + (debugLines.size() * 20);
		g.setColor(new Color(0, 0, 0, 128));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(frameRate.getFrameRate(), 0, 10);
		g.drawString("Seconds Running" + time.getSeconds(), 0, 20);
		for (int i = 0; i < debugLines.size(); i++) {
			g.drawString(debugLines.get(i), 0, 30 + (i * 20));
		}
	}

	public void onClose() {
		System.out.println("Closing");
	}

	static BufferedImage texture;

}
