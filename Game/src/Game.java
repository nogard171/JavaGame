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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.FrameRate;
import util.ImageLoader;
import util.KeyboardInput;
import util.MouseInput;

public class Game extends JFrame implements Runnable {

	public Game() {
		loadClasses();
	}

	int width = 832;
	int height = 640;
	boolean fullscreen = false;
	FrameRate frameRate;
	KeyboardInput keyboard;
	MouseInput mouse;
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

		if (fullscreen) {
			setUndecorated(true);

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
			addMouseListener(mouse);
			addMouseMotionListener(mouse);
			graphicsDevice.setFullScreenWindow(this);

			createBufferStrategy(2);
			bs = getBufferStrategy();
			// graphicsDevice.setDisplayMode(getDisplayMode());
		} else {
			Canvas canvas = new Canvas();
			canvas.setSize(width, height);
			canvas.addKeyListener(keyboard);
			canvas.addMouseListener(mouse);
			canvas.addMouseMotionListener(mouse);
			getContentPane().add(canvas);
			pack();
			setVisible(true);
			canvas.createBufferStrategy(2);
			bs = canvas.getBufferStrategy();
		}

		gameThread = new Thread(this);
		gameThread.start();
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
		while (running) {
			gameLoop();
		}
	}

	public void onSetup() {
		onTextureLoading();
	}

	boolean menuShown = false;
	boolean titleScreen = false;

	public void gameLoop() {

		do {
			do {
				Graphics g = null;
				if (bs == null) {
					g = createImage(width, height).getGraphics();
				} else {
					g = bs.getDrawGraphics();
				}
				g.clearRect(0, 0, getWidth(), getHeight());
				onUpdate();
				onPaint(g);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	public void loadClasses() {

		keyboard = new KeyboardInput(this);
		mouse = new MouseInput();
		frameRate = new FrameRate();
	}

	public void onTextureLoading() {
		texture = ImageLoader
				.getImageFromResources("\\resources\\image\\tileset.png");
	}

	int season = 0;

	public void onUpdate() {

		processInput();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processInput() {
		mouse.poll();
		if (keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
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
		if (keyboard.isKeyDown(KeyEvent.VK_1)) {
			shopcharges[0]+=100;
		}
		if (keyboard.isKeyDown(KeyEvent.VK_2)) {
			shopcharges[1]+=100;
		}
		if (keyboard.isKeyDown(KeyEvent.VK_3)) {
			shopcharges[2]+=100;
		}
	}

	boolean debug = false;
	int bank = 0;
	int taxs = 0;
	int mayor = 30000;
	// woodcutter,miner and blacksmith
	int[] shops = { 300, 500, 1000 ,1200,5000,2000};
	float rate = 0.05f;
	int[] shopUpkeep = { 200, 400, 200,300,1000,500 };// 800
	int[] shopcharges = { 300, 500, 400,400,2000,700 };// 1300
	String[] shopnames = { "woodcutter", "miner", "blacksmith","tailor" ,"farmer","general shop"};
	boolean[] shopOpened = { true, true, true ,true,true,true};
	String[] shopCloseed = { "","","","","",""};
	public void onCharge() {
		number = 1 + (int) (Math.random() * 100);
		for (int i = 0; i < shops.length; i++) {

			if (shops[i] >= 0 && shopOpened[i]) {
				shops[i] -= shopUpkeep[i];
				mayor += shopUpkeep[i];
			} else if(shopCloseed[i]==""){
				shopCloseed[i] = getCurrentTimeStamp();
				shopOpened[i] = false;
			}
			else
			{
				shops[i] +=10000;
				bank-=10000;
				shopOpened[i] = true;
				shopCloseed[i] = "";
			}
		}
		for (int i = 0; i < shops.length; i++) {
			if (shopOpened[i] && mayor - shopcharges[i] > 0) {
				shops[i] += shopcharges[i];
				mayor -= shopcharges[i];
			}
		}
		for (int i = 0; i < shops.length; i++) {
			if (shopOpened[i]) {
				float amount =(((shops[i] - shopUpkeep[i])*3)* rate)+number;
				shopUpkeep[i] = (int) amount;
				
			} 
		}
		for (int i = 0; i < shops.length; i++) {
			if (shopOpened[i]) {
				float amount =(((shops[i] - shopUpkeep[i])*3)* rate)+number;
				shops[i] -= amount;
				taxs += amount+0.95f;
				
			} else  if(shopCloseed[i]==""){
				shopCloseed[i] = getCurrentTimeStamp();
				shopOpened[i] = false;
			}
			else
			{
				shops[i] +=10000;
				bank-=10000;
				shopOpened[i] = true;
				shopCloseed[i] = "";
			}
		}
		if (mayor < 10000) {
			mayor += taxs;
			taxs = 0;
		}
	}
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}
	int count = 0;
	int number = 0;
	int total = 0;
	int highestTotal = 0;
	Random ran = new Random();

	public void onPaint(Graphics g) {
		count++;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		total = 0;
		for (int i = 0; i < shops.length; i++) {
			total += shops[i];
		}
		total += mayor + taxs;
		if (total > highestTotal) {
			highestTotal = total;
		}
		int temp = (total / shops.length) / 100;
		// rate = (temp+1)/100;
		g.drawString("total:\t" + highestTotal + "(" + total + ")", 100, 50);

		g.drawString("bank:\t" + bank, 100, 70);
		g.drawString("tax:\t" + taxs, 100, 80);
		g.drawString("mayor:\t" + mayor, 100, 100);
		for (int i = 0; i < shops.length; i++) {
			if(shopOpened[i])
			{
				g.drawString(
						shopnames[i] + "(Opened):\t" + shops[i]+"("+shopcharges[i]+")"+"("+shopUpkeep[i]+")",
						100, 120 + (i * 20));
			}
			else
			{
				g.drawString(
						shopnames[i] + "(Closed:"+shopCloseed[i] +"):\t" +"("+shopcharges[i]+")"+"("+shopUpkeep[i]+")",
						100, 120 + (i * 20));
			}
			
		}
		if (count % 2 == 1) {
			onCharge();
		}
		if (count % 100 == 1) {
			highestTotal = 0;

			for (int i = 0; i < shops.length; i++) {
				bank += shops[i];
			}
		}
		frameRate.calculate();
		if (debug) {
			onDebug(g);
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
		g.drawString(count + "", 0, 20);
		for (int i = 0; i < debugLines.size(); i++) {
			g.drawString(debugLines.get(i), 0, 30 + (i * 20));
		}
	}

	public void onClose() {
		System.out.println("Closing");
	}

	static BufferedImage texture;

}
