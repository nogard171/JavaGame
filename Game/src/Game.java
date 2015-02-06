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
import util.Time;


public class Game extends JFrame implements Runnable{

	public Game()
	{
		keyboard = new KeyboardInput(this);
		frameRate = new FrameRate();
	}
	int width = 832;
	int height = 640;
	boolean fullscreen = false;
	FrameRate frameRate;
	KeyboardInput keyboard;
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
		
			graphicsDevice.setFullScreenWindow(this);
			
			createBufferStrategy(2);
			bs = getBufferStrategy();
			//graphicsDevice.setDisplayMode(getDisplayMode());
		}
		else
		{
			Canvas canvas = new Canvas();
			canvas.setSize(width, height);
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
			gameThread.join();
			System.out.println("Game Loop Stopped.");
			graphicsDevice.setDisplayMode(this.currentDisplayMode);
			graphicsDevice.setFullScreenWindow(null);
			System.out.println("Display Restored.");
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
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
		
	}
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
		
		
		if(keyboard.currentKey == KeyEvent.VK_0)
		{
			season=0;		
		}
		if(keyboard.currentKey == KeyEvent.VK_1)
		{
			season=1;		
		}
		if(keyboard.currentKey == KeyEvent.VK_2)
		{
			season=2;		
		}
		if(keyboard.currentKey == KeyEvent.VK_3)
		{
			season=3;		
		}
		if(keyboard.currentKey == KeyEvent.VK_SHIFT)
		{
			player.dash();	
		}
		if(keyboard.currentKey == KeyEvent.VK_SPACE)
		{
			keyboard.currentKey=-1;			
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.checkCollision(player);
		time.onUpdate();
		map.onUpdate();
	}
	
	Player player = new Player();
	Object recThree = new Object(new Rectangle(200,100,32,32));
	public void onPaint(Graphics g)
	{
		frameRate.calculate();
		
		map.onPaint(g, this);
		player.draw(g, this);
		map.onUpperPaint(g, this);
		
		onDebug(g);
		repaint();
	}
	Map map = new Map();
	public void onDebug(Graphics g)
	{
		int width =150;
		int height = 30;
		g.setColor(new Color(0,0,0,128));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(frameRate.getFrameRate(), 0,10);
		g.drawString("Seconds Running" + time.getSeconds(), 0,20);
	}
	public void onClose()
	{
		System.out.println("Closing");
	}
	static BufferedImage texture;
	
}
