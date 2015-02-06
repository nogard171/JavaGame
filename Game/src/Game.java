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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import objects.*;
import objects.Object;
import util.FrameRate;
import util.ImageLoader;
import util.KeyboardInput;
import util.MouseInput;
import util.Time;


public class Game extends JFrame implements Runnable{

	public Game()
	{
		keyboard = new KeyboardInput(this);
		mouse = new MouseInput();
		ui = new Interface();
		menu = new Interface();
		frameRate = new FrameRate();
	}
	int width = 832;
	int height = 640;
	boolean fullscreen = false;
	FrameRate frameRate;
	KeyboardInput keyboard;
	MouseInput mouse;
	Interface ui;
	Interface menu;
	private GraphicsDevice graphicsDevice;
	private DisplayMode currentDisplayMode;
	private BufferStrategy bs;
	private Thread gameThread;
	public void createAndShowGUI()
	{
		setIgnoreRepaint(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Test title");
		setBackground(Color.white);
		
		if(fullscreen)
		{
			setUndecorated(true);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			graphicsDevice = ge.getDefaultScreenDevice();
			currentDisplayMode = graphicsDevice.getDisplayMode();
			width = graphicsDevice.getDisplayMode().getWidth();
			height = graphicsDevice.getDisplayMode().getHeight();
			
			if(!graphicsDevice.isFullScreenSupported())
			{
				System.err.println("ERROR: Not Supported.");
			}
			
			addKeyListener(new KeyAdapter(){
				
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
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
			//graphicsDevice.setDisplayMode(getDisplayMode());
		}
		else
		{
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
	public void shutdown()
	{
		try
		{
			running = false;
			System.out.println("Game Loop Stopped.");
			if(fullscreen)
			{
				gameThread.join();				
				graphicsDevice.setDisplayMode(this.currentDisplayMode);
				graphicsDevice.setFullScreenWindow(null);
				System.out.println("Display Restored.");
			}			
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
	public DisplayMode getDisplayMode()
	{
		return new DisplayMode(800,600,32, DisplayMode.REFRESH_RATE_UNKNOWN);
	}
	boolean running = false;
	public void run()
	{
		running = true;
		System.out.println("game running");
		frameRate.initialize();
		onSetup();
		while(running)
		{
			gameLoop();
		}
	}
	public void onSetup() 
	{
		onTextureLoading();
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		MenuItem resume = new MenuItem();
		resume.setTag("resume");
		resume.isImage = false;
		resume.setBounds(new Rectangle(menu.getPosition().x,menu.getPosition().y,70,15));
		items.add(resume);
		MenuItem options = new MenuItem();
		options.setTag("options");
		options.isImage = false;
		options.setBounds(new Rectangle(menu.getPosition().x,menu.getPosition().y+20,70,15));
		items.add(options);
		MenuItem exit = new MenuItem();
		exit.setTag("exit");
		exit.isImage = false;
		exit.setBounds(new Rectangle(menu.getPosition().x,menu.getPosition().y+40,70,15));
		items.add(exit);
		menu.usesWindow = true;
		menu.dim = new Dimension(75,90);
		menu.onLoad(items);		
	}
	boolean menuShown = false;
	public void gameLoop()
	{
		do
		{
			do
			{
				Graphics g = null;
				if(bs ==null)
				{
					g = createImage(width,height).getGraphics();
				}
				else
				{
					g = bs.getDrawGraphics();
				}
				g.clearRect(0, 0, getWidth(), getHeight());
				onUpdate();
				onPaint(g);
			}while(bs.contentsLost());
			bs.show();
		}while(bs.contentsLost());
	}
	public void onTextureLoading()
	{
		texture = ImageLoader.getImageFromResources("\\resources\\image\\tileset.png");
		player.setTexture(ImageLoader.getImageFromResources("\\resources\\image\\playerset.png"));
	}
	int season =0;
	Time time = new Time();
	public void onUpdate()
	{
		
		//System.out.println("looping");
		player.onUpdate();
		// TODO Auto-generated method stub
		map.checkCollision(player);
		time.onUpdate();
		map.onUpdate();
		
		processInput();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void processInput()
	{
		mouse.poll();
		
		if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
			player.moveRight();
			
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
			player.moveLeft();
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_UP)){
			player.moveUp();
			
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_DOWN)){
			player.moveDown();
			
		}
		
		
		if(keyboard.isKeyDown(KeyEvent.VK_0))
		{
			season=0;		
		}
		if(keyboard.isKeyDown(KeyEvent.VK_1))
		{
			season=1;		
		}
		if(keyboard.isKeyDown(KeyEvent.VK_2))
		{
			season=2;		
		}
		if(keyboard.isKeyDown(KeyEvent.VK_3))
		{
			season=3;		
		}
		if(keyboard.isKeyDown(KeyEvent.VK_SHIFT))
		{
			player.dash();	
		}
		else
		{
			player.stopDashing();
		}
		if(keyboard.isKeyDown(KeyEvent.VK_SPACE))
		{
			keyboard.currentKey=-1;			
		}
		if(keyboard.isKeyDown(KeyEvent.VK_F1))
		{
			debug = !debug;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
		{
			menuShown = true;	
		}
		for(MenuItem item:menu.menuItems)
		{
			if(item.isClicked&&item.getTag()=="exit")
			{
				shutdown();
			}
		}
		ui.onInput(mouse);
		menu.onInput(mouse);
	}
	boolean debug = false;
	Player player = new Player();
	public void onPaint(Graphics g)
	{
		frameRate.calculate();
		
		map.onPaint(g, this);
		player.draw(g, this);
		map.onUpperPaint(g, this);

		ui.onPaint(g,this);
		if(menuShown)
		{
			menu.onPaint(g,this);
		}
		if(debug)
		{
			onDebug(g);
		}
		repaint();
	}
	Map map = new Map();
	Rectangle rec = new Rectangle(0,20,50,10);
	public void onDebug(Graphics g)
	{
		int width =150;
		int height = 30;
		g.setColor(new Color(0,0,0,128));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(frameRate.getFrameRate(), 0,10);
		g.drawString("Seconds Running" + time.getSeconds(), 0,20);
		g.drawString("Exit", 0,30);
		g.drawRect(rec.x, rec.y, rec.width, rec.height);
	}
	public void onClose()
	{
		System.out.println("Closing");
	}
	static BufferedImage texture;
	
}
