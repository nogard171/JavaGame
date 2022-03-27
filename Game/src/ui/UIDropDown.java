package ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import utils.Input;
import utils.UIPhyics;

public class UIDropDown extends UIControl {
	private int selectedValue = 0;
	private LinkedList<String> values = new LinkedList<String>();
	public Rectangle dropDownBounds = new Rectangle();
	private boolean show = false;
	private String value = "Auto";

	public void addValue(String nameValue) {
		values.add(nameValue);
		dropDownBounds = new Rectangle((int) this.getPosition().getX(), (int) this.getPosition().getY() + 16, 100,
				16 * values.size());
	}

	public String getValue(int index) {
		return values.get(index);
	}

	public LinkedList<String> getValues() {
		return values;
	}

	public int getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(int selectedValue) {
		this.selectedValue = selectedValue;
	}

	public boolean isShown() {
		return show;
	}

	public void setShown(boolean show) {
		this.show = show;
	}

	@Override
	public void update() {
		super.update();
		if (this.show) {
			boolean hoveredInDropdown = UIPhyics.inside(dropDownBounds, Input.getMousePosition());
			if (hoveredInDropdown) {
				selectedValue = (int) (Input.getMousePosition().getY() - this.getPosition().getY()) / 16;
				System.out.println("Hobert" + selectedValue + "/" + values.size());
				if (values.size() >= selectedValue) {
					if (Input.isMousePressed(0)) {
						this.eventAction.onSelect(this, selectedValue - 1);
					}
				}
			} else if(!this.hover){
				this.setShown(false);
			}
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
