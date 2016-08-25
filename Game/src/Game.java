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
		test.setOrigin(16, 16);
		test.setSize(24, 48);
		ground.x = 0;
		ground.y = 100;
		ground.setSize(this.Width, 100);
		hud.init();
		hud.setHeight(this.Height);

		for (int i = 0; i < 1; i++) {
			Person person = new Person();
			people.add(person);
		}
	}

	float speed = 0;
	float grav = 0;
	boolean moved = false;
	int jump = 0;

	public void Update(int delta) {
		super.Update(delta);
		speed = delta / 4;
		grav -= 0.01 * delta;
		if (test.y - test.getHeight() <= ground.y) {
			test.y = ground.y + test.getHeight();
			grav = 0;
			jump = 0;
		}
		for (Person person : people) {
			if (person.y - person.getHeight() <= ground.y) {
				person.y = ground.y + person.getHeight();
			}
		}
		int x = 0;

		if (keyboard.keyPressed(Keyboard.KEY_D)) {
			x = (int) speed;
		} else if (keyboard.keyPressed(Keyboard.KEY_A)) {
			x = (int) -speed;
		}

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

			if (person.move < 4) {
				person.EndMove();
			} else if (person.move >= 4 && person.move < 6) {
				// test2.point = true;
			} else if (person.move >= 6 && person.move < 8 && person.x < this.Width) {
				person.Move(1, 0);
			} else if (person.move >= 8 && person.x > 0) {
				person.Move(-1, 0);
			}

		}

		time++;
		hud.update(super.mouse);
		super.keyboard.endPoll();
	}

	@Override
	public void Resized() {
		super.Resized();
		System.out.println("height:" + this.Height);
		hud.setHeight(this.Height);
	}

	int time = 0;
	// int move = 0;
	Random ran = new Random();
	boolean move_test2 = false;
	Person test = new Person();
	// Person test2 = new Person();
	Entity ground = new Entity();
	Tree tree = new Tree(100, 100);
	HUD hud = new HUD();
	ArrayList<Person> people = new ArrayList<Person>();

	public void Render() {
		super.Render();

		// tree.Render();
		test.Render();
		// test2.Render();
		for (Person person : people) {
			person.Render();
		}
		ground.Render();
		hud.Render();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}