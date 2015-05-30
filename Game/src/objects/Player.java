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
	// ths players velocity
	public Point velocity = new Point(100, 100);
	// the players position
	public float positionX = 0;
	public float positionY = 0;
	public float frameX = 0;
	public float frameY = 0;
	public float stamina = 50;
	public float maxStamina = 50;
	public float health = 50;
	public float healthRegen = 0.05f;
	public Player()
	{
		isDead = false;
	}
	public Player(float x, float y)
	{
		positionX = x;
		positionY = y;
		isDead = false;
	}
	public float getHealthRegen() {
		return healthRegen;
	}

	public void setHealthRegen(float healthRegen) {
		this.healthRegen = healthRegen;
	}

	public float maxHealth = 50;
	
	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(float maxStamina) {
		this.maxStamina = maxStamina;
	}

	public float staminaRegen = 0.01f;
	public boolean isDead = false;

	public float getStaminaRegen() {
		return staminaRegen;
	}

	public void setStaminaRegen(float staminaRegen) {
		this.staminaRegen = staminaRegen;
	}

	public float getStamina() {
		return stamina;
	}

	public void setStamina(float stamina) {
		this.stamina = stamina;
	}

	public void addStamina(float stamina) {
		this.stamina += stamina;
	}
	public void minusStamina(double d) {
		// TODO Auto-generated method stub
		stamina -= d;
	}
	
	public void addHealth(float health) {
		this.health += health;
	}
	public void minusHealth(double d) {
		// TODO Auto-generated method stub
		health -= d;
	}

	// return the current frame
	public BufferedImage getFrame() {
		return this.frame;
	}

	// this sets the frames points
	public void setFramePoints(int x, int y) {
		this.frameX = x;
		this.frameY = y;
	}

	// this returns the players position
	public Point getPosition() {
		return new Point((int) positionX, (int) positionY);
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
	// this sets the players position to the ints
		public void setPosition(float x, float y) {
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
		if (texture != null) {
			frame = texture.getSubimage((int) frameX * 32, (int) frameY * 32,
					32, 32);
		}
	}

	public void draw(Graphics g) {
		updateFrame();
		if(isDead)
		{
			g.drawString(getName()+"(Dead)", getPosition().x, getPosition().y - 10);
		}
		else
		{
			g.drawString(getName(), getPosition().x, getPosition().y - 10);
		}
		g.drawImage(frame, getPosition().x, getPosition().y, 32, 32, null);
		if (Locker.grid||texture == null) {
			g.drawRect(getPosition().x, getPosition().y, 32, 32);
		}
	}


	public void update() {
		// TODO Auto-generated method stub
		if (stamina < getMaxStamina()) {
			addStamina(getStaminaRegen());
		}
		if (health < getMaxHealth()) {
			addHealth(getHealthRegen());
		}
	}
}
