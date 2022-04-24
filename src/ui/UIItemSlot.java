package ui;

import org.newdawn.slick.Color;

import classes.Action;

public class UIItemSlot extends UIControl {
	public int bagIndex = -1;
	public int slotIndex = -1;
	public String item = "";
	public int count = 0;
	protected Color backgroundColor;

	public UIItemSlot() {
	}

	public UIItemSlot(String newItem, int newCount) {
		item = newItem;
		count = newCount;
	}

	public UIItemSlot(String newItem, int newCount, int newBagIndex, int newSlotIndex) {
		item = newItem;
		count = newCount;
		bagIndex = newBagIndex;
		slotIndex = newSlotIndex;
	}

	public UIItemSlot(String newItem, int newCount, int newBagIndex, int newSlotIndex, Action newAction) {
		item = newItem;
		count = newCount;
		bagIndex = newBagIndex;
		slotIndex = newSlotIndex;
		this.onEvent(newAction);
	}
}
