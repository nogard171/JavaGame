package classes;

import org.newdawn.slick.Color;

import utils.Input;
import utils.UIPhyics;
import utils.Window;

public class UIMenuItem extends UIControl {
	private String text = "";
	private Color backgroundColor = null;

	private Action eventAction;
	private int mouseClickCount = 0;
	private int mouseReleaseCount = 0;
	private int mouseEnterCount = 0;
	private int mouseExitCount = 0;

	@Override
	public void update() {
		if (this.eventAction != null) {
			boolean hover = UIPhyics.inside(this, Input.getMousePosition());
			if (hover) {
				this.eventAction.onMouseHover(this);
				if (mouseEnterCount == 0) {
					this.eventAction.onMouseEnter(this);
					mouseExitCount = 0;
				}
				mouseEnterCount++;
			}
			if (!hover) {
				if (mouseExitCount == 0) {
					this.eventAction.onMouseExit(this);
					mouseEnterCount = 0;
				}
				mouseExitCount++;
			}
			int mouseWheel = Input.getMouseWheel();
			if (hover && mouseWheel > 0) {
				this.eventAction.onMouseWheelUp(this);
			} else if (hover && mouseWheel < 0) {
				this.eventAction.onMouseWheelDown(this);
			}

			int mouseIndex = Input.getMouseButton();
			boolean mouseDown = Input.isMouseDown(mouseIndex);
			if (hover && mouseDown) {
				if (mouseClickCount == 0) {
					this.eventAction.onMouseClick(this, mouseIndex);
				}
				mouseClickCount++;
				mouseReleaseCount = 0;
			} else {
				mouseClickCount = 0;
			}
			if (hover && mouseDown) {
				this.eventAction.onMouseDown(this, mouseIndex);
			}

			if (hover && !mouseDown) {
				if (mouseReleaseCount == 0) {
					this.eventAction.onMouseReleased(this, mouseIndex);
				}
				mouseReleaseCount++;
			}
		}

	}

	public void onEvent(Action action) {
		this.eventAction = action;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.setSize(new Size(getSize().getWidth(), 12));
		this.text = text;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
