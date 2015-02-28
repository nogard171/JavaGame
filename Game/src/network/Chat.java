package network;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import util.InputHandler;

public class Chat implements KeyListener, MouseListener {
	public Point position = new Point(100,100);
	public Dimension dim = new Dimension(200, 100);
	public String input = "";
	InputHandler keyboard;
	public Chat(Component c)
	{
		c.addKeyListener(this);
		c.addMouseListener(this);
		 keyboard = new InputHandler(c);
	}
	int start = 0;
	public void draw(Graphics g) {
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
		for(int i=start;i<10;i++)
		{
			if(i+start<Lines.size())
			{
				g.setColor(Color.white);
				g.drawString(Lines.get(i), position.x + 2, position.y+ 12+(i*15));
				g.setColor(Color.black);
			}
		}
		g.drawString(Locker.username + ":" + input, position.x + 2, position.y
				+ dim.height + 12);
		g.setColor(Color.black);
	}

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
			if (!temp.matches("^.*[^a-zA-Z0-9 ].*$") && !temp.contains("/")
					&& !temp.contains("\\")) {
				input += arg0.getKeyChar();
			}
			if(keyboard.isKeyDown(KeyEvent.VK_ENTER))
			{
				Locker.proticol="message";
				Locker.sendLine = input;
				input = "";
			}
			if(keyboard.isKeyDown(KeyEvent.VK_BACK_SPACE)&&input.length()>0)
			{
				input = input.substring(0, input.length()-1);
			}
		}
	}

	boolean focused = false;
	public ArrayList<String> Lines = new ArrayList<String>();

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getX() > position.x && arg0.getX() < position.x + dim.width&&
				arg0.getY() > position.y && arg0.getY() < position.y + dim.height+32) {
			focused = true;
		} else {
			focused = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
