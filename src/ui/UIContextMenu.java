package ui;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Object;
import classes.Size;
import utils.Renderer;

public class UIContextMenu extends UIControl {
	private String name = "";
	private LinkedList<UIMenuItem> controls = new LinkedList<UIMenuItem>();
	private Color backgroundColor = null;
	public LinkedList<Object> objectsHovered = new LinkedList<Object>();

	public UIContextMenu(String string) {
		this.name = string;
	}

	@Override
	public void update() {
		super.update();
		if (this.isVisible) {
			for (UIMenuItem control : controls) {
				control.update();
			}
		}
	}

	public void removeNonDefault() {
		ArrayList<UIControl> controlsToRemove = new ArrayList<UIControl>();
		for (UIControl temp : this.getControls()) {
			if (!temp.isDefault()) {
				controlsToRemove.add(temp);
			}
		}
		controls.removeAll(controlsToRemove);
	}

	@Override
	public void render() {
		if (this.isVisible) {
			float x = 0;
			float y = 0;
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY(), this.getSize().getWidth() + 5, 16,
					new Color(0.5f, 0.5f, 0.5f, 0.75f));
			Renderer.renderText(this.getPosition().x + x, this.getPosition().y + y, "Choose Option", 12, Color.white);
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY() + 16,
					this.getSize().getWidth() + 5, this.getSize().getHeight() - 16, new Color(0, 0, 0, 0.5f));
			for (UIControl temp : this.getControls()) {
				UIMenuItem item = (UIMenuItem) temp;
				if (item != null) {
					item.setSize(new Size(this.getSize().getWidth(), item.getFontSize()));
					Color bgColor = item.getBackgroundColor();
					if (bgColor != null) {
						Renderer.renderQuad(item.getPosition().x, item.getPosition().y, this.getSize().getWidth() + 5,
								item.getFontSize(), bgColor);
					}
					Renderer.renderText(item.getPosition().x, item.getPosition().y - 3, item.getText(),
							item.getFontSize(), Color.white);
				}

			}
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public void setPosition(Vector2f position) {
		super.setPosition(new Vector2f(position.x+20,position.y));
		int y = 0;
		for (UIMenuItem control : controls) {
			control.setPosition(
					new Vector2f(this.getPosition().x, this.getPosition().y + (y * control.getFontSize()) + 16));
			y++;
		}
	}

	public void add(UIMenuItem ctl) {
		int txtWidth = Renderer.getTextWidth("Choose Option", 12, Color.white);

		int tempTxtWidth = Renderer.getTextWidth(ctl.getText(), ctl.getFontSize(), Color.white);
		if (txtWidth < tempTxtWidth) {
			txtWidth = tempTxtWidth;
		}
		int tempWidth = this.getSize().getWidth();
		if (tempWidth < txtWidth) {
			tempWidth = txtWidth;
		}
		this.setSize(new Size(tempWidth, ((controls.size() + 1) * ctl.getFontSize()) + 16));
		//ctl.setName("Item#" + controls.size());
		ctl.setPosition(new Vector2f(0, ((controls.size()) * ctl.getFontSize())));
		ctl.setSize(new Size(this.getSize().getWidth(), ctl.getFontSize()));
		this.controls.add(ctl);
	}

	public void clear() {
		this.controls.clear();
	}

	public LinkedList<UIMenuItem> getControls() {
		return this.controls;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
