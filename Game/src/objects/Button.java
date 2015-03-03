package objects;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import util.TextureHandler;

public class Button extends Item implements MouseListener {

	public BufferedImage image;
	public Point imagePosition = new Point(0, 0);
	public ArrayList<BufferedImage> graphics = new ArrayList<BufferedImage>();
	public Point texturePoints = new Point(96, 64);
	public Dimension dim = new Dimension(50, 20);
	public Graphics solidImage;
	private int state = 0;
	public String text = "test";
	public boolean isDown = false;
	public boolean isUp = false;
	public boolean isClicked = false;
	public String backGroundLocation = "/Game/resources/images/button_set.png";
	public String imageLocation = "";
	public Dimension backGroundDim = new Dimension(16,16);
	public boolean isClickable = true;
	boolean autoTexture = false;

	public Button(Component c, boolean auto) {
		c.addMouseListener(this);
		autoTexture = auto;
		// setup();
	}

	public Button(Component c) {
		c.addMouseListener(this);
		// setup();
	}

	Font font;

	public void setup() {
		if (autoTexture) {
			texture = TextureHandler.textureLoad(backGroundLocation);
			if (imageLocation != "") {
				texture = TextureHandler.textureLoad(imageLocation);
			}
		}
		
		graphics.addAll(getButtonTexture(96,64));
		graphics.addAll(getButtonTexture(32,64));
	}
	public ArrayList<BufferedImage> getButtonTexture(int x, int y)
	{
		 ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
		temp.add(texture.getSubimage(x, y, 10, 32));
		temp.add(texture.getSubimage(x+10, y, 10, 32));
		temp.add(texture.getSubimage(x+22, y, 10, 32));
		return temp;
	}

	public void update() {
		setup();
		if (mouse == 1) {
			System.out.println("Clicked");
			mouse = 0;
		}
	}

	@Override
	public void draw(Graphics g) {
		update();
		if (hasBackground) {
			g.drawImage(graphics.get((state*3)),(int)(position.x), (int)(position.y),null);
			g.drawImage(graphics.get((state*3)+1),(int)(position.x+(10)), (int)(position.y),dim.width,32,null);
			g.drawImage(graphics.get((state*3)+2),(int)(position.x+(dim.width+8)), (int)(position.y),null);
			}
		if (image != null) {
			g.drawImage(image, position.x + (dim.width / 4), position.y
					- (dim.height / 4) + 5, null);
		} else {
			g.setFont(font);
			g.drawString(text, position.x + (dim.width / 8), position.y
					+ (state * 2) + (dim.height / 2) + 8);
		}

		if (isClicked) {
			g.drawString("hello world", mousePoint.x, mousePoint.y);
		}
	}

	public boolean isClicked(Point mousePosition) {
		Rectangle bounds = new Rectangle(position.x, position.y, dim.width,
				dim.height);
		if (bounds.contains(mousePosition) && isClickable) {
			return true;
		} else {
			return false;
		}
	}

	int mouse = -1;
	Point mousePoint = new Point(0, 0);

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isClicked(new Point(arg0.getX(), arg0.getY()))) {
			mouse = arg0.getButton();
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
	}

	boolean isHovered = false;
	public boolean hasBackground = true;

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
		if (isClicked(new Point(arg0.getX(), arg0.getY()))
				&& arg0.getButton() == 1) {
			state = 1;
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isClicked(new Point(arg0.getX(), arg0.getY()))
				&& arg0.getButton() == 1) {
			state = 0;
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
	}
}
