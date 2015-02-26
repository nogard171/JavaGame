package objects;

import util.AudioPlayer;
import util.FrameRate;
import util.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Timer;

import networking.Locker;

public class Player {
	// the players name
	private String name = "player2";
	// the players texture
	private BufferedImage texture;
	// the players current frame
	private BufferedImage frame;
	// the moving boolean
	public boolean moving = false;
	// the frame points determine the frame
	public Point framePoints = new Point(0, 0);
	private Point velocity = new Point(2, 2);
	// this is the players direction
	public Direction direction = Direction.Up;
	// the players frameCycle
	private float frameCycle = 1;
	// the max cycles for the player
	private int maxCycles = 4;
	// this is the players steps
	private int steps = 0;
	// the players position
	private Rectangle bounds = new Rectangle(0, 0, 32, 32);
	// this is the players health
	private double health = 10;
	// this is the players max health
	private double maxHealth = 100;
	// this is the health regen rate
	private float healthRegenRate = 0.5f;
	// this is the players mana
	private double mana = 50;
	// this is the players max mana
	private double maxMana = 50;
	// this is the mana regen rate
	private float manaRegenRate = 1.5f;
	// this is the current stamina
	private double stamina = 40;
	// this is the max stamina for the player
	private double maxStamina = 100;
	// the stamina regen rate
	private float staminaRegenRate = 1.5f;
	// this is how fast the player moves
	private int speed = 2;
	// this indicates the selected walking sound
	private int selectedWalking = 0;
	// boolean for if the player is dashing
	private boolean dashing = false;
	public int level = 1;

	public long currentExperience = 0;
	public long experience = 0;
	public long maxExperience = 83;
	public long baseExperience = 83;
	// count for timing based things
	public int count = 0;
	// audio player for walking sounds
	AudioPlayer audioPlayer = new AudioPlayer();
	public ArrayList<Item> inventory = new ArrayList<Item>();
	// audio sounds location
	public String[] audioFilePath = { "\\resources\\audio\\footstep02.wav",
			"\\resources\\audio\\chop.wav" };
	private Point baseVelocity = new Point(100, 100);

	public Player() {
		Skill skill = new Skill();
		skill.name = "Vitality";
		skill.description = "This is the players "+skill.name+" skill.";
		skill.level = 1;
		skills.add(skill);
	}

