package classes;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import data.EngineData;
import utils.Input;
import utils.Renderer;
import utils.UIPhyics;

public class UIMenu extends UIControl {
	private String name = "";
	private ArrayList<UIMenuItem> controls = new ArrayList<UIMenuItem>();

	public UIMenu(String string) {
		this.name = string;
		this.toggle();
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

	public String getName() {
		return name;
	}

	@Override
	public void setPosition(Vector2f position) {
		super.setPosition(position);
		int y = 0;
		for (UIMenuItem control : controls) {
			control.setPosition(new Vector2f(this.getPosition().x, this.getPosition().y + (y * 12)));
			y++;
		}
	}

	public void add(UIMenuItem ctl) {
		int txtWidth = Renderer.getTextWidth(ctl.getText(), 12, Color.white);
		int tempWidth = this.getSize().getWidth();
		if (tempWidth < txtWidth) {
			tempWidth = txtWidth;
		}
		this.setSize(new Size(tempWidth, ((controls.size() + 1) * 12)));
		ctl.setPosition(new Vector2f(this.getPosition().x, this.getPosition().y + ((controls.size()) * 12)));
		ctl.setSize(new Size(tempWidth, 12));
		this.controls.add(ctl);
	}

	public void clear() {
		this.controls.clear();
	}

	public ArrayList<UIMenuItem> getControls() {
		return this.controls;
	}

}
