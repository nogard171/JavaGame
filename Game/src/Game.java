import input.KeyboardInput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


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
	float speed = 5f;
	public void onUpdate()
	{
		//System.out.println("looping");
	
		if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
			recOne.x+=speed;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
			recOne.x-=speed;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_UP)){
			recOne.y-=speed;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_DOWN)){
			recOne.y+=speed;
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Rectangle recOne = new Rectangle(0,0,32,32);
	Rectangle recTwo = new Rectangle(100,100,32,32);
	Rectangle recThree = new Rectangle(300,100,32,200);
	public void onPaint(Graphics g)
	{
		frameRate.calculate();
		g.setColor(Color.black);
		g.drawString(frameRate.getFrameRate(), 0,10);

		g.fillRect(recOne.x, recOne.y, recOne.width, recOne.height);
		g.drawRect(recTwo.x, recTwo.y-32, recTwo.width, recTwo.height+32);
		g.drawRect(recThree.x, recThree.y, recThree.width, recThree.height);
		if(recTwo.intersects(recOne))
		{
			float kineticEnergyX = ((recTwo.x-recOne.x)/speed)-1.5f;
			float kineticEnergyY =((recTwo.y-recOne.y)/speed)-1.5f;
			recOne.x-=kineticEnergyX;
			recOne.y-=kineticEnergyY;
		}
		if(recThree.intersects(recOne))
		{
			float kineticEnergyX = ((recThree.x-recOne.x)/speed)-1.5f;
			float kineticEnergyY =((recThree.y-recOne.y)/speed)-1.5f;
			recOne.x-=kineticEnergyX;
			recOne.y-=kineticEnergyY;
		}
		
		repaint();
	}
	public void onClose()
	{
		System.out.println("Closing");
	}
}
