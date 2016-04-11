import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Objects.*;
import events.Action;
import util.*;

public class Game2 extends JFrame implements Runnable, WindowListener {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread graphicsThread;
	    private boolean running = false;
	    private BufferStrategy strategy;

	    public Game2() {
	        super("FrameDemo");
	        addWindowListener(this);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        setSize(500, 500);
	        setResizable(true);
	        setVisible(true);

	        createBufferStrategy(2);
	        strategy = getBufferStrategy();

	        running = true;
	        graphicsThread = new Thread(this);
	        graphicsThread.start();
	    }

	    public static void main(String[] args) {
	        new Game2();
	    }

	    public void addNotify() {
	        super.addNotify();
	        createBufferStrategy(2);
	        strategy = getBufferStrategy();
	    }

	    @Override
	    public void run() {
	          // Main loop
	        while (running) {
	            // Prepare for rendering the next frame
	            // ...

	            // Render single frame
	            do {
	                // The following loop ensures that the contents of the drawing buffer
	                // are consistent in case the underlying surface was recreated
	                do {
	                    // Get a new graphics context every time through the loop
	                    // to make sure the strategy is validated
	                    Graphics g = strategy.getDrawGraphics();

	                    g.setColor(Color.GRAY);
	                    g.drawRect(0, 0, 500, 500);
	                    g.setColor(Color.BLACK);
	                    g.drawLine(50, 50, 200, 50);

	                    // Dispose the graphics
	                    g.dispose();

	                    // Repeat the rendering if the drawing buffer contents
	                    // were restored
	                } while (running && strategy.contentsRestored());

	                // Display the buffer
	                strategy.show();

	                // Repeat the rendering if the drawing buffer was lost
	            } while (running && strategy.contentsLost());
	        }
	        setVisible(false);
	        dispose();
	    }

	    @Override
	    public void windowActivated(WindowEvent e) {}
	    @Override
	    public void windowClosed(WindowEvent e) {}
	    @Override
	    public void windowClosing(WindowEvent e) {
	        running = false;
	    }
	    @Override
	    public void windowDeactivated(WindowEvent e) {}
	    @Override
	    public void windowDeiconified(WindowEvent e) {}
	    @Override
	    public void windowIconified(WindowEvent e) {}
	    @Override
	    public void windowOpened(WindowEvent e) {}

}
