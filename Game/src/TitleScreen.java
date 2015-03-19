import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;

import networking.Client;
import networking.Locker;
import networking.Server;
import objects.KeyBindings;
import objects.NPC_AI;
import objects.Object;
import objects.Player;
import objects.TextBox;
import util.FrameRate;
import util.ImageLoader;
import util.KeyboardInput;
import util.MouseInput;
import util.Time;

public class TitleScreen extends JFrame {

	Dimension dim;
	public BufferedImage texture;
	FrameRate frameRate;
	KeyboardInput keyboard;
	MouseInput mouse;
	Rectangle menuBounds = new Rectangle(0, 0, 0, 0);
	Rectangle newGameBounds = new Rectangle(0, 0, 130, 32);
	Rectangle exitBounds = new Rectangle(0, 0, 50, 32);
	public String username = "";
	public TitleScreen() {
	username = System.getProperty("user.name");
		// TODO Auto-generated constructor stub
		texture = ImageLoader
				.getImageFromResources("\\resources\\image\\titleset.png");
	}

	double x = 0;
	Time time = new Time();

	public void onUpdate(double d) {
		exitBounds = new Rectangle(dim.width - 100,dim.height- exitBounds.height*2, exitBounds.width,exitBounds.height);
		processInput(d);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onPaint(Graphics g) {

		g.drawImage(
				ImageLoader.getImageFromResources("\\resources\\image\\bg.jpg"),
				0, 0, dim.width, dim.height, null);
		
		g.setFont(new Font("arial",Font.PLAIN,24));
		g.setColor(new Color(224,224,224, 128));
		String data = "User:"+username;
		g.drawString(data, (dim.width/2)-(data.length()*6),dim.height/2);
		g.setFont(new Font("arial",Font.PLAIN,12));
		g.fillRoundRect(menuBounds.x, menuBounds.y, menuBounds.width,
				menuBounds.height, 20, 20);
		g.setColor(new Color(224, 224, 224, 128));
		g.setFont(new Font("arial", 1, 20));
		
		drawButton(g,this.newGameBounds,"Enter World",action);
		drawButton(g,this.exitBounds,"Exit",exitAction);
		repaint();
	}
	int exitAction = 0;
	public void drawButton(Graphics g, Rectangle buttonBounds,String text, int act)
	{
		g.drawImage(texture.getSubimage(0, act*32, 128, 32), buttonBounds.x
				+ (int) x, buttonBounds.y,buttonBounds.width,buttonBounds.height, null);
		g.drawString(text, buttonBounds.x + (int) x+8, buttonBounds.y+(act*3)
				+ (buttonBounds.height-12));
	}
	int action = 0;
	public void loadClasses(Component c) {
		dim = new Dimension(c.getWidth(), c.getHeight());
		keyboard = new KeyboardInput(c);
		mouse = new MouseInput();
		frameRate = new FrameRate();
	}

	public void processInput(double delta) {
		// System.out.println(mouse.getPosition());
		mouse.poll();

		
		if (newGameBounds.intersects(mouse.getPosition().x, mouse.getPosition().y,1,1) && mouse.buttonDown(1)) {
			action=1;	
			OK = true;
		}
		else
		{
			action = 0;
		}
		if (exitBounds.intersects(mouse.getPosition().x, mouse.getPosition().y,1,1) && mouse.buttonDown(1)) {
			exitAction=1;	
			System.exit(1);
		}
		else
		{
			exitAction = 0;
		}
	}
	boolean OK = false;
	public void gameLoop(double d, BufferStrategy bs) {

		do {
			do {
				Graphics g = null;
				if (bs == null) {
					g = createImage(dim.width, dim.height).getGraphics();
				} else {
					g = bs.getDrawGraphics();
				}
				g.clearRect(0, 0, getWidth(), getHeight());
				onUpdate(d);
				onPaint(g);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}

	public void onSetup() {
		// TODO Auto-generated method stub

	}
}
