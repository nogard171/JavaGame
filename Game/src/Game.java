import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Objects.MapData;
import Objects.NetworkData;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Game extends Window {
	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
	float rotation = 0;
	Client network = null;
	private Rectangle window = new Rectangle(0, 0, 0, 0);
	MapData map = null;

	public void Init() {
		super.Init();
		window = new Rectangle(0, 0, this.displayWidth, this.displayHeight);
		hud = new HUD(this.displayWidth, this.displayWidth);
		network = new Client();
		network.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		network.login("alex", "test");
		/*
		try {
			test.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/images/grass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	boolean moved = false;
	public void Update(int delta) {
		super.Update(delta);
		if (game_State == State.GAME) {

			hud.Update(delta, mouse, keyboard);
			// rotate quad
			// test.rotX += 0.15f * delta;
			if (super.keyboard.keyOnce(Keyboard.KEY_F1)) {
				hud.debug = !hud.debug;
				System.out.println("Debuged:" + hud.debug);
			}
			float speed = 0.15f * delta;
			if (super.keyboard.keyPressed(Keyboard.KEY_A)) {
				test.Move(-speed, 0);
				moved = true;
			}
			if (super.keyboard.keyPressed(Keyboard.KEY_D)) {
				test.Move(speed, 0);
				moved = true;
			}
			if (super.keyboard.keyPressed(Keyboard.KEY_W)) {
				test.Move(0, -speed);
				moved = true;
			}
			if (super.keyboard.keyPressed(Keyboard.KEY_S)) {
				test.Move(0, speed);
				moved = true;
			}
			test.onClick(mouse, new Action() {
				@Override
				public void actionPerformed() {
					System.out.println("clicked");
				}
			});
			if(moved)
			{
				int newWidth = Math.round((Display.getWidth() / 32))+2;
				int newHeight = Math.round((Display.getHeight() / 32)) + 2;
				network.getMap(test.x,test.y,newHeight,newWidth);
				moved = false;
			}
		} else if (game_State == State.LOGIN) {

			if (super.keyboard.keyOnce(Keyboard.KEY_RETURN)) {
				network.login("alex", "test");
			}
			textField.onClick(mouse, new Action() {
				public void actionPerformed() {
					System.out.println("clicked");
				}
			});
			textField.onKey(keyboard, new Action() {
				@Override
				public void actionPerformed() {
					boolean shift = keyboard.keyPressed(Keyboard.KEY_RSHIFT)
							|| keyboard.keyPressed(Keyboard.KEY_LSHIFT);
					// System.out.println("Key:"+keyboard.getKeyCode());
					if (keyboard.getKeyChar(shift) != ""
							|| keyboard.keyOnce(Keyboard.KEY_SPACE)) {
						textField.addChar(keyboard.getKeyChar(shift));
					}
					if (keyboard.keyOnce(Keyboard.KEY_BACK)) {
						// System.out.println("Backspace");
						textField.backSpace();
					}
				}
			});
			if (network.logged_in) {
				game_State = State.LOADING;
			}
		}
		super.keyboard.endPoll();
	}

	State game_State = State.LOGIN;
	HUD hud = null;
	Entity test = new Entity(400,300, 32, 32);

	Entity obj = new Entity(120, 100);
	TextBox textField = new TextBox(100, 100);

	public void Render() {
		super.Render();
		if (game_State == State.GAME) {
			if (network.map != null) {
				// System.out.println("map count:" + network.map.ground.size());
				GL11.glTranslatef(-test.x,-test.y, 0);
				network.map.Render(test.x,test.y);
				GL11.glTranslatef(test.x,test.y, 0);
			}
			//GL11.glTranslatef(test.x,test.y, 0);
			test.Render();
			//GL11.glTranslatef(-test.x,-test.y, 0);
			int newWidth = Display.getWidth()/32;
			int newHeight = Display.getHeight()/32;

			if(network.map!=null&&network.map.tiles.containsKey(test.x+","+test.y))
			{
				System.out.println("worked");
			}

			//test.collide(obj);
			//obj.isSolid = true;
			//obj.Render();
			hud.Render();
		} else if (game_State == State.LOGIN) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			textField.Render();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		} else if (game_State == State.LOADING) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			if (network.map != null && network.map.tiles != null) {
				new Text().Render("Loading: " + network.map.ground.size() + "/"
						+ network.mapCount, 100, 100, 8, Color.black);
				if (network.map.tiles.size() >= network.mapCount) {
					
					game_State = State.GAME;
				} else {
					System.out.println("Loading: " + network.map.tiles.size()
							+ "/" + network.mapCount);
				}
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}