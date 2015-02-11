package objects;

import util.AudioPlayer;
import util.FrameRate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.Timer;

import networking.Locker;

public class Player
{
    //the players name
    private String name = "test";
    //the players texture
    private BufferedImage texture;
    //the players current frame
    private BufferedImage frame;
    //the moving boolean
    public boolean moving = false;
    //the frame points determine the frame
    public Point framePoints = new Point(0,0);
    private Point velocity = new Point(2,2);
    //this is the players direction
    public Direction direction = Direction.Up;
    //the players frameCycle
    private int frameCycle = 1; 
    //the max cycles for the player
    private int maxCycles = 4;
 	//this is the players steps
    private int steps = 0;
    //the players position
    private Rectangle bounds = new Rectangle(0,0,32,32);
    //this is the players health
    private double health = 10;
    //this is the players max health
    private double maxHealth = 100;
    //this is the health regen rate
    private float healthRegenRate = 0.1f;
    //this is the players mana
    private double mana = 50;
    //this is the players max mana
    private double maxMana = 50;
    //this is the mana regen rate
    private float manaRegenRate = 0.1f;
    //this is the current stamina
    private double stamina = 40; 
    //this is the max stamina for the player
    private double maxStamina = 100;
    //the stamina regen rate
    private float staminaRegenRate = 0.1f;
    //this is how fast the player moves
    private int speed = 2;
    //this indicates the selected walking sound
    private int selectedWalking = 0;
    //boolean for if the player is dashing
    private boolean dashing = false;
    //count for timing based things
    public int count = 0;
    //audio player for walking sounds
    AudioPlayer audioPlayer = new AudioPlayer();
	public ArrayList<Item> inventory = new ArrayList<Item>();
    //audio sounds location
    public String[] audioFilePath = {"\\resources\\audio\\footstep02.wav",
    		"\\resources\\audio\\chop.wav"};
    //return the current frame
    public BufferedImage getFrame()
    {
    	return this.frame;
    }
    //this sets the frames points
    public void setFramePoints(int x, int y)
    {
    	this.framePoints = new Point(x,y);
    }
    //this returns the players position
    public Point getPosition()
    {
    	return new Point(bounds.x,bounds.y);
    }
    //sets the players texture
    public void setTexture(BufferedImage newTexture)
    {
    	this.texture = newTexture;
    }
    //this sets the players position to the newPoint
    public void setPosition(Point newPoint)
    {
    	this.bounds = new Rectangle(newPoint.x,newPoint.y,bounds.width,bounds.height);
    }
    //this sets the players position to the ints 
    public void setPosition(int x, int y)
    {
    	this.bounds = new Rectangle(x,y,bounds.width,bounds.height);
    }
    //this returns the players type back
    public Direction getDirection()
    {
    	return this.direction;
    }
    //move the player right
    public void moveRight()
    {
    	if(action==0)
    	{
    		this.bounds.x+=this.velocity.x;
    		this.direction = Direction.Right;
    		moving = true;
    	}
    }
    //move the player left
    public void moveLeft()
    {
    	if(action==0)
    	{
    		this.bounds.x-=this.velocity.x;
    		this.direction = Direction.Left;
    		moving = true;
    	}
    }
    //move the player up
    public void moveUp()
    {
    	if(action==0)
    	{
    		this.bounds.y-=this.velocity.y;
    		this.direction = Direction.Up;
    		moving = true;
    	}
    }
    //move the player Down
    public void moveDown()
    {
    	if(action==0)
    	{
    		this.bounds.y+=this.velocity.y;
    		this.direction = Direction.Down;
    		moving = true;
    	}
    }
    //this allows player to dash
    public void dash()
    {
    	//if the player is moving and stamina is greater than
    	//zero continue
    	if(this.moving&&this.stamina>0&&!this.colliding&&action==0)
    	{
    		dashing = true;
    		this.velocity.x =6;
    		this.velocity.y =6;
    		//minus stamina from the current stamina
    		this.stamina -=0.5f;
    	}
    	else
    	{
    		this.velocity.x =2;
    		this.velocity.y =2;
    		//turn dashing off
    		dashing = false;
    	}
	
    }
    public void stopDashing()
    {
		this.velocity.x =2;
		this.velocity.y =2;
		//turn dashing off
		dashing = false;
    }
    //this returns the players stamina
    public double getStamina()
    {
    	return this.stamina;
    }
    //this returns health
    public double getHealth()
    {
    	return this.health;
    }
    //this returns maxhealth
    public double getMaxHealth()
    {
    	return this.maxHealth;
    }
    //this returns Mana
    public double getMana()
    {
    	return this.mana;
    }
    //this returns maxMana
    public double getMaxMana()
    {
    	return this.maxMana;
    }
    //this returns the players speed
    public int getSpeed()
    {
    	return this.speed;
    }
    //cycle function for cycling through animation
    public void cycle(int delta)
    {
    	if(frameCycle>=maxCycles)
    	{
    		String workingDir = System.getProperty("user.dir");
    		frameCycle=0;
    		audioPlayer.play(workingDir+audioFilePath[selectedWalking]);
    	}
    	else if(delta%5==1)
    	{
    		frameCycle++;
    	}
    }
    //this returns the players name
    public String getName()
    {
    	return this.name;
    }
    //this sets the players name
    public void setName(String newName)
    {
    	this.name = newName;
    }    
    //adds health when healed and such
    public void addHealth(double value)
    {
    	this.health+=value;
    }
    //boolean for chopping trees
    public int action = 0;
    public void harvest(Object object)
    {
    	// TODO Auto-generated method stub
    	if(object.getLowerType() == Type.Tree)
    	{
    		this.action = 1; 
    	}	
    }
    public Rectangle getBounds()
    {
    	return this.bounds;
    }
    public boolean colliding = false;
    int chopped = 0;
	public boolean networked;
	private boolean isColliding;
	FrameRate frameRate = new FrameRate();
    //this updates the players frame
    public void onUpdate()
    {
    	frameRate.calculate();
    	int delta = frameRate.getDelta();
    	if(!this.networked)
	    {
	    	//if stamina is less than the maxStamina, it will regen until it's equal to maxStamina 
	    	if(delta%5==1&&this.stamina<maxStamina)
	    	{
	    	    //add the stamina regen rate to the current stamina
	    	    this.stamina += staminaRegenRate;
	    	}
	    	//if health is less than the maxhealth, it will regen until it's equal to maxhealth
	    	if(delta%5==1&&this.health<maxHealth)
	    	{
	    	    //add the stamina regen rate to the current stamina
	    	    this.health += healthRegenRate;
	    	}
	    	//if health is less than the maxhealth, it will regen until it's equal to maxhealth
	    	if(delta%5==1&&this.mana<maxMana)
	    	{
	    	    //add the stamina regen rate to the current stamina
	    	    this.mana += manaRegenRate;
	    	}
	    }
    	if(delta%5==1&&action==1)
    	{
    		//get the current working directory
    		String workingDir = System.getProperty("user.dir");
    	    audioPlayer.play(workingDir+audioFilePath[1]);
    	    if(chopped>=5)
    	    {
    	    	action = 0;
    	    	chopped = 0;    	  
    	    }
    	    chopped++;
    	}
    	//if the player is moving call cycling
    	if(moving&&!this.colliding)
    	{
    	    //get the current working directory
    	    String workingDir = System.getProperty("user.dir");

    	    //else if count modulus of 4(is a multipuly of 4)
    	    //and dashing is true, then play the walking sound faster
    	   if(dashing&&count%10==1)
    	    {
    	    	audioPlayer.play(workingDir+audioFilePath[selectedWalking]);
    	    }
    	    //call the cycle function
    	    cycle(delta);
    	    //set the framepoints.x based on the maxCycles and frameCycles
    	    //math: maxCycles/2 which gets the total number of steps for one cycle
    	    //frameCycle/(maxCycles/2) gets the current cycle the player is on
    	    framePoints.x = frameCycle/2;
    	    if(!this.networked)
    	    {
    	    	//this sends the players points to the server
    	    	Locker.proticol = "move";
				Locker.sendLine = getPosition().x+","+
						getPosition().y+","+
						this.moving+","+
						this.direction+","+
						this.action+","+
						this.health+","+
						this.maxHealth+","+
						this.mana+","+
						this.maxMana+",";
    	    }
    	}
    	else
    	{
    	    framePoints.x =	1;
    	}
    	//check the direction
    	switch(this.direction)
    	{
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
    	//set the frame to the new updated frame
    	frame = texture.getSubimage(framePoints.x*32, framePoints.y*32, texture.getWidth()/3, texture.getHeight()/4);
    	//stop moving
    	moving = false;
	}
    public void setHealth(float f, float g) 
	{
		this.health = f;
		this.maxHealth = g;
	}
	public void setMana(float f, float g) 
	{
		this.mana = f;
		this.maxMana = g;
	}
	public void onCollide(Object recThree) {
		
		isColliding = true;	
		moving = false;
		// TODO Auto-generated method stub
		if(this.direction ==Direction.Up)
		{
			this.bounds.y= this.bounds.y+this.velocity.y;
		}
		else if(this.direction ==Direction.Down)
		{
			this.bounds.y= this.bounds.y-this.velocity.y;
		}
		else if(this.direction == Direction.Left)
		{
			this.bounds.x= this.bounds.x+this.velocity.x;
		}
		else if(this.direction ==Direction.Right)
		{
			this.bounds.x= this.bounds.x-this.velocity.x;
		}
	}
	public void draw(Graphics bbg, ImageObserver obj)
	{
		bbg.drawString(getName(),getPosition().x,getPosition().y-15);
        bbg.setColor(new Color(128,128,128,128));
        bbg.fillRect(getPosition().x,getPosition().y-13,32,5);
        bbg.setColor(new Color(255,0,0,128));
        int healthWidth = (int) ((32/getMaxHealth())*getHealth());
        bbg.fillRect(getPosition().x,getPosition().y-13,healthWidth,5);
        bbg.setColor(new Color(128,128,128,128));        
        bbg.fillRect(getPosition().x,getPosition().y-8,32,5);
        bbg.setColor(new Color(0,0,255,128));
        int manaWidth = (int) ((32/getMaxMana())*getMana());
        bbg.fillRect(getPosition().x,getPosition().y-8,manaWidth,5);
        //draw the players Rec in the backbuffer
        bbg.drawImage(getFrame(),getPosition().x,getPosition().y, 32,32,obj);

	}
	public void attack() 
	{
		if(this.direction == Direction.Right)
		{
			action = 2;
		}
	}
}
