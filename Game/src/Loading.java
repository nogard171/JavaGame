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

public class Loading extends JFrame {

	Dimension dim;
	public BufferedImage texture;
	FrameRate frameRate;
	public Loading() {
	
		// TODO Auto-generated constructor stub
		texture = ImageLoader
				.getImageFromResources("\\resources\\image\\titleset.png");
	}
	public void onUpdate(double d) {
		if(item>=itemCount)
		{
			LOADED = true;
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	int itemCount = 100;
	double item = 0;
	int barSize = 200;
	public void onPaint(Graphics g,double d) {

		
			g.fillRect(0, 0, dim.width, dim.height);
			g.setColor(Color.white);
			g.drawRect(100,100, (barSize/itemCount)*(int)item,32);
			item += d*5;
		repaint();
	}
	
	public void loadClasses(Component c) {
		dim = new Dimension(c.getWidth(), c.getHeight());
		frameRate = new FrameRate();
	}
	boolean LOADED = false;
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
				onPaint(g,d);
			} while (bs.contentsLost());
			bs.show();
		} while (bs.contentsLost());
	}
}
