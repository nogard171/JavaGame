package ui;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import threads.UIThread;
import utils.Input;
import utils.Renderer;

public class UIEquipment extends UIControl {
	private int displayListID = -1;
	public static HashMap<String, UIBagSlot> equipSlots = new HashMap<String, UIBagSlot>();
	public UIPanel panel;

	public HashMap<String, UIEquipmentSlot> equipmentSlot = new HashMap<String, UIEquipmentSlot>();
	UIEquipmentSlot hoveredSlot;
	public int openOrder = -1;

	private Action getEvents() {
		return new Action() {
			@Override
			public void onMouseDown(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					try {
						UIEquipmentSlot tempSlot = (UIEquipmentSlot) self;
						if (tempSlot != null) {
							if (tempSlot.item != "") {
								if (UIThread.draggingSlot == null) {
									UIThread.draggingSlot = new UIItemSlot(tempSlot.item, tempSlot.count,
											tempSlot.bagIndex, tempSlot.slotIndex, tempSlot.eventAction);
									tempSlot.item = "";
									tempSlot.count = 0;
								}
							}
						}
					} catch (ClassCastException e) {

					}
				}
			}

			@Override
			public void onMouseEnter(UIControl self) {
				try {
					UIItemSlot tempSlot = (UIItemSlot) self;
					if (tempSlot != null) {
						tempSlot.backgroundColor = new Color(0, 0, 0, 0.5f);
					}
				} catch (ClassCastException e) {

				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				try {
					UIItemSlot tempSlot = (UIItemSlot) self;
					if (tempSlot != null) {
						tempSlot.backgroundColor = null;
					}
				} catch (ClassCastException e) {

				}
			}

			@Override
			public void onMouseReleased(UIControl self, int mouseButton) {
				try {
					UIEquipmentSlot tempSlot = (UIEquipmentSlot) self;
					if (tempSlot != null) {
						if (tempSlot.item == "") {
							if (UIThread.draggingSlot != null) {
								if (tempSlot.allowItems.contains(UIThread.draggingSlot.item)) {
									tempSlot.item = UIThread.draggingSlot.item;
									tempSlot.count = UIThread.draggingSlot.count;
									tempSlot.eventAction = UIThread.draggingSlot.eventAction;
									UIThread.draggingSlot = null;
								} else {

								}
							}
						}
					}
				} catch (ClassCastException e) {

				}
			}

		};

	}

	public UIEquipmentSlot generateSlot(String name, int x, int y) {
		System.out.println("Order:" + name);
		UIEquipmentSlot tempSlot = new UIEquipmentSlot();
		tempSlot.setName(name);
		tempSlot.setIcon(name.toUpperCase());
		tempSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		tempSlot.eventAction = getEvents();
		return tempSlot;
	}

	public void setup() {
		panel = new UIPanel();
		panel.onEvent(new Action() {
			@Override
			public void onMouseHover(UIControl control) {
				UIThread.uiHovered = true;
			}

			@Override
			public void onMouseExit(UIControl control) {

			}

			@Override
			public void onMouseReleased(UIControl self) {

			}
		});
		panel.setPosition(this.getPosition());
		panel.setSize(new Size(120, 195));
		String[] names = { "cape_icon", "helm_icon", "money_pouch_icon", "chestplate_icon", "necklace_icon",
				"glove_icon", "legs_icon", "ring_icon", "weapon_icon", "boot_icon", "shield_icon" };

		int x = 0, y = 0, i = 0;
		UIEquipmentSlot tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x = 32 + 5;
		y = 37;

		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;

		x = 0;
		y += 37;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;

		x = 0;
		y += 37;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);
		i++;
		x += 32 + 5;
		tempSlot = generateSlot(names[i], x, y);
		equipmentSlot.put(tempSlot.getName(), tempSlot);

		x = 0;
		y += 37;
		UIEquipmentSlot bagSlot1 = new UIEquipmentSlot();
		bagSlot1.setName("bag_slot1");
		bagSlot1.setIcon("BAG_ICON");
		bagSlot1.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		bagSlot1.eventAction = getEvents();
		equipmentSlot.put("bag_slot1", bagSlot1);
		x += 32 + 5;
		UIEquipmentSlot bagSlot2 = new UIEquipmentSlot();
		bagSlot2.setName("bag_slot2");
		bagSlot2.setIcon("BAG_ICON");
		bagSlot2.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		bagSlot2.eventAction = getEvents();
		equipmentSlot.put("bag_slot2", bagSlot2);
		x += 32 + 5;
		UIEquipmentSlot bagSlot3 = new UIEquipmentSlot();
		bagSlot3.setName("bag_slot3");
		bagSlot3.setIcon("BAG_ICON");
		bagSlot3.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		bagSlot3.eventAction = getEvents();
		equipmentSlot.put("bag_slot3", bagSlot3);

	}

	static Vector2f hoveredItemPosition;

	public void update() {
		super.update();
		if (this.isVisible) {

			panel.update();
			if (panel.hover) {

			}
			for (UIEquipmentSlot slot : equipmentSlot.values()) {
				slot.update();
			}
			if (!Input.isMouseDown(0)) {

			}
		}
	}

	public void render() {
		if (this.isVisible) {
			panel.render();
			for (UIEquipmentSlot slot : equipmentSlot.values()) {

				slot.render();
			}
		}
	}

	public void cleanup() {

	}
}
