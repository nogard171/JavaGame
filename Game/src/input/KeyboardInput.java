package input;

import java.awt.Component;
import java.awt.event.*;

//the input handling class in which uses keylistener
public class KeyboardInput implements KeyListener 
{
	//declare the array of if keys are down or not
	private boolean[] keys;
	private int lastKey = -1;
	private int currentKey = -1;
	public int getLastKey()
	{
		return lastKey;
	}
	public int getCurrentKey()
	{
		return currentKey;
	}
	//the constructor for the input handler class
	public KeyboardInput(Component c)
	{
		//adds the default false value to all the keys in the array
		keys = new boolean[256];
		//set the keylistener to this class
		c.addKeyListener(this);
	}
	//if key is down return the keycode
	public boolean isKeyDown(int keyCode)
	{
		if(keyCode > 0 && keyCode < 256)
		{
			return keys[keyCode];
		}
		return false;
	}
	//if key is being pressed, set the boolean for the specific key
	//to true
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()> 0 && e.getKeyCode() < 256)
		{
			keys[e.getKeyCode()] = true;
			currentKey = e.getKeyCode();
		}
		
	}
	//if the key is released, set the boolean for the specific key
	//to false
    public void keyReleased(KeyEvent e) 
    { 
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) 
            { 
                    keys[e.getKeyCode()] = false; 
                    lastKey = e.getKeyCode();
            } 
    }
    //if a key is just typed do nothing
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	} 

}