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
		ground.setSize(this.displayWidth, 100);
		hud.init();
		hud.setHeight(this.displayHeight);
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
		if (test2.y - test2.getHeight() <= ground.y) {
			test2.y = ground.y + test2.getHeight();
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
		if (time >= 100) {
			move = ran.nextInt(10 - 1 + 1) + 1;
			time = 0;
		}

		if (keyboard.keyPressed(Keyboard.KEY_E)) {
			test.point = true;
		} else {
			test.point = false;
		}

		if (move < 4) {
			test2.EndMove();
		} else if (move >= 4 && move < 6) {
			// test2.point = true;
		} else if (move >= 6 && move < 8 && test2.x < this.displayWidth) {
			test2.Move(1, 0);
		} else if (move >= 8 && test2.x > 0) {
			test2.Move(-1, 0);
		}

		time++;
		hud.update(super.mouse);
		super.keyboard.endPoll();
	}

	int time = 0;
	int move = 0;
	Random ran = new Random();
	boolean move_test2 = false;
	Person test = new Person();
	Person test2 = new Person();
	Entity ground = new Entity();
	Tree tree = new Tree(100, 100);
	HUD hud = new HUD();

	public void Render() {
		super.Render();

		// tree.Render();
		test.Render();
		test2.Render();
		ground.Render();
		hud.Render();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}