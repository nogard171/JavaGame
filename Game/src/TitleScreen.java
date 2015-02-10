import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import util.ImageLoader;
import util.MouseInput;


public class TitleScreen {

	Dimension dim;
	Rectangle menuBounds = new Rectangle(0,0,0,0);
	Rectangle newGameBounds = new Rectangle(0,0,100,20);
	public TitleScreen()
	{
	}
	
	public void onUpdate()
	{
		menuBounds = new Rectangle((dim.width/2)-(50),(dim.height/2)-100,100,200);
		newGameBounds = new Rectangle(menuBounds.x, menuBounds.y,100,20);
	}
	public void onPaint(Graphics g, ImageObserver obj)
	{
		g.drawImage(ImageLoader.getImageFromResources("\\resources\\image\\bg.jpg"),0,0,dim.width,dim.height,obj);
		g.setColor(new Color(255,255,255,128));
		g.fillRoundRect(menuBounds.x,menuBounds.y,menuBounds.width,menuBounds.height, 20,20);
		g.setColor(new Color(0,0,0,128));
		g.setFont(new Font("arial", 1, 20));
		g.drawString("New Game", newGameBounds.x,newGameBounds.y+newGameBounds.height);
		g.drawRect(menuBounds.x, menuBounds.y,newGameBounds.width,newGameBounds.height);
	}
}
