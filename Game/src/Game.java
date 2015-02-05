import input.KeyboardInput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.FrameRate;


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
		
		for(int i =0;i<10;i++)
		{
			Projectile pro = new Projectile();
			projectiles.add(pro);
		}
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
	float speedX = 0;
	float speedY = 0;
	public void onUpdate()
	{
		//System.out.println("looping");
		recOne.x+=speedX;
		recOne.y+=speedY;
		for(int i =0;i<this.projectiles.size();i++)
		{
			projectiles.get(i).onUpdate();
		}
		if(keyboard.isKeyDown(KeyEvent.VK_UP)){
			speedY=-5;
			direction = 3;
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_DOWN)){
			speedY=5;
			direction = 4;
		}else
		{
			speedY=0;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
			speedX=5;
			direction = 1;
		}
		else if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
			speedX=-5;
			direction = 2;
		}
		else
		{
			speedX=0;			
		}
		
		if(keyboard.keyReleased()==KeyEvent.VK_SPACE)
		{
			projectiles.get(proIndex).bounds.x = recOne.x;
			projectiles.get(proIndex).bounds.y = recOne.y;
			projectiles.get(proIndex).shooting = true;
			if(direction==1)
			{
				projectiles.get(proIndex).speedX= 5;
			}
			if(direction==2)
			{
				projectiles.get(proIndex).speedX= -5;
			}
			if(proIndex<projectiles.size()-1)
			{
				proIndex++;
			}
			else
			{
				proIndex=0;
			}
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	int proIndex=0;
	boolean shooting = false;
	int direction = 0;
	int speed= 0;
	Rectangle recOne = new Rectangle(0,0,32,32);
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	Rectangle recThree = new Rectangle(300,100,32,200);
	public void onPaint(Graphics g)
	{
		frameRate.calculate();
		g.setColor(Color.black);
		g.drawString(frameRate.getFrameRate(), 0,10);

		g.fillRect(recOne.x, recOne.y, recOne.width, recOne.height);
		for(int i =0;i<this.projectiles.size();i++)
		{
			if(projectiles.get(i).shooting)
			{
				g.drawRect(projectiles.get(i).bounds.x,projectiles.get(i).bounds.y,projectiles.get(i).bounds.width,projectiles.get(i).bounds.height);
			}
		}
		if(recThree.intersects(recOne))
		{
			g.setColor(Color.red);	
			
			if(direction ==3)
			{
				speedY=0;
				recOne.y+= ((recThree.y+recThree.height)-recOne.y);
			}
			else if(direction ==4)
			{
				speedY=0;
				recOne.y+= ((recThree.y-recOne.height)-recOne.y);
			}
			else if(direction == 2)
			{
				speedX=0;
				recOne.x+= ((recThree.x+recThree.width)-recOne.x);
			}
			else if(direction ==1)
			{
				speedX=0;
				recOne.x+= ((recThree.x-recOne.width)-recOne.x);
			}
			
		}
		for(int i =0;i<this.projectiles.size();i++)
		{
			if(recThree.intersects(projectiles.get(i).bounds))
			{
				g.setColor(Color.red);	
				
				if(direction ==3)
				{
					projectiles.get(i).speedY=0;
					projectiles.get(i).bounds.y+= ((recThree.y+recThree.height)-projectiles.get(i).bounds.y);
				}
				else if(direction ==4)
				{
					projectiles.get(i).speedY=0;
					projectiles.get(i).bounds.y+= ((recThree.y-projectiles.get(i).bounds.height)-projectiles.get(i).bounds.y);
				}
				else if(direction == 2)
				{
					projectiles.get(i).speedX=0;
					projectiles.get(i).bounds.x+= ((recThree.x+recThree.width)-projectiles.get(i).bounds.x);
				}
				else if(direction ==1)
				{
					projectiles.get(i).speedX=0;
					projectiles.get(i).bounds.x+= ((recThree.x-projectiles.get(i).bounds.width)-projectiles.get(i).bounds.x);
				}
				projectiles.get(i).onCollide();
			}
			else
			{
				g.setColor(Color.black);
			}
		}
		
		g.fillRect(recThree.x, recThree.y, recThree.width, recThree.height);
		repaint();
	}
	public void onClose()
	{
		System.out.println("Closing");
	}
}
