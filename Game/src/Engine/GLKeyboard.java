package Engine;

import org.lwjgl.input.Keyboard;

public class GLKeyboard 
{	
	public boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
}
