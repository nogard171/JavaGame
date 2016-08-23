import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HUD {
	Object test = new Object();
	int height = 0;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void init() {

	}

	public void update(MouseInput mouse) {
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
		test.Render(0, this.height-32);
	}
}
