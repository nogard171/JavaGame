package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

import network.Locker;

public class Player {
	// the players name
	private String name = "test";
	// the players texture
	private BufferedImage texture;
	// the players current frame
	private BufferedImage frame;
	//ths players velocity
	public Point velocity = new Point(200,200);
	// the players position
	public float positionX = 0;
	public float positionY = 0;
	public float frameX = 0;
	public float frameY = 0;

	// return the current frame
	public BufferedImage getFrame() {
		return this.frame;
	}

	// this sets the frames points
	public void setFramePoints(int x, int y) {
		this.frameX= x;
		this.frameY= y;
	}

	// this returns the players position
	public Point getPosition() {
		return new Point((int)positionX,(int)positionY);
	}

	// sets the players texture
	public void setTexture(BufferedImage newTexture) {
		this.texture = newTexture;
	}

	// this sets the players position to the newPoint
	public void setPosition(Point newPoint) {
		this.positionX = newPoint.x;
		this.positionY = newPoint.y;
	}

	// this sets the players position to the ints
	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}

	// this returns the players name
	public String getName() {
		return this.name;
	}

	// this sets the players name
	public void setName(String newName) {
		this.name = newName;
	}

	// this updates the players frame
	public void updateFrame() {
		frame = texture.getSubimage((int)frameX * 32, (int)frameY * 32, 32,
				32);
	}

	public void draw(Graphics g) {
		updateFrame();
		g.drawImage(frame, getPosition().x, getPosition().y, 32, 32, null);
		if (Locker.grid) {
			g.drawRect(getPosition().x,getPosition().y, 32, 32);
		}
	}
}
