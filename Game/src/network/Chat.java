package network;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import util.InputHandler;

public class Chat implements KeyListener, MouseListener, MouseMotionListener {
	public Point position = new Point(0, 0);
	public Dimension dim = new Dimension(200, 100);
	public String input = "";
	InputHandler keyboard;

	public Chat(Component c) {
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		keyboard = new InputHandler(c);
	}

	int start = 0;
	public void update()
	{
		scrollUp = new Rectangle(position.x+dim.width, position.y, 16, 16);
		scrollDown = new Rectangle(position.x+dim.width, position.y+dim.height, 16, 16);
		scrollBar = new Rectangle(position.x+dim.width, scrollBar.y, 16, scrollBar.height);
		scrollBar = new Rectangle(position.x+dim.width, position.y+16+(int)(((float)(dim.height-(10+scrollBar.height))/(float)(Lines.size()+1))*((float)start)), 16, 16);
		
		if(Lines.size()>6&&start<Lines.size()-6&&autoScroll)
		{
			start++;
		}
		if(start>=Lines.size()-6&&!autoScroll)
		{
			autoScroll = true;
		}
		scrolling();
	}
	boolean autoScroll = true;
	public void draw(Graphics g) {
		update();
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(position.x, position.y, dim.width, dim.height);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, dim.width, dim.height);
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(position.x, position.y + dim.height, dim.width, 16);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y + dim.height, dim.width, 16);
		g.setColor(Color.white);
		g.drawString(Locker.username + ":" + input, position.x + 2, position.y
				+ dim.height + 12);
		g.setColor(Color.black);
		g.setColor(Color.white);
		for (int i = start; i <start+ 6; i++) {
			if (i < Lines.size()) {
				g.setColor(Color.white);
				g.drawString(Lines.get(i), position.x + 2, position.y + 12
						+ ((i-start) * 15));
				g.setColor(Color.black);
			}
		}
		System.out.println(scrollBar.height);
		g.setColor(Color.white);
		g.drawString(Locker.username + ":" + input, position.x + 2, position.y
				+ dim.height + 12);
		g.setColor(Color.black);
		
		g.drawRect(scrollUp.x,scrollUp.y,scrollUp.width,scrollUp.height);
		g.drawRect(scrollDown.x,scrollDown.y,scrollDown.width,scrollDown.height);
		g.drawRect(scrollBar.x,scrollBar.y,scrollBar.width,scrollBar.height);
		g.setColor(Color.black);
	}
	Rectangle scrollUp = new Rectangle(position.x+dim.width, position.y, 16, 16);
	Rectangle scrollDown = new Rectangle(position.x+dim.width, position.y+dim.height, 16, 16);
	Rectangle scrollBar = new Rectangle(position.x+dim.width, position.y+16, 16, dim.height-16);
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (focused) {
			// TODO Auto-generated method stub
			Pattern p = Pattern.compile("[^a-zA-Z0-9]");
			String temp = arg0.getKeyChar() + "";
			if (keyboard.isKeyDown(KeyEvent.VK_ENTER)&&input !="") {
				Locker.proticol = "message";
				Locker.sendLine = input;
				input = "";
			}
			else if (keyboard.isKeyDown(KeyEvent.VK_BACK_SPACE)
					&& input.length() > 0) {
				input = input.substring(0, input.length() - 1);
			}
			else if (!temp.contains("/") && !temp.contains("\\")) {
				input += arg0.getKeyChar();
			}
		}
	}

	boolean focused = false;
	public ArrayList<String> Lines = new ArrayList<String>();
	boolean middleMouse = false;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getButton() == 1) {

			if (arg0.getX() > position.x
					&& arg0.getX() < position.x + dim.width
					&& arg0.getY() > position.y
					&& arg0.getY() < position.y + dim.height + 32) {
				focused = true;
			} else {
				focused = false;
			}
			
		}
		
	}
	boolean leftMouse = false;
	public void scrolling()
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	Point clicked = new Point(0, 0);

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getX() > position.x && arg0.getX() < position.x + dim.width
				&& arg0.getY() > position.y
				&& arg0.getY() < position.y + dim.height + 32) {
			if (arg0.getButton() == 2) {
				clicked = new Point(arg0.getX() - position.x, arg0.getY()
						- position.y);
				middleMouse = true;
			}
		}
		if(arg0.getButton() == 1)
		{
			if (scrollUp.intersects(new Rectangle(arg0.getX(),arg0.getY(),1,1))&&start>0) {
				start--;
				System.out.println("clicked scrollup");
				autoScroll = false;
			}
			if (scrollDown.intersects(new Rectangle(arg0.getX(),arg0.getY(),1,1))&&start<Lines.size()-6) {
				start++;
				System.out.println("clicked scrolldown");
				autoScroll = false;
				
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getButton() == 2) {
			middleMouse = false;
		}
		if(arg0.getButton() ==1)
		{
			 leftMouse = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (middleMouse) {
			position = new Point(arg0.getX() - clicked.x, arg0.getY()
					- clicked.y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
