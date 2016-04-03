package util;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.*;

//the input handling class in which uses keylistener
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	// declare the array of if keys are down or not
	private boolean[] keys;
	private static final int BUTTON_COUNT = 3;

	private Point mousePos;
	private Point currentPos;
	private boolean[] mouse;
	private int[] polled;
	private int notches;
	private int polledNotches;
	public int keyHit = -1;

	// the constructor for the input handler class
	public InputHandler(Component c) {
		// adds the default false value to all the keys in the array
		keys = new boolean[256];
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		mouse = new boolean[BUTTON_COUNT];
		polled = new int[BUTTON_COUNT];
		// set the keylistener to this class
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addMouseWheelListener(this);
	}

	// if key is down return the keycode
	public boolean isKeyDown(int keyCode) {
		if (keyCode > 0 && keyCode < 256) {
			return keys[keyCode];
		}
		return false;
	}

	// if key is down return the keycode
	public boolean isKeyUp(int keyCode) {
		if (keyCode > 0 && keyCode < 256) {
			return keys[keyCode];
		}
		return false;
	}

	// if key is being pressed, set the boolean for the specific key
	// to true
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = true;
			keyHit = e.getKeyCode();
		}

	}

	// if the key is released, set the boolean for the specific key
	// to false
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = false;
		}
	}

	// if a key is just typed do nothing
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public synchronized void poll() {

		mousePos = new Point(currentPos);
		polledNotches = notches;
		notches = 0;

		for (int i = 0; i < mouse.length; ++i) {
			if (mouse[i]) {
				polled[i]++;
			} else {
				polled[i] = 0;
			}
		}
	}

	public Point getPosition() {
		return mousePos;
	}

	public int getNotches() {
		return polledNotches;
	}

	public boolean buttonDown(int button) {
		return polled[button - 1] > 0;
	}

	public boolean buttonDownOnce(int button) {
		return polled[button - 1] == 1;
	}

	public synchronized void mousePressed(MouseEvent e) {
		int button = e.getButton() - 1;
		if (button >= 0 && button < mouse.length) {
			mouse[button] = true;
		}
	}

	public synchronized void mouseReleased(MouseEvent e) {
		int button = e.getButton() - 1;
		if (button >= 0 && button < mouse.length) {
			mouse[button] = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		// Not needed
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}

	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		notches += e.getWheelRotation();
	}
}
