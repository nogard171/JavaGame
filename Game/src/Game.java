import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	public Game() {
	}

	int width = 832;
	int height = 640;
	private GraphicsDevice graphicsDevice;
	private DisplayMode currentDisplayMode;
	private BufferStrategy bs;
	private Thread gameThread;

	public void createAndShowGUI() {
		setVisible(true);
	}

	public void update(Graphics g) {
		paint(g);
	}

	  private Image mImage;
	public void paint(Graphics g) {
		Dimension d = getSize();
	    checkOffscreenImage();
	    Graphics offG = mImage.getGraphics();
	    offG.setColor(getBackground());
	    offG.fillRect(0, 0, d.width, d.height);
	    // Draw into the offscreen image.
	    paintOffscreen(mImage.getGraphics());
	    // Put the offscreen image on the screen.
	    g.drawImage(mImage, 0, 0, null);
	}
	private void checkOffscreenImage() {
	    Dimension d = getSize();
	    if (mImage == null || mImage.getWidth(null) != d.width
	        || mImage.getHeight(null) != d.height) {
	      mImage = createImage(d.width, d.height);
	    }
	  }

	  public void paintOffscreen(Graphics g) {
	    g.drawString("test", 100, 100);
	  }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			repaint();
		}
	}

}
