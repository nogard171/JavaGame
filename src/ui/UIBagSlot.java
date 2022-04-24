package ui;

import java.util.LinkedList;

public class UIBagSlot extends UIControl {
	public int count = 10;
	public LinkedList<UIItemSlot> itemSlots = new LinkedList<UIItemSlot>();

	@Override
	public void update() {
		super.update();
		for (UIItemSlot itemSlot : itemSlots) {
			itemSlot.update();
		}
	}
}
