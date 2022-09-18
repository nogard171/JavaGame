import org.lwjgl.input.Keyboard;

public class KeyboardInput
{
	boolean[] keys_down;
	int key_count = 255;

	public KeyboardInput()
	{
		keys_down = new boolean[key_count];
		for (int k = 0; k < keys_down.length; k++)
		{
			keys_down[k] = false;
		}
	}

	public void poll()
	{
		for (int k = 0; k < keys_down.length; k++)
		{
			if (Keyboard.isKeyDown(k))
			{
				keys_down[k] = true;
			}
		}
	}

	public void end()
	{
		for (int k = 0; k < keys_down.length; k++)
		{
			keys_down[k] = false;
		}
	}

	public boolean isKeyDown(int key)
	{
		// TODO Auto-generated method stub
		return keys_down[key];
	}
}
