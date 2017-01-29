import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Window
{

	public static void main(String args[])
	{
		Main game = new Main();
		game.ShowDisplay();
	}

	Random ran = new Random();

	@Override
	public void Init()
	{
		super.Init();
		view = new View();
		renderer.Init();
		int size = 10;
		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				Entity entity = new Entity();
				entity.setName("TILE");
				entity.setPosition(new Vector2f(x * 32, y * 32));
				if ((ran.nextInt(100) + 1) % 5 == 0)
				{
					entity.setQuadName("DIRT");
					entity.setSolid(true);
				}
				renderer.addEntity(entity);
			}
		}
		Entity entity = new Entity();
		entity.setName("PLAYER");
		entity.setQuadName("SAND");
		entity.setPosition(new Vector2f(-32, -32));
		entity.setColor(new Color(255,255,255));
		renderer.addEntity(entity);
	}

	View view;

	@Override
	public void Resize()
	{
		view.setViewWidth((int) Math.ceil(super.getWidth() / 32));
		view.setViewHeight((int) Math.ceil(super.getHeight() / 32));
	}

	@Override
	public void Update()
	{
		super.Update();
		int x = Mouse.getX(); // will return the X coordinate on the Display.
		int y = Mouse.getY(); // will return the Y coordinate on the Display.
		float xSpeed = 0;
		float ySpeed = 0;
		float speed = 5;
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			xSpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			xSpeed = -speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			ySpeed = -speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			ySpeed = speed;
		}
		view.moveView(new Vector2f(xSpeed, ySpeed));
		xSpeed = 0;
		ySpeed = 0;
		speed = 2.5f;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			xSpeed = -speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			xSpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			ySpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			ySpeed = -speed;
		}
		Entity player = renderer.getEntity("player");
		new EntityController();
		if (player != null)
		{
			Rectangle playerBounds = new Rectangle((int) player.getPosition().getX(), (int) player.getPosition().getY(),
					32, 32);
			for (Entity newEntity : renderer.getEntitiesToDisplay())
			{
				newEntity.setColor(new Color(255, 255, 255));
					if (playerBounds.intersects(new Rectangle((int) newEntity.getPosition().getX(),
							(int) newEntity.getPosition().getY(), 32, 32))&&newEntity.isSolid())
					{
						newEntity.setColor(new Color(255, 0, 0));
						//break;
					}
			}
		}
		EntityController.moveEntity(player, new Vector2f(xSpeed, ySpeed));

		Entity entity = renderer.getHoveredEntity();
		if (entity != null)
		{
			// entity.setColor(new Color(128, 128, 128, 128));
			// Display.setTitle("Type:" + entity.getQuadName());
		} else
		{
			// Display.setTitle("Type:NULL");
		}
	}

	Renderer renderer = new Renderer();

	@Override
	public void Render()
	{
		super.Render();
		renderer.Render(view);
		Display.setTitle("FPS:" + super.fpsCounter.getFPS());
	}

	@Override
	public void Destroy()
	{
		super.Destroy();
		renderer.Destroy();
	}
}