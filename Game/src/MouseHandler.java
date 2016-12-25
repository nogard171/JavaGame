import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class MouseHandler
{
	private static Vector2f position = null;
	public static Vector2f getPosition()
	{
		return position;
	}

	private static boolean[] buttons = null;

	public static void Init()
	{
		buttons = new boolean[3];

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = false;
		}
	}

	public static int getButton()
	{
		int button = -1;
		if (buttons != null)
		{
			for (int b = 0; b < buttons.length; b++)
			{
				if (buttons[b])
				{
					button = b;
				}
			}
		}
		return button;
	}

	public static void poll()
	{
		if (position == null)
		{
			Init();
		}
		
		position = new Vector2f(Mouse.getX(), Mouse.getY()-48);

		for (int b = 0; b < buttons.length; b++)
		{
			buttons[b] = Mouse.isButtonDown(b);
		}
	}

}
