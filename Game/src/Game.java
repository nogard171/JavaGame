import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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


public class Game extends JFrame{

	public Game()
	{
		keyboard = new KeyboardInput(this);
		frameRate = new FrameRate();
	}
	int width = 800;
	int height = 600;
	FrameRate frameRate;
	KeyboardInput keyboard;
	public void createAndShowGUI()
	{
		GamePanel gamePanel = new GamePanel();
		gamePanel.setBackground(Color.white);
		gamePanel.setPreferredSize(new Dimension(this.width,this.height));
		getContentPane().add(gamePanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Test title");
		pack();
		
		frameRate.initialize();
		setVisible(true);
		onSetup();
	}
	public void onSetup() 
	{
		texture = ImageLoader.getImageFromResources("\\resources\\image\\tileset.png");
		recOne.lowerType=objects.Type.Player;
	}
	public class GamePanel extends JPanel
	{
		public void paint(Graphics g)
		{
			super.paint(g);
			onUpdate();
			onPaint(g);
		}
	}
	public void onTextureLoading()
	{
		recOne.setTexture(null, objects.Type.getTexture(texture,recOne.lowerType));
	}
	int season =0;
	Time time = new Time();
	public void onUpdate()
	{
		onTextureLoading();
		
		//System.out.println("looping");
		recOne.onUpdate();
		// TODO Auto-generated method stub

		if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
			recOne.moveRight();
			
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
			recOne.moveLeft();
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_UP)){
			recOne.moveUp();
			
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_DOWN)){
			recOne.moveDown();
			
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
		map.checkCollision(recOne);
		time.onUpdate();
		map.onUpdate();
	}
	
	Object recOne = new Object(new Rectangle(0,32,32,32));
	Object recThree = new Object(new Rectangle(200,100,32,32));
	public void onPaint(Graphics g)
	{
		frameRate.calculate();
		
		map.onPaint(g, this);
		recOne.onPaint(g, this);
		g.drawRect(recOne.bounds.x, recOne.bounds.y,recOne.bounds.width,recOne.bounds.height);
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
