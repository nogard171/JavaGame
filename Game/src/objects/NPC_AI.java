package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import networking.Locker;

public class NPC_AI {
	public Rectangle bounds = new Rectangle(0, 0, 32, 32);
	public Direction direction = Direction.Right;
	public Player character = new Player();
	private Random random = new Random();
	private BufferedImage texture;

	public NPC_AI(int i, int j) {
		// TODO Auto-generated constructor stub
		bounds.x = i;
		bounds.y = j;
		//removeArea();
		setup();
	}

	public NPC_AI() {
		// TODO Auto-generated constructor stub
		//removeArea();
		setup();
	}
	public void setup()
	{
		timer = new Timer(delay, taskPerformer);
	}
	public void removeArea()
	{
		Locker.map.removeObjectsIn(walkableArea);
	}
	double count = 0;
	int action = 0;
	Point newPoint = new Point(0, 0);
	int speed = 25;
	Rectangle walkableArea = new Rectangle(100, 100, 200, 200);
	public int getAction(int min, int max)
	{
		return min		+ (int) (Math.random() * max);
	}
	public void onUpdate(double delta) {
		character.setTexture(texture);
		character.setBounds(bounds);
		// moveRight(random.nextInt(100));
		character.onUpdate(delta);
		if(!talking)
		{
		if (action == 0) {
			newPoint = new Point(
					walkableArea.x
							+ (int) (Math.random() * walkableArea.width),
							walkableArea.y + (int) (Math.random() * walkableArea.height));
			action = 1;
			//action = getAction(1,2);
		}
		if(character.colliding)
		{
			action = 0;
		}
		if (character.bounds.x < newPoint.x&&action==1) {
			character.moveRight();
		}
		if (character.bounds.x >= newPoint.x&&action==1) {
			character.moveLeft();
		}
		if (character.bounds.y < newPoint.y&&action==1) {
			character.moveDown();
		}
		if (character.bounds.y >= newPoint.y&&action==1) {
			character.moveUp();
		}
		if (bounds.intersects(new Rectangle(newPoint.x, newPoint.y, 32, 32))) {
			removeArea();
			//action = 0;
			timer.start();
		}
		}
	}
	 int delay = 1000; //milliseconds
	ActionListener taskPerformer = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          //...Perform a task...
	    	  action = getAction(0,10);
	      }
	  };
	Timer timer;
	public boolean talking = false;
	public void moveRight(double d) {
		bounds.x += d;
		// character.moveRight();
	}

	public void moveLeft(double amount) {
		bounds.x -= amount;
		// character.moveLeft();
	}

	public void moveDown(double amount) {
		bounds.y += amount;
		// character.moveDown();
	}

	public void moveUp(double amount) {
		bounds.y -= amount;
		// character.moveUp();
	}

	public void onPaint(Graphics g, ImageObserver obj) {
		character.draw(g, obj);
		g.drawRect(newPoint.x, newPoint.y, 32, 32);
		g.drawRect(walkableArea.x, walkableArea.y, walkableArea.width,walkableArea.height);
	}

	public void setTexture(BufferedImage imageFromResources) {
		// TODO Auto-generated method stub
		texture = imageFromResources;
	}
}
