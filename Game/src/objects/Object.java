package objects;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import networking.Locker;
import util.Harvester;
import util.KeyboardInput;

public class Object {

	public Rectangle bounds = new Rectangle(0, 0, 32, 32);
	private Point velocity = new Point(2, 2);
	private Direction direction = Direction.Right;
	public Type lowerType = Type.Blank;
	public Type upperType = Type.Blank;
	public Rectangle upperBounds = new Rectangle(0, 0, 32, 32);
	public boolean isVisible = true;
	public BufferedImage lowerTexture;
	public BufferedImage upperTexture;
	public boolean isColliding = false;
	public boolean passable = true;
	public Point framePoints = new Point(0, 0);
	public Dimension specialDimensions = new Dimension(-16, -16);
	public int index = -1;

	public Object(Rectangle rec) {
		this.bounds = rec;
	}

	public Object() {

	}

	public void setUpperTexture(BufferedImage upper) {
		if (upper != null) {
			this.upperTexture = upper;
		}
	}

	public void setTexture(BufferedImage upper, BufferedImage lower) {
		if (lower != null) {
			this.lowerTexture = lower;
		}
		if (upper != null) {
			this.upperTexture = upper;
		}
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public void moveUp() {
		this.bounds.y -= this.velocity.y;
		this.direction = Direction.Up;
		isMoving = true;
	}

	public void moveDown() {
		this.bounds.y += this.velocity.y;
		this.direction = Direction.Down;
		isMoving = true;
	}

	public void moveRight() {
		this.bounds.x += this.velocity.x;
		this.direction = Direction.Right;
		isMoving = true;
	}

	public void moveLeft() {
		this.bounds.x -= this.velocity.x;
		this.direction = Direction.Left;
		isMoving = true;
	}

	boolean isMoving = false;
	public boolean isPushable = false;
	public void onCollide(Player player) {

		// TODO Auto-generated method stub
		if (player.direction == Direction.Up) {
			this.bounds.y -= this.velocity.y;
		} else if (player.direction == Direction.Down) {
			this.bounds.y += this.velocity.y;
		} else if (player.direction == Direction.Left) {
			this.bounds.x -= this.velocity.x;
		} else if (player.direction == Direction.Right) {
			this.bounds.x +=this.velocity.x;
		}
		isColliding = true;
		isMoving = false;
	}
	float gen =0f;
	float genRate = 1.5F;
	float maxGen = 20f;
	public void onUpdate(double delta) {
		if(this.harvested)
		{
			// TODO Auto-generated method stub
			gen += delta * genRate;
			if (gen > maxGen&&!this.isColliding) {
				this.harvested = false;
				this.passable = false;
				gen = 0;
			}
		}
	}

	public Type getLowerType() {
		return this.lowerType;
	}

	public void onPaint(Graphics g, Point position, ImageObserver obj) {
		// TODO Auto-generated method stub
		if (this.isVisible) {
			if (upperType == Type.Blank && harvested) {
				// g.drawImage(lowerTexture,this.bounds.x,this.bounds.y,this.bounds.width-specialDimensions.width,this.bounds.height-specialDimensions.height,obj);
			} else {
				g.drawImage(lowerTexture, this.bounds.x+position.x, this.bounds.y+position.y,
						this.bounds.width - specialDimensions.width,
						this.bounds.height - specialDimensions.height, obj);
			}
		}
	}

	public void onUpperPaint(Graphics g,Point position, ImageObserver obj) {
		// TODO Auto-generated method stub
		if (this.isVisible && upperTexture != null && !harvested) {
			Graphics2D g2d = (Graphics2D) g;
			if (Locker.player.getBounds().x > bounds.x
					- (this.upperBounds.width / 2)+position.x
					&& Locker.player.getBounds().x < bounds.x + bounds.width+position.x
							+ (this.upperBounds.width / 2)&&
							Locker.player.getBounds().y > bounds.y+position.y
							- (this.upperBounds.height-16)
							&& Locker.player.getBounds().y < bounds.y + bounds.height-5+position.y) {
				g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
			}
			g.drawImage(upperTexture, this.bounds.x + this.upperBounds.x + 6+position.x,
					this.bounds.y + this.upperBounds.y+position.y, upperBounds.width
							- specialDimensions.width, upperBounds.height
							- specialDimensions.height, obj);
			g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
		}
	}

	public boolean harvested = false;
	int hits = 5;

	public Item harvest() {
		Item item = null;
		// TODO Auto-generated method stub
		if (hits <= 0) {
			Locker.proticol = "harvest";
			Locker.sendLine = index + "";
			harvested = true;
			Harvester harvest = new Harvester();
			if(upperType!=Type.Blank)
			{
				item = harvest.getObjectItem(upperType);
			}
			else
			{
				item = harvest.getObjectItem(lowerType);
			}
			if (lowerType == Type.Rock) {
				passable = true;
			}
			hits=5;
		} else {
			hits--;
		}
		return item;
	}
}
