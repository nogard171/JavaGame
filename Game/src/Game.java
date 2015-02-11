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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

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


public class Game extends JFrame implements Runnable{

	public Game()
	{
		title = new TitleScreen();
		loadClasses();
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
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphicsDevice = ge.getDefaultScreenDevice();
		
		if(fullscreen)
		{
			setUndecorated(true);
			
			
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
		/*ArrayList<MenuItem> items = new ArrayList<MenuItem>();
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
		menu.windowPosition=new Point((width/2)-menu.dim.width,(height/2)-menu.dim.height);
		menu.setPosition(new Point((width/2)-menu.dim.width,(height/2)-menu.dim.height));
		menu.onLoad(items);		*/
		
		ui.dim = new Dimension(32*6,32*7);
		ui.setWindowPosition(new Point(32,(height)-(32*7)));
		ui.usesWindow = true;
		ui.setPosition(new Point(0,(height)-(32*7)));
		ArrayList<MenuItem> uiItems = new ArrayList<MenuItem>();
		MenuItem bag = new MenuItem();
		bag.description = "This will show the players inventory.";
		bag.setTag("bag");
		bag.setBounds(new Rectangle(0,0,32,32));
		MenuItem chara = new MenuItem();
		chara.description = "This will show the players stats and related info.";
		chara.setTag("chara");
		chara.setBounds(new Rectangle(0,32,32,32));
		MenuItem equip = new MenuItem();
		equip.description = "This will show the players equipment.";
		equip.setTag("equip");
		equip.setBounds(new Rectangle(0,64,32,32));
		MenuItem magic = new MenuItem();
		magic.description = "This will show the players magic skills.";
		magic.setTag("magic");
		magic.setBounds(new Rectangle(0,96,32,32));
		
		
		MenuItem hideShow = new MenuItem();
		hideShow.description = "This will hide the Interface";
		hideShow.setHoverable(false);
		hideShow.setTag("hide");
		hideShow.setBounds(new Rectangle(0,-(height-ui.getPosition().y),32,32));
		MenuItem exit = new MenuItem();
		exit.description = "You must click exit twice to quit the game.";
		exit.setTag("exit");
		exit.setBounds(new Rectangle(0,-(height-ui.getPosition().y)-192,32,32));
		
		
		uiItems.add(bag);
		uiItems.add(chara);
		uiItems.add(equip);
		uiItems.add(magic);
		uiItems.add(hideShow);
		uiItems.add(exit);
		ui.onLoad(uiItems);

		Item item = new Item();
		item.setCount(1);
		item.setName("test");
		item.setPosition(5, 5);
		item.setType(ItemType.Log);
		item.setTexture(ImageLoader.getImageFromResources("\\resources\\image\\itemset.png").getSubimage(0, 32, 32, 32));
		Locker.player.inventory.add(item);
		
		
	}
	boolean menuShown = false;
	boolean titleScreen = false;
	TitleScreen title;
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
	public void loadClasses()
	{

		keyboard = new KeyboardInput(this);
		mouse = new MouseInput();
		ui = new Interface();
		menu = new Interface();
		frameRate = new FrameRate();
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
		title.dim = new Dimension(this.getWidth(),this.getHeight());
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
		if(!titleScreen)
		{
			if(!menuShown)
			{
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

				ui.onInput(mouse);
				
				if(keyboard.isKeyDown(KeyEvent.VK_ALT))
				{
					for(MenuItem item:ui.menuItems)
					{
						if(item.isHovered)
						{
							System.out.println(item.description);
							ui.hoverPoints = new Point(item.getBounds().x,item.getBounds().y);
							ui.hoverDescription = item.description;
							break;
						}
						else
						{
							ui.hoverDescription = "";
						}
					}
				}
				else
				{
					ui.hoverDescription = "";
				}
			}
			else
			{
				menu.onInput(mouse);
			}
			if(keyboard.isKeyDown(KeyEvent.VK_F1))
			{
				debug = !debug;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(keyboard.isKeyDown(KeyEvent.VK_F2))
			{

				server = new Server();
				
				if(server!=null&&serverStatus)
				{
					Locker.sendLine="shutdown:"+Locker.player.getName();
				}
				else
				{
					server.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				serverStatus = !serverStatus;
				client = new Client();
				if(client!=null&&client.isAlive())
				{
					client.disconnect();
				}
				else
				{
					client.username = Locker.player.getName();
					client.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(client.connected)
					{

						clientStatus = client.connected;
					}
				}
			}
			if(keyboard.isKeyDown(KeyEvent.VK_F3)&&!serverStatus)
			{

				client = new Client();
				if(client!=null&&client.isAlive())
				{
					client.disconnect();
				}
				else
				{
					client.username = Locker.player.getName();
					client.start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(client.connected)
					{

						clientStatus = client.connected;
					}
				}
				
			}
			if(keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
			{
				menuShown = true;	
			}
			
		}
		else
		{

			if(new Rectangle(mouse.getPosition().x,mouse.getPosition().y,1,1).intersects(title.newGameBounds))
			{
				//System.out.println("new game hovered");
				if(mouse.buttonDownOnce(1))
				{
					System.out.println("new game clicked");
					//titleScreen = false;
				}
			}
		}

		
		title.onUpdate();
		networkingData();
	}
	boolean serverStatus = false;
	boolean clientStatus = false;
	//the server and client vars
	Server server;
	Client client;
	public void networkingData()
	{
		if(Locker.sendLine!=""&&clientStatus)
		{
				sendMessage(Locker.proticol+":"+Locker.player.getName()+","+Locker.sendLine);
				Locker.sendLine = "";
		}
		if(Locker.recieveLine!="")
		{
			//System.out.println(Locker.recieveLine);
			debugLines.add(Locker.recieveLine);
			Locker.recieveLine = "";
		}
	}
	public void sendMessage(String message)
	{
		try {
			client.sendMessage(client.client,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	boolean debug = false;
	Player player = new Player();
	public void onPaint(Graphics g)
	{
		if(titleScreen)
		{
			title.onPaint(g, this);
		}
		else
		{
			frameRate.calculate();		
			map.onPaint(g, this);
			player.draw(g, this);
			map.onUpperPaint(g, this);
			ui.onPaint(g,this);
			
			
			if(menuShown)
			{
				g.setColor(new Color(0,0,0,128));
				g.fillRect(0, 0, width, height);			
				menu.onPaint(g,this);			
			}
		}
		if(debug)
		{
			onDebug(g);
		}
		repaint();
	}
	Map map = new Map();
	Rectangle rec = new Rectangle(0,20,50,10);
	ArrayList<String> debugLines = new ArrayList<String>();
	public void onDebug(Graphics g)
	{
		int width =150;
		int height = 30+(debugLines.size()*20);
		g.setColor(new Color(0,0,0,128));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(frameRate.getFrameRate(), 0,10);
		g.drawString("Seconds Running" + time.getSeconds(), 0,20);
		for(int i =0;i<debugLines.size();i++)
		{
			g.drawString(debugLines.get(i), 0, 30+(i*20));
		}
	}
	public void onClose()
	{
		System.out.println("Closing");
	}
	static BufferedImage texture;
	
}
