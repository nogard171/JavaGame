import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lwjgl.util.Rectangle;

public class HUD {
	Object test = new Object();
	int height = 0;
	int x = 0;
	int y = 0;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void init() {

	}

	public void updateObjects() {
		test.position = new Rectangle(x, y, 32, 32);
	}

	public void update(MouseInput mouse) {
		if (this.height != y) {
			y = this.height;
			this.updateObjects();
		}

		if (mouse != null) {
			test.pollMouse(mouse);
		}
		test.onClick(new Action() {
			public void run() {
				System.out.println("worked");
			}
		});
	}

	public void Render() {
		test.Render();
	}
}
