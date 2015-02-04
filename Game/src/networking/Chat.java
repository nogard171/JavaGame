package networking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import objects.TextBox;

public class Chat implements KeyListener,MouseListener{
	private Rectangle Bounds = new Rectangle(0,0,300,200);
	public ArrayList<String> Lines = new ArrayList<String>();
	private Color BackColor = Color.black;
	private int Transparency = 128;
	TextBox text = new TextBox();
	public Chat()
	{
		
	}
	int currentIndex = 0;
	public void drawChat(Graphics g, ImageObserver obj,Dimension dim, Insets inset)
	{
		//set the textboxs bounds
		text.setBounds(new Rectangle(0,dim.height-47-inset.bottom,Bounds.width-1,20));
		//draw the background of the chat window
		g.setColor(new Color(BackColor.getRed(),BackColor.getGreen(),BackColor.getBlue(),this.Transparency));
		g.fillRect(0,dim.height-Bounds.height-inset.bottom,Bounds.width, Bounds.height);
		//draw the textbox.
		text.draw(g);
		//draw the lines of the text box
		for(int i=currentIndex;i<14;i++)
		{
			if(i<Lines.size())
			{
				g.drawString(Lines.get(i),0,dim.height-Bounds.height-inset.bottom+(i*10)+20);
			}
		}
	}
	
	//this sets the bounds
	public void setBounds(int x, int y, int width,int height)
	{
		this.Bounds = new Rectangle(x,y,width,height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		text.keyPressed(e);
		//if enter was pressed add the textbox's text to lines
		if(e.getKeyChar()=='\n')
		{
			if(!text.Text.startsWith("/"))
			{
				Locker.sendLine = text.Text;
				Locker.proticol = "message";
				text.Text ="";
			}
			else
			{
				if(text.Text.startsWith("/name="))
				{
					String name = text.Text.substring(text.Text.indexOf('=')+1,text.Text.length());
					Locker.sendLine = name;
					Locker.proticol = "name";
					Locker.username = name;
					text.Text ="";
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//calls the textbox isclicked
		text.isClicked(arg0);
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
