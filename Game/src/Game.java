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

	public void Init() {
		super.Init();
		hud = new HUD(this.displayWidth, this.displayWidth);
		network = new Client();
		network.start();
	}

	public void Update(int delta) {
		super.Update(delta);
		hud.Update(delta, mouse,keyboard);
		// rotate quad
		//test.rotX += 0.15f * delta;
		if (super.keyboard.keyOnce(Keyboard.KEY_RETURN)) {
			System.out.println("Enter");
		}
		if (super.keyboard.keyOnce(Keyboard.KEY_F1)) {
			hud.debug = !hud.debug;
			System.out.println("Debuged:" + hud.debug);
		}
		float speed = 0.35f * delta;
		if (super.keyboard.keyPressed(Keyboard.KEY_LEFT)) {
			test.Move(-speed, 0);
		}
		if (super.keyboard.keyPressed(Keyboard.KEY_RIGHT)) {
			test.Move(speed, 0);
		}
		if (super.keyboard.keyPressed(Keyboard.KEY_UP)) {
			test.Move(0,-speed);
		}
		if (super.keyboard.keyPressed(Keyboard.KEY_DOWN)) {
			test.Move(0,speed);
		}
		// keep quad on the screen
		if (test.x < 0) {
			test.x = 0;
		}
		if (test.x > super.displayWidth) {
			test.x = super.displayWidth;
		}
		if (test.y < 0) {
			test.y = 0;
		}
		if (test.y > super.displayHeight) {
			test.y = super.displayHeight;
		}
		
		test.onClick(mouse,new Action(){
			@Override
			public void actionPerformed()
			{
			   System.out.println("clicked");
			}
		});
		super.keyboard.endPoll();
	}

	HUD hud = null;
	Entity test = new Entity(100, 100);

	public void Render() {
		super.Render();

		test.Render();

		hud.Render();
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();
	}
}