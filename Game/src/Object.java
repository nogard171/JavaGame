import java.awt.event.MouseEvent;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

public class Object extends Sprite {

	Rectangle position = new Rectangle(0, 0, 32, 32);
	boolean isSolid = false;
	boolean isClicked = false;
	boolean isHovered = false;
	private Action action = new Action();

	public void onClick(Action act) {
		if (act == null) {
			act = this.action;
		}
		if (isClicked) {
			act.run();
		}
	}

	int mouseClicked = 0;

	public void Render() {
		this.Render(this.position.getX(), this.position.getY());
	}

	public void pollMouse(MouseInput mouse) {

		if (this.position.contains(mouse.getPosition().getX(),
				mouse.getPosition().getY() + this.position.getHeight())) {
			this.isHovered = true;
		} else {
			this.isHovered = false;
		}
		if (mouse.mouseDown(0) && this.isHovered && this.mouseClicked == 0) {
			this.isClicked = true;
			this.mouseClicked++;
		} else if (!mouse.mouseDown(0)) {
			this.mouseClicked = 0;
			this.isClicked = false;
		} else {
			this.isClicked = false;
		}
	}
}
