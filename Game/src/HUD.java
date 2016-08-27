import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HUD {
	Object test = new Object();
	int height = 0;
	int x = 0;
	int y = 0;

	public HUD() {
		init();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void init() {
		//test.setTexture("/res/img/window.png");
		test.setColor(new Color(255,255,255));
	}

	public void updateObjects() {
		test.position = new Rectangle(x, y, 32, 32);
	}

	public void update(MouseInput mouse) {
		if (this.height != y) {
			y = this.height;
			this.updateObjects();
		}

	}

	public void Render() {
		test.Render();
	}
}
