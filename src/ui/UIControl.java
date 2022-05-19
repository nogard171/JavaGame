package ui;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;

import classes.Action;
import classes.Size;
import utils.Input;
import utils.UIPhyics;

public class UIControl {
	private Size size = new Size(32, 32);
	private String name = "";
	private Vector2f position = new Vector2f(0, 0);

	private boolean isDefault = false;
	public boolean isVisible = true;
	protected boolean hover = false;

	public boolean isHovered() {
		return hover;
	}

	protected long hoverStartTime;
	protected Action eventAction;
	protected int mouseClickCount = 0;
	private int mouseReleaseCount = 0;
	private int mouseEnterCount = 0;
	private int mouseExitCount = 0;
	private Point mousePosition = new Point(-1, -1);

	public UIControl() {

	}

	Vector2f previousMouse = new Vector2f(0, 0);

	public void update() {
		if (this.isVisible) {
			if (this.eventAction != null) {
				if (!previousMouse.equals(Input.getMousePosition())) {
					this.eventAction.onMouseMove(this,
							new Vector2f((float) Input.getMousePosition().x - (float) previousMouse.x,
									(float) Input.getMousePosition().y - (float) previousMouse.y));
					previousMouse = Input.getMousePosition();
				}

				hover = UIPhyics.inside(this, Input.getMousePosition());
				if (hover) {
					if (hoverStartTime == -1) {
						hoverStartTime = System.currentTimeMillis();
					}
					this.eventAction.onMouseHover(this);
					this.eventAction.onMouseHover(this, System.currentTimeMillis() - hoverStartTime);
					if (mouseEnterCount == 0) {
						this.eventAction.onMouseEnter(this);
						mouseExitCount = 0;
					}
					mouseEnterCount++;
				}
				if (!hover) {
					hoverStartTime = -1;
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
						this.eventAction.onMouseClick(this);
					}
					mouseClickCount++;
					mouseReleaseCount = 0;
				} else if (!hover && mouseDown) {
					if (mouseClickCount == 0) {
						this.eventAction.onMouseClickOut(this);
					}
					mouseClickCount++;
					mouseReleaseCount = 0;
				} else {
					mouseClickCount = 0;
				}
				if (hover && mouseDown) {
					this.eventAction.onMouseDown(this, mouseIndex);
					this.eventAction.onMouseDown(this);
				}

				if (hover && !mouseDown) {
					if (mouseReleaseCount == 0) {
						this.eventAction.onMouseReleased(this, mouseIndex);
						this.eventAction.onMouseReleased(this);
					}
					mouseReleaseCount++;
				}
				if (hover && !mouseDown) {
					if (mouseReleaseCount == 0) {
						this.eventAction.onMouseReleased(this, mouseIndex);
						this.eventAction.onMouseReleased(this);
					}
					mouseReleaseCount++;
				}
				if (hover
						&& !mousePosition
								.equals(new Point((int) Input.getMousePosition().x, (int) Input.getMousePosition().y))
						&& mouseDown) {
					this.eventAction.onMouseDrag(this, mouseIndex);
					Point mouseOffset = new Point(
							(int) Input.getMousePosition().getX() - (int) this.getPosition().getX(),
							(int) Input.getMousePosition().getY() - (int) this.getPosition().getY());
					this.eventAction.onMouseDrag(this, mouseOffset);
					mousePosition = new Point((int) Input.getMousePosition().x, (int) Input.getMousePosition().y);
				}
			}
		}
	}

	public void render() {

	}

	public void onEvent(Action action) {
		this.eventAction = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public void toggle() {
		isVisible = !isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
