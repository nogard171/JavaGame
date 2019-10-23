package classes;

import utils.UIPhyics;
import utils.Window;

public class UIButton extends UIControl {
	private Size size = new Size(32, 32);
	private String material = "GRASS";
	private String text = "";

	private int mouseClickCount = 0;
	private int mouseDownCount = 0;
	private int mouseReleaseCount = 0;
	private Action onClickFunction;
	private Action onDownFunction;
	private Action onReleaseFunction;

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public void update() {
		if (UIPhyics.isColliding(this) && Window.isMouseDown(0)) {
			if (onClickFunction != null && mouseClickCount == 0) {
				onClickFunction.run(this);
			}
			mouseClickCount++;
			mouseReleaseCount = 0;
		} else {
			mouseClickCount = 0;
		}
		if (UIPhyics.isColliding(this) && Window.isMouseDown(0)) {
			if (onDownFunction != null) {
				onDownFunction.run(this);
			}
		}

		if (UIPhyics.isColliding(this) && !Window.isMouseDown(0)) {
			if (onReleaseFunction != null && mouseReleaseCount == 0) {
				onReleaseFunction.run(this);
			}
			mouseReleaseCount++;
		}
	}

	public void onClick(Action action) {
		this.onClickFunction = action;
	}

	public void onDown(Action action) {
		this.onDownFunction = action;
	}

	public void onRelease(Action action) {
		this.onReleaseFunction = action;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.size.setHeight(12);
		this.text = text;
	}
}
