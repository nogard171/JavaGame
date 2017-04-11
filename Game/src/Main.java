import engine.Sprite;
import engine.Window;

import static org.lwjgl.opengl.GL11.*;
public class Main extends Window
{

	public static void main(String args[])
	{
		Main game = new Main();
		game.Start();
	}

	@Override
	public void onInit()
	{

	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

	}

	@Override
	public void onRender()
	{
		new Sprite().Render();
	}
}