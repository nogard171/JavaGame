import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;






import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import objects.Player;
import network.Client;
import network.Locker;
import network.Server;


/*
 * Main Class For Game
 */
public class Main extends JFrame  
{
	//this indicates if the game is running or not
	boolean isRunning = true;
	//this statically sets the fps for the game
	int fps = 60;
	//this is the dimenions for the game
	int windowWidth = 800;
	int windowHeight = 600;
	//this is the backimage in which is drawn on first
	BufferedImage backBuffer;
	//debugging boolean
	private boolean debugging = false;
	//the debug window position
	private Point debugPosition = new Point(0,0);
	//this is the servers status, if it started
	boolean serverStatus = false;
	//this is the clients status, if it connected to a server or not
	boolean clientStatus = false;	

	FrameRate frameRate;
	//this declares the input object
		InputHandler input;
	//the server and client vars
	Server server;
	Client client;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//this is the main function in which gets executed
	//during a java application launch
	public static void main(String[] args) 
	{
		//this declares the main object
		Main game = new Main();
		//this tells the console the game is running
		System.out.println("Game running!");
		//this starts running the game
		game.run();
		//this exits the java application when the running is complete
		System.exit(0);
	}
	//this function starts the primary game loop
	public void run()
	{	
		//this call sets up the games dimensions and display
		setup();
		//this is the primary game loop
		while(isRunning)
		{
			//this gets the current ms
			long time = System.currentTimeMillis();
			//this calls the update function
			update();
			//this calls the draw function
			draw();
			//delay for each frame - time it took for one frame
			time = (1000 / fps) - (System.currentTimeMillis() - time);
			if (time > 0)
			{
				try
				{
					Thread.sleep(time);
				}
				catch(Exception e){}
			}
		}
		//this sets visibility
		setVisible(false);
	}
	//this is the setup function
	void setup()
	{
		//set the windows title
		setTitle("Ybauron: A Tale of Adventure");
		//set the windows screen size
		setSize(windowWidth,windowHeight);
		//set if window is resizable
		setResizable(false);
		//set the default action when exiting
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//set visibility to true
		setVisible(true);
		//set the sides to position things allow the border.
		//set the inputhandler to this
		input = new InputHandler(this);
		//set the backbuffer to a created bufferedimage
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		//set the chat as keylistener
		try {
			Locker.player.setTexture(ImageIO.read(new URL("http://204.237.93.81/resources/images/playerset.png")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Locker.username = "testing";
	}
	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	//this updates the game
	void update()
	{
		//if right arrow is pressed, add to the players x position
		right = input.isKeyDown(KeyEvent.VK_RIGHT);
		//if left arrow is pressed, minus to the players x position
		left = input.isKeyDown(KeyEvent.VK_LEFT);		
		//if up arrow is pressed, minus to the players y position
		up = input.isKeyDown(KeyEvent.VK_UP);
		//if down arrow is pressed, add to the players y position
		down = input.isKeyDown(KeyEvent.VK_DOWN);
		
		if(left||right||up||down)
		{
			Locker.proticol="move";
			Locker.sendLine = left+","+right+","+up+","+down;
		}
		//start the server, then connect a client to it.
		if(input.isKeyDown(KeyEvent.VK_F2))
		{
			//set server to a new Server
			server = new Server();
			//check if the server is null
			if(server!=null&&serverStatus)
			{
				
			}
			else
			{
				//start the server thread
				server.start();
				//delay 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			//toggle serverstatus
			serverStatus = !serverStatus;
			//set the client to a new client
			client = new Client();
			//check if the client is null
			if(client!=null&&client.isAlive())
			{
				//disconnect the client if it's connected.
				client.disconnect();
			}
			else
			{
				//set the client's username to the local player one
				client.username = Locker.username;
				//start the client thread
				client.start();
				//delay 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//if the client is connected set clientstatus to it.
				if(client.connected)
				{
					clientStatus = client.connected;
				}
			}
		}
		//start the client if the server is not started.
		if(input.isKeyDown(KeyEvent.VK_F3)&&!serverStatus)
		{
			
			//set the client to a new client
			client = new Client();
			//check if the client is null
			if(client!=null&&client.isAlive())
			{
				//disconnect the client if it's connected.
				client.disconnect();
			}
			else
			{
				//set the client's username to the local player one
				client.username = Locker.username;
				//start the client thread
				client.start();
				//delay 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//if the client is connected set clientstatus to it.
				if(client.connected)
				{
					clientStatus = client.connected;
				}
			}
		}
		//if escape is pressed continue
		if(input.isKeyDown(KeyEvent.VK_ESCAPE))
		{
		    //call countDownExit function
		    countDownExit(5);
		}
		//check for networking data.
		networkingData();
	}
	public void networkingData()
	{
		//if sendline has data send it to the server.
		if(Locker.sendLine!=""&&clientStatus)
		{
				sendMessage(Locker.proticol+":"+Locker.username+","+Locker.sendLine);
				//set sendline back to nothing
				Locker.sendLine = "";
		}
		//if receiveline has something add it into the chat.
		if(Locker.recieveLine!="")
		{
			//chat.Lines.add(Locker.recieveLine);
			//set the receiveline to nothing
			Locker.recieveLine = "";
		}
	}
	//the sendmessage function to send text to the server
	public void sendMessage(String message)
	{
		try {
			client.sendMessage(client.client,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//count down to 0 from the inputted time
	public void countDownExit(int time)
	{
	    //ouput text
	    System.out.println("Closing in "+time +" Seconds...");
	    try
	    {
		//loop the number of times
		for(int i=time;i>0;i--)
		{
		    //output current time
		    System.out.println(i);
		    //paused the entire thread for 1 second
		    Thread.sleep(1000);
		}
		//output closed
		System.out.println("Closed");
	    	}
	    	catch (InterruptedException e)
	    	{
	    	    // TODO Auto-generated catch block
	    	    e.printStackTrace();
	    	}
	    	//exit the current application
	    	System.exit(0);
	}
	//this draws everything for the game
	void draw()
	{
		//declare the graphics
		Graphics g = getGraphics(); 
        //make graphics based on the backbuffer
        Graphics bbg = backBuffer.getGraphics(); 
        //set the backbuffer color to white
        bbg.setColor(Color.WHITE); 
        //fill a rectangle in the backbuffer
        bbg.fillRect(0, 0, windowWidth, windowHeight);
        bbg.setColor(Color.black); 
        //render the bottom of the map]
        Locker.player.draw(bbg);
        for(Player player:Locker.players)
        {
        	 //render the bottom of the map
            bbg.drawRect(player.position.x, player.position.y, 32,32);
        }
        
        //draw the backbuffer on the graphics
        g.drawImage(backBuffer, 0,0, this);
	}
	
	
}


