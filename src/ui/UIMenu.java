package ui;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Size;
import data.EngineData;
import utils.Input;
import utils.Renderer;
import utils.UIPhyics;
import utils.Window;

public class UIMenu extends UIControl {
	private String name = "";
	private LinkedList<UIMenuItem> controls = new LinkedList<UIMenuItem>();
	private Color backgroundColor = null;

	public UIMenu(String string) {
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
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY(), this.getSize().getWidth() + 5,
					this.getSize().getHeight(), new Color(0, 0, 0, 0.5f));
			for (UIControl temp : this.getControls()) {
				UIMenuItem item = (UIMenuItem) temp;
				if (item != null) {
					item.setSize(new Size(this.getSize().getWidth(), item.getFontSize()));
					Color bgColor = item.getBackgroundColor();
					if (bgColor != null) {
						Renderer.renderQuad(this.getPosition().x + x, this.getPosition().y + y,
								this.getSize().getWidth() + 5, item.getFontSize(), bgColor);
					}
					Renderer.renderText(this.getPosition().x + x, this.getPosition().y + y - 6, item.getText(),
							item.getFontSize(), Color.white);
					y += item.getFontSize();
				}

			}
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public void setPosition(Vector2f position) {
		super.setPosition(position);
		int y = 0;
		for (UIMenuItem control : controls) {
			control.setPosition(new Vector2f(this.getPosition().x, this.getPosition().y + (y * control.getFontSize())));
			y++;
		}
	}

	public void add(UIMenuItem ctl) {
		int txtWidth = Renderer.getTextWidth(ctl.getText(), ctl.getFontSize(), Color.white);
		int tempWidth = this.getSize().getWidth();
		if (tempWidth < txtWidth) {
			tempWidth = txtWidth;
		}
		this.setSize(new Size(tempWidth, ((controls.size() + 1) * ctl.getFontSize())));
		ctl.setPosition(
				new Vector2f(this.getPosition().x, this.getPosition().y + ((controls.size()) * ctl.getFontSize())));
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

	public void setVisible(boolean visible) {
		for (UIMenuItem control : controls) {
			control.isVisible = visible;
		}
		this.isVisible = visible;
	}
}
