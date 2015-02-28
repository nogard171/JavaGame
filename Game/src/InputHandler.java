

import java.awt.Component;
import java.awt.event.*;

//the input handling class in which uses keylistener
public class InputHandler implements KeyListener 
{
	//declare the array of if keys are down or not
	private boolean[] keys;
	//the constructor for the input handler class
	public InputHandler(Component c)
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
		}
		
	}
	//if the key is released, set the boolean for the specific key
	//to false
    public void keyReleased(KeyEvent e) 
    { 
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) 
            { 
                    keys[e.getKeyCode()] = false; 
            } 
    }
    //if a key is just typed do nothing
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	} 

}