	public Skill getSkill(String skillName) {
		Skill temp = new Skill();
		temp.level = 0;
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).name == skillName) {
				temp = skills.get(i);
				break;
			}
		}
		return temp;
	}

	public boolean hasSkill(String skillName) {
		boolean temp = false;
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).name == skillName) {
				temp = true;
				break;
			}
		}
		return temp;
	}

	// return the current frame
	public BufferedImage getFrame() {
		return this.frame;
	}

	// this sets the frames points
	public void setFramePoints(int x, int y) {
		this.framePoints = new Point(x, y);
	}

	// this returns the players position
	public Point getPosition() {
		return new Point(bounds.x, bounds.y);
	}

	// sets the players texture
	public void setTexture(BufferedImage newTexture) {
		this.texture = newTexture;
	}

	// this sets the players position to the newPoint
	public void setPosition(Point newPoint) {
		this.bounds = new Rectangle(newPoint.x, newPoint.y, bounds.width,
				bounds.height);
	}

	// this sets the players position to the ints
	public void setPosition(int x, int y) {
		this.bounds = new Rectangle(x, y, bounds.width, bounds.height);
	}

	// this returns the players type back
	public Direction getDirection() {
		return this.direction;
	}

	// move the player right
	public void moveRight() {
		if (action == 0) {
			this.bounds.x += this.velocity.x;
			this.direction = Direction.Right;
			moving = true;
		}
	}

	// move the player left
	public void moveLeft() {
		if (action == 0) {
			this.bounds.x -= this.velocity.x;
			this.direction = Direction.Left;
			moving = true;
		}
	}

	// move the player up
	public void moveUp() {
		if (action == 0) {
			this.bounds.y -= this.velocity.y;
			this.direction = Direction.Up;
			moving = true;
		}
	}

	// move the player Down
	public void moveDown() {
		if (action == 0) {
			this.bounds.y += this.velocity.y;
			this.direction = Direction.Down;
			moving = true;
		}
	}

	// this allows player to dash
	public void dash() {
		// if the player is moving and stamina is greater than
		// zero continue
		if (this.moving && this.stamina > 0 && !this.colliding && action == 0) {
			dashing = true;
			this.baseVelocity.x = 300;
			this.baseVelocity.y = 300;
			// minus stamina from the current stamina
			this.stamina -= 0.2f;
		} else {
			this.baseVelocity.x = 100;
			this.baseVelocity.y = 100;
			// turn dashing off
			dashing = false;
		}

	}

	public void stopDashing() {
		this.baseVelocity.x = 100;
		this.baseVelocity.y = 100;
		// turn dashing off
		dashing = false;
	}

	// this returns the players stamina
	public double getStamina() {
		return this.stamina;
	}

	// this returns the players stamina
	public double getMaxStamina() {
		return this.maxStamina;
	}

	// this returns health
	public double getHealth() {
		return this.health;
	}

	// this returns maxhealth
	public double getMaxHealth() {
		return this.maxHealth;
	}

	// this returns Mana
	public double getMana() {
		return this.mana;
	}

	// this returns maxMana
	public double getMaxMana() {
		return this.maxMana;
	}

	// this returns the players speed
	public int getSpeed() {
		return this.speed;
	}

	// cycle function for cycling through animation
	public void cycle(double delta) {
		this.velocity.x = (int) (baseVelocity.x * delta);
		this.velocity.y = (int) (baseVelocity.y * delta);
		
		if (frameCycle >= maxCycles) {
			String workingDir = System.getProperty("user.dir");
			frameCycle = 0;
			audioPlayer.play(workingDir + audioFilePath[selectedWalking]);
		} else {
			frameCycle += delta * 10;
		}
	}

	// this returns the players name
	public String getName() {
		return this.name;
	}

	// this sets the players name
	public void setName(String newName) {
		this.name = newName;
	}

	// adds health when healed and such
	public void addHealth(double value) {
		this.health += value;
	}

	// boolean for chopping trees
	public int action = 0;

	public void harvest(Object object) {
		// TODO Auto-generated method stub
		if (object.getLowerType() == Type.Tree) {
			this.action = 1;
		}
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public boolean colliding = false;
	int chopped = 0;
	public boolean networked;
	private boolean isColliding;
	public ArrayList<Skill> skills = new ArrayList<Skill>();

	// this updates the players frame
	public void onUpdate(double delta) {
		if (!this.networked) {
			checkLevel();
			// if stamina is less than the maxStamina, it will regen until it's
			// equal to maxStamina
			if (this.stamina < maxStamina) {
				// add the stamina regen rate to the current stamina
				this.stamina += staminaRegenRate * delta;
			}
			if (this.stamina > maxStamina) {
				// add the stamina regen rate to the current stamina
				this.stamina = maxStamina * delta;
			}
			// if health is less than the maxhealth, it will regen until it's
			// equal to maxhealth
			if (this.health < maxHealth) {
				// add the stamina regen rate to the current stamina
				this.health += healthRegenRate * delta;
			}
			if (this.health > maxHealth) {
				// add the stamina regen rate to the current stamina
				this.health = maxHealth;
			}
			// if health is less than the maxhealth, it will regen until it's
			// equal to maxhealth
			if (this.mana < maxMana) {
				// add the stamina regen rate to the current stamina
				this.mana += manaRegenRate* delta;
			}
			if (this.mana > maxMana) {
				// add the stamina regen rate to the current stamina
				this.mana = maxMana;
			}
		}
		// if the player is moving call cycling
		if (moving && !this.colliding) {
			// get the current working directory
			String workingDir = System.getProperty("user.dir");

			// else if count modulus of 4(is a multipuly of 4)
			// and dashing is true, then play the walking sound faster
			if (dashing ) {
				//audioPlayer.play(workingDir + audioFilePath[selectedWalking]);
			}
			// call the cycle function
			cycle(delta);
			// set the framepoints.x based on the maxCycles and frameCycles
			// math: maxCycles/2 which gets the total number of steps for one
			// cycle
			// frameCycle/(maxCycles/2) gets the current cycle the player is on
			framePoints.x = (int) (frameCycle) / 2;
			if (!this.networked) {
				// this sends the players points to the server
				Locker.proticol = "move";
				Locker.sendLine = getPosition().x + "," + getPosition().y + ","
						+ this.moving + "," + this.direction + ","
						+ this.action + "," + this.health + ","
						+ this.maxHealth + "," + this.mana + "," + this.maxMana
						+ ",";
			}
		} else {
			framePoints.x = 1;
		}
		// check the direction
		switch (this.direction) {
		case Right:
			framePoints.y = 2;
			break;
		case Left:
			framePoints.y = 1;
			break;
		case Up:
			framePoints.y = 3;
			break;
		case Down:
			framePoints.y = 0;
			break;
		}
		// set the frame to the new updated frame
		frame = texture.getSubimage(framePoints.x * 32, framePoints.y * 32,
				texture.getWidth() / 3, texture.getHeight() / 4);
		// stop moving
		moving = false;
	}

	public void addExperience(long amount) {
		experience += amount;
		boolean adding = true;
		while (adding) {
			if (this.experience > this.maxExperience) {
				level++;
				newMaxStats();
			} else {
				adding = false;
			}
		}
	}

	public void checkLevel() {
		if (this.experience > this.maxExperience) {
			level++;
			newMaxStats();
		}
	}

	float multiplier = 0.05f;
	float rate = 1.5f;

	public void newMaxStats() {
		experience -= maxExperience;
		this.maxExperience = (long) (((Math.pow(
				((level * multiplier) * baseExperience), rate)) * (level - 1)) + this.baseExperience);

		maxHealth += ((maxHealth / level) / ((0.05) * level))
				+ (2 * (getSkill("Vitality").level + 1));
		this.healthRegenRate += 0.13f * (getSkill("Vitality").level + 1);

		maxMana += ((maxMana / level) / ((0.05) * level))
				+ (2 * (getSkill("Magic").level + 1));
		manaRegenRate += 0.15f * (getSkill("Magic").level + 1);

		maxStamina += ((maxStamina / level) / ((0.055) * level))
				+ (3 * (getSkill("Strength").level + 1));
		staminaRegenRate += 0.125f * (getSkill("Strength").level + 1);
	}

	public String getExperience() {
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.###E0");
		return experience+"";
	}

	public String getMaxExperience() {
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.###E0");
		return maxExperience+"";
	}

	public void setHealth(float f, float g) {
		this.health = f;
		this.maxHealth = g;
	}

	public void setMana(float f, float g) {
		this.mana = f;
		this.maxMana = g;
	}

	public void onCollide(Object recThree) {

		isColliding = true;
		moving = false;
		// TODO Auto-generated method stub
		if (this.direction == Direction.Up) {
			this.bounds.y = this.bounds.y + this.velocity.y;
		} else if (this.direction == Direction.Down) {
			this.bounds.y = this.bounds.y - this.velocity.y;
		} else if (this.direction == Direction.Left) {
			this.bounds.x = this.bounds.x + this.velocity.x;
		} else if (this.direction == Direction.Right) {
			this.bounds.x = this.bounds.x - this.velocity.x;
		}
	}
	int index =0;
	public void preformAction(Object obj)
	{
		if(obj.lowerType.equals(Type.Tree))
		{
			System.out.println("Cutting tree down"+index);
			obj.harvest();
		}
		else if(obj.lowerType.equals(Type.Rock))
		{
			System.out.println("Mining a Rock"+index);
			obj.harvest();
		}
		index++;
	}

	public void draw(Graphics bbg, ImageObserver obj) {
		bbg.drawString(getName(), getPosition().x, getPosition().y - 15);
		bbg.setColor(new Color(128, 128, 128, 128));
		bbg.fillRect(getPosition().x, getPosition().y - 13, 32, 5);
		bbg.setColor(new Color(255, 0, 0, 128));
		int healthWidth = (int) ((32 / getMaxHealth()) * getHealth());
		bbg.fillRect(getPosition().x, getPosition().y - 13, healthWidth, 5);
		bbg.setColor(new Color(128, 128, 128, 128));
		bbg.fillRect(getPosition().x, getPosition().y - 8, 32, 5);
		bbg.setColor(new Color(0, 0, 255, 128));
		int manaWidth = (int) ((32 / getMaxMana()) * getMana());
		bbg.fillRect(getPosition().x, getPosition().y - 8, manaWidth, 5);
		// draw the players Rec in the backbuffer
		bbg.drawImage(getFrame(), getPosition().x, getPosition().y, 32, 32, obj);

	}

	public void attack() {
		if (this.direction == Direction.Right) {
			action = 2;
		}
	}
}
