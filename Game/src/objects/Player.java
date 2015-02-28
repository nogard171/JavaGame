package objects;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

public class Player
{
    //the players name
    private String name = "test";
    //the players texture
    private BufferedImage texture;
    //the players current frame
    private BufferedImage frame;
    //the frame points determine the frame
    public Point framePoints = new Point(0,0);
    //the players position
    public Point position = new Point(0,0);
    
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
    	return this.position;
    }
    //sets the players texture
    public void setTexture(BufferedImage newTexture)
    {
    	this.texture = newTexture;
    }
    //this sets the players position to the newPoint
    public void setPosition(Point newPoint)
    {
    	this.position = newPoint;
    }
    //this sets the players position to the ints 
    public void setPosition(int x, int y)
    {
    	this.position = new Point(x,y);
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
   
    //this updates the players frame
    public void updateFrame()
    {
    	
	}
}
