package ui;

import classes.Action;
import classes.Size;
import utils.Input;
import utils.UIPhyics;
import utils.Window;

public class UIButton extends UIControl {
	private String material = "GRASS";
	private String text = "";

	private Action eventAction;
	private int mouseClickCount = 0;
	private int mouseReleaseCount = 0;
	private int mouseEnterCount = 0;
	private int mouseExitCount = 0;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public void update() {
		if (this.eventAction != null) {
			boolean hover = UIPhyics.inside(this, Input.getMousePosition());
			if (hover) {
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
			boolean mouseDown = false;
			int mouseButtonIndex = -1;
			int mouseWheel = Input.getMouseWheel();
			for (int b = 0; b < Input.getMouseButtonCount(); b++) {
				if (Input.isMouseDown(b)) {
					mouseButtonIndex = b;
					mouseDown = true;
				}
			}
			if (hover && mouseWheel > 0) {
				this.eventAction.onMouseWheelUp(this);
			} else if (hover && mouseWheel < 0) {
				this.eventAction.onMouseWheelDown(this);
			}

			if (hover && mouseDown) {
				if (mouseClickCount == 0) {
					this.eventAction.onMouseClick(this, mouseButtonIndex);
				}
				mouseClickCount++;
				mouseReleaseCount = 0;
			} else {
				mouseClickCount = 0;
			}
			if (hover && mouseDown) {
				this.eventAction.onMouseDown(this, mouseButtonIndex);
			}

			if (hover && !mouseDown) {
				if (mouseReleaseCount == 0) {
					this.eventAction.onMouseReleased(this, mouseButtonIndex);
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
}
