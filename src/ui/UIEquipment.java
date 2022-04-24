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
		panel.setSize(new Size(128, 200));

		int x = 0, y = 0;
		UIEquipmentSlot capeSlot = new UIEquipmentSlot();
		capeSlot.setPosition(new Vector2f(this.getPosition().x + 5, this.getPosition().y + 5));
		equipmentSlot.put("cape_slot", capeSlot);
		x += 32 + 5;
		UIEquipmentSlot helmSlot = new UIEquipmentSlot();
		helmSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5));
		equipmentSlot.put("helm_slot", helmSlot);
		x = 32 + 5;
		y = 37;
		UIEquipmentSlot chestSlot = new UIEquipmentSlot();
		chestSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("chest_slot", chestSlot);
		x += 32 + 5;
		UIEquipmentSlot neckalaceSlot = new UIEquipmentSlot();
		neckalaceSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("neckalace_slot", neckalaceSlot);

		x = 0;
		y += 37;
		UIEquipmentSlot glovesSlot = new UIEquipmentSlot();
		glovesSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("gloves_slot", glovesSlot);
		x += 32 + 5;
		UIEquipmentSlot legsSlot = new UIEquipmentSlot();
		legsSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("legs_slot", legsSlot);
		x += 32 + 5;
		UIEquipmentSlot ringsSlot = new UIEquipmentSlot();
		ringsSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("rings_slot", ringsSlot);

		x = 32 + 5;
		y += 37;
		UIEquipmentSlot bootsSlot = new UIEquipmentSlot();
		bootsSlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("boots_slot", bootsSlot);
		x += 32 + 5;
		UIEquipmentSlot moneySlot = new UIEquipmentSlot("MONEY_POUCH_ICON");
		moneySlot.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("money_slot", moneySlot);

		x = 0;
		y += 37;
		UIEquipmentSlot bagSlot1 = new UIEquipmentSlot();
		bagSlot1.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("bag_slot1", bagSlot1);
		x += 32 + 5;
		UIEquipmentSlot bagSlot2 = new UIEquipmentSlot();
		bagSlot2.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
		equipmentSlot.put("bag_slot2", bagSlot2);
		x += 32 + 5;
		UIEquipmentSlot bagSlot3 = new UIEquipmentSlot();
		bagSlot3.setPosition(new Vector2f(this.getPosition().x + 5 + x, this.getPosition().y + 5 + y));
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
		panel.render();

		for (UIEquipmentSlot slot : equipmentSlot.values()) {
			slot.render();
		}
	}

	public void cleanup() {

	}
}
