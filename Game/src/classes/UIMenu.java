package classes;

import java.util.ArrayList;

import data.UIData;

public class UIMenu extends UIControl {
	private String name = "";
	private ArrayList<UIButton> buttons = new ArrayList<UIButton>();

	public UIMenu(String string) {
		this.name = string;
	}

	public String getName() {
		return name;
	}

	public void addButton(UIButton btn) {
		this.buttons.add(btn);
	}

	public ArrayList<UIButton> getButtons() {
		return this.buttons;
	}

	@Override
	public void update() {
		for (UIControl control : buttons) {
			control.update();
		}
	}
}
