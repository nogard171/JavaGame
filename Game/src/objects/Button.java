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
	public String backGroundLocation = "/resources/images/button_set.png";
	public String imageLocation = "";
	public Dimension backGroundDim = new Dimension(10, 10);
	public boolean isClickable = true;
	boolean autoTexture = false;
	public Button(Component c, boolean auto) {
		c.addMouseListener(this);
		autoTexture = auto;
		setup();
	}

	public Button(Component c) {
		c.addMouseListener(this);
		setup();
	}

	Font font;

	public void setup() {
		if (autoTexture) {
			texture = TextureHandler.textureLoad(backGroundLocation);
			if (imageLocation != "") {
				texture = TextureHandler.textureLoad(imageLocation);
			}
		}
		
		graphics.clear();
		graphics.addAll(generateButton(texturePoints.x, texturePoints.y,
				dim.width, dim.height));
		if (isClickable) {
			graphics.addAll(generateButton(texturePoints.x - 64,
					texturePoints.y, dim.width, dim.height));
		}
	}

	public ArrayList<BufferedImage> generateButton(int xtemp, int ytemp,
			int width, int height) {
		ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
		int x = 0;
		int y = 0;
		int numX = width / backGroundDim.width;
		int numY = height / backGroundDim.height;
		for (int i = 0; i < numX * numY; i++) {
			if (y == 0) {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * backGroundDim.width), ytemp
							+ (int) ((float) 0 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * backGroundDim.width) + 2,
							ytemp + (int) ((float) 0 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * backGroundDim.width), ytemp
							+ (int) ((float) 0 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				}

			} else if (y == numY - 1) {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * backGroundDim.width), ytemp
							+ (int) ((float) 2 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * backGroundDim.width) + 2,
							ytemp + (int) ((float) 2 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * backGroundDim.width), ytemp
							+ (int) ((float) 2 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				}

			} else {
				if (x == 0) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 0 * backGroundDim.width), ytemp
							+ (int) ((float) 1 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else if (x == numX - 1) {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 2 * backGroundDim.width) + 2,
							ytemp + (int) ((float) 1 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
					temp.add(img);
				} else {
					BufferedImage img = texture.getSubimage(xtemp
							+ (int) ((float) 1 * backGroundDim.width), ytemp
							+ (int) ((float) 1 * backGroundDim.height),
							backGroundDim.width, backGroundDim.height);
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
			int x = 0;
			int y = 0;
			int numX = dim.width / backGroundDim.width;
			int numY = dim.height / backGroundDim.height;
			for (int i = 0; i < numX * numY; i++) {

				g.drawImage(graphics.get(i + (state * (numX * numY))),
						position.x + (x * backGroundDim.width), position.y
								+ (y * backGroundDim.height), null);
				if (x >= numX - 1) {
					y++;
					x = 0;
				} else {
					x++;
				}
			}
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
