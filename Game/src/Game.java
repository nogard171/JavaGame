import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {

	public void Init() {
		super.Init();
		test.setPosition(100, 100);
		test.setOrigin(16, 16);
		test.setSize(24, 48);
		ground.setPosition(0, 100);
		ground.setSize(this.Width, 100);
		hud.init();
		hud.setHeight(this.Height);

		for (int i = 0; i < 1; i++) {
			Person person = new Person();
			Random rand = new Random();
			// Java 'Color' class takes 3 floats, from 0 to 1.
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			person.setColor(randomColor);
			people.add(person);
		}
	}

	float speed = 0;
	float grav = 0;
	boolean moved = false;
	int jump = 0;

	@Override
	public void Update(int delta) {
		super.Update(delta);
		speed = delta / 4;
		grav -= 0.01 * delta;
		if (test.position.getY() - test.height <= ground.position.getY()) {
			test.position.setY(ground.position.getY() + test.height);
			grav = 0;
			jump = 0;
		}

		for (Person person : people) {
			if (person.position.getY() - person.height <= ground.position.getY()) {
				person.position.setY(ground.position.getY() + person.height);
			}
		}

		int x = 0;

		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			x = (int) speed;
		} else if (keyboard.keyPressed(Keyboard.KEY_A)) {
			x = (int) -speed;
		}
		//
		if (keyboard.keyPressed(Keyboard.KEY_LSHIFT)) {
			test.velocity_rate = 2;
		} else {
			test.velocity_rate = 1;
		}

		if (keyboard.keyOnce(Keyboard.KEY_SPACE) && jump < 2) {
			// test.rot +=5;
			grav += 4;
			jump++;
		}
		int y = (int) grav;

		test.Move(x, y);

		if (!keyboard.keyPressed(Keyboard.KEY_D) && !keyboard.keyPressed(Keyboard.KEY_A)) {
			test.EndMove();
		}
		if (keyboard.keyPressed(Keyboard.KEY_ESCAPE)) {
			System.exit(0); 
		}
		if (keyboard.keyPressed(Keyboard.KEY_E)) {
			test.point = true;
		} else {
			test.point = false;
		}

		if (time >= 100) {
			for (Person person : people) {
				person.move = ran.nextInt(10 - 1 + 1) + 1;
			}
			time = 0;
		}
		for (Person person : people) {
			speed = ran.nextInt((int) (5 - 1 + 1)) + 1;
			if (person.move < 2) {
				person.EndMove();
			} else if (person.move >= 4 && person.move < 6) {
				// person.point = true;
			} else if (person.move >= 6 && person.move < 8 && person.position.getX() < this.Width) {
				person.Move((int) speed, 0);
			} else if (person.move >= 8 && person.move < 10 && person.position.getX() > 0) {
				person.Move((int) -speed, 0);
			} else if (person.move >= 10 && person.position.getX() > 0) {

			}

		}

		time++;
		hud.update(super.mouse);
		super.keyboard.endPoll();
	}

	@Override
	public void Resized() {
		super.Resized();
		System.out.println("width:" + this.Width);
		hud.setHeight(this.Height);
		ground.setSize(this.Width, 100);
	}

	int time = 0;
	// int move = 0;
	Random ran = new Random();
	boolean move_test2 = false;
	Person test = new Person();
	Object ground = new Object();
	Tree tree = new Tree(100, 100);
	HUD hud = new HUD();
	ArrayList<Person> people = new ArrayList<Person>();
	Object go = new Object();

	public void Render() {
		super.Render();

		// tree.Render();

		for (Person person : people) {
			person.Render();
		}
		test.setColor(new Color(255, 0, 0));
		test.Render();
		ground.Render();

		hud.Render();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}