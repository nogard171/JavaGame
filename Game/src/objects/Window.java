package objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;

import util.TextureHandler;

public class Window implements MouseListener, MouseMotionListener {
	public Point position = new Point(200, 200);
	public BufferedImage texture;
	public ArrayList<BufferedImage> states = new ArrayList<BufferedImage>();
	public Point texturePoints = new Point(64, 384);
	public Dimension dim = new Dimension(200, 200);
	public Graphics solidImage;
	private int state = 0;
	public String text = "test";
	public boolean isDown = false;
	public boolean isUp = false;
	public boolean isClicked = false;
	public String titleString = "";
	Button close;
	Button title;
	public Slot slot = new Slot();

	public Window(Component c, boolean autoTexture) {
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		if (autoTexture) {
			texture = TextureHandler
					.textureLoad("/Game/resources/images/menu_set.png");
		}
		setup(c);
	}

	public Window(Component c) {
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		setup(c);
	}

	public void setup(Component c) {
		close = new Button(c, true);
		close.hasBackground = false;
		close.dim = new Dimension(40, 32);
		close.image = TextureHandler.textureLoad(
				"/Game/resources/images/button_set.png")
				.getSubimage(96, 192, 32, 32);
		title = new Button(c, true);
		title.text = titleString;
		title.dim = new Dimension(40, 32);
		for (int i = 0; i < 2; i++) {
			BufferedImage img = texture.getSubimage(96 - (i * 64), 64, 32, 32);
			states.add(img);
		}
	}

	public ArrayList<BufferedImage> generateWindow(int xtemp, int ytemp,
			int width, int height) {
		ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
		int x = 0;
		int y = 0;
		int numX = width / 16;
		int numY = height / 16;
		for (int i = 0; i < numX * numY; i++) {
			if (y == 0) {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * 16), ytemp
							+ (int) ((float) 0 * 16), 16, 16);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * 16), ytemp
							+ (int) ((float) 0 * 16), 16, 16);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * 16), ytemp
							+ (int) ((float) 0 * 16), 16, 16);
					temp.add(img);
				}

			} else if (y == numY - 1) {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * 16), ytemp
							+ (int) ((float) 2 * 16), 16, 16);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * 16), ytemp
							+ (int) ((float) 2 * 16), 16, 16);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * 16), ytemp
							+ (int) ((float) 2 * 16), 16, 16);
					temp.add(img);
				}

			} else {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * 16), ytemp
							+ (int) ((float) 1 * 16), 16, 16);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * 16), ytemp
							+ (int) ((float) 1 * 16), 16, 16);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * 16), ytemp
							+ (int) ((float) 1 * 16), 16, 16);
					temp.add(img);
				}

			}
			if (x >= numX - 1) {
				y++;
				x = 0;
			} else {
				x++;
			}
		}
		return temp;
	}

	public void update() {
		title.text = titleString;
		close.position = new Point(
				position.x + dim.width - close.dim.width,
				(position.y )-8);
		title.position = new Point(
				position.x ,
				(position.y )-32);
		

		slot.position = new Point(position.x+8,position.y+8);
		Item test = new Item();
		test.texture = TextureHandler.textureLoad("/Game/resources/images/window_set.png").getSubimage(0, 0, 32, 32);
		test.position = new Point(slot.position.x,slot.position.y);
		slot.obj = test;
		if (mouse == 1) {
			mouse = 0;
		}		
	}

	public boolean visible = false;

	public void draw(Graphics g) {
		if (visible) {
			
		
			g.drawImage(texture.getSubimage(0, 0, 128, 128), position.x , position.y,dim.width,dim.height, null);
			close.draw(g);
			title.draw(g);
			slot.draw(g);
			update();
		}
	}

	public boolean isClicked(Point mousePosition) {
		if (visible) {
			Rectangle bounds = new Rectangle(position.x, position.y, dim.width,
					dim.height);
			if (bounds.contains(mousePosition)) {
				return true;
			} else {
				return false;
			}
		}
		else
		{
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
		if (close.isClicked(this.mousePoint)) {
			visible = false;
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
	}

	boolean isHovered = false;

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	Point clicked = new Point(0, 0);
	boolean middleMouse = false;

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isClicked(new Point(arg0.getX(), arg0.getY()))
				&& arg0.getButton() == 2) {
			clicked = new Point(arg0.getX() - position.x, arg0.getY()
					- position.y);
			middleMouse = true;
		}
		if (isClicked(new Point(arg0.getX(), arg0.getY()))
				&& arg0.getButton() == 1) {
			state = 1;
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getButton() == 2) {
			middleMouse = false;
		}
		if (isClicked(new Point(arg0.getX(), arg0.getY()))
				&& arg0.getButton() == 1) {
			state = 0;
		}
		mousePoint = new Point(arg0.getX(), arg0.getY());
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
