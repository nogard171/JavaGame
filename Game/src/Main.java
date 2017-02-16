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

public class Main extends GLWindow
{

	public static void main(String args[])
	{
		Main game = new Main();
		game.ShowDisplay();
	}

	Person person = new Person();

	@Override
	public void Init()
	{
		super.Init();

		quad = new Loader().loadQuad();


		Entity head = new Entity();
		head.setPosition(0, 120);
		head.setScale(0.75f, 1);
		head.setName("head");
		person.body.add(head);

		Entity chest = new Entity();
		chest.setPosition(0, 72);
		chest.setName("chest");
		chest.setScale(0.75f, 1.5f);
		chest.color = new Color(0, 1, 0);
		person.body.add( chest);

		Entity frontLeg = new Entity();
		frontLeg.setPosition(8, 24);
		frontLeg.setName("frontLeg");
		frontLeg.setScale(0.25f, 0.75f);
		frontLeg.color = new Color(0, 1, 1);
		person.body.add(frontLeg);

		Entity frontLeg2 = new Entity();
		frontLeg2.setPosition(8, 48);
		frontLeg2.setName("frontLeg");
		frontLeg2.setScale(0.25f, 0.75f);
		frontLeg2.color = new Color(1, 1, 1);
		person.body.add(frontLeg2);
		
		Entity backLeg = new Entity();
		backLeg.setPosition(8, 24);
		backLeg.setName("backLeg");
		backLeg.setScale(0.25f, 0.75f);
		backLeg.color = new Color(1, 0, 1);
		person.body.add(backLeg);

		Entity backLeg2 = new Entity();
		backLeg2.setPosition(8, 48);
		backLeg2.setName("backLeg");
		backLeg2.setScale(0.25f, 0.75f);
		backLeg2.color = new Color(1, 1, 0);
		person.body.add(backLeg2);
	}

	@Override
	public void Resize()
	{

	}

	@Override
	public void Update()
	{
		super.Update();
		int x = Mouse.getX(); // will return the X coordinate on the Display.
		int y = Mouse.getY(); // will return the Y coordinate on the Display.
		
		
		person.Walk();
	}

	Quad quad = null;
	Viewport view = new Viewport();

	@Override
	public void Render()
	{
		super.Render();
		Display.setTitle("FPS:" + super.fpsCounter.getFPS());

		/*
		 * GL11.glColor3f(1, 0, 0); GL11.glBegin(view.getMode());
		 * glCallList(quad.getDisplayList()); GL11.glEnd();
		 */

		for (int i = 0; i < person.body.size(); i++)
		{
			Entity limb = person.body.get(i);
			if (limb != null)
			{
				GL11.glPushMatrix();
				Vector2f vec = limb.getPosition();
				GL11.glTranslatef(vec.getX(), vec.getY(), 0);
				GL11.glColor3f(limb.color.getRed(), limb.color.getGreen(), limb.color.getBlue());
				GL11.glScalef(limb.getScale()[0], limb.getScale()[1], 0);
				GL11.glRotatef(limb.getRotation(), 0, 0,1);
				// GL11.glColor3f(1, 0, 0);
				GL11.glBegin(view.getMode());
				glCallList(quad.getDisplayList());
				GL11.glEnd();

				GL11.glPopMatrix();
			}
		}
	}

	@Override
	public void Destroy()
	{
		super.Destroy();
	}
}