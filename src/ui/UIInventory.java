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

public class UIInventory extends UIControl {
	private int displayListID = -1;
	public static HashMap<String, UIBagSlot> bagSlots = new HashMap<String, UIBagSlot>();
	public UIPanel panel;
	private Size spacing = new Size(5, 5);
	private Size itemCount = new Size(0, 0);
	int count = 0;
	public int openOrder = -1;
	private static boolean needsUpdating = false;

	@Override
	public void setPosition(Vector2f newPosition) {
		if (!this.getPosition().equals(newPosition)) {
			super.setPosition(newPosition);
			if (panel != null) {
				panel.setPosition(newPosition);
			}
			needsUpdating = true;
		}
	}

	public static void addItem(String item, int count) {
		System.out.println("Count: " + count);
		boolean added = false;
		for (UIBagSlot bagSlot : bagSlots.values()) {
			if (bagSlot != null) {
				for (UIItemSlot itemSlot : bagSlot.itemSlots) {
					if (itemSlot != null) {
						if (itemSlot.item.equals("")) {
							itemSlot.item = item;
							itemSlot.count = count;

							addEventsToItemSlot(itemSlot);

							needsUpdating = true;
							added = true;
						}
					}
					if (added) {
						break;
					}
				}

			}
			if (added) {
				break;
			}
		}
	}

	private static void addEventsToItemSlot(UIItemSlot itemSlot) {
		itemSlot.onEvent(new Action() {
			@Override
			public void onMouseDown(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					try {
						UIItemSlot tempSlot = (UIItemSlot) self;
						if (tempSlot != null) {
							if (tempSlot.item != "") {
								if (UIThread.draggingSlot == null) {
									UIThread.draggingSlot = new UIItemSlot(tempSlot.item, tempSlot.count,
											tempSlot.bagIndex, tempSlot.slotIndex, tempSlot.eventAction);
									tempSlot.item = "";
									tempSlot.count = 0;
									needsUpdating = true;
								}
							}
						}
					} catch (ClassCastException e) {

					}
				}
			}

			@Override
			public void onMouseEnter(UIControl self) {
				hoveredItemPosition = self.getPosition();
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
				hoveredItemPosition = null;
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
					UIItemSlot tempSlot = (UIItemSlot) self;
					if (tempSlot != null) {
						if (tempSlot.item == "") {
							if (UIThread.draggingSlot != null) {
								tempSlot.item = UIThread.draggingSlot.item;
								tempSlot.count = UIThread.draggingSlot.count;
								tempSlot.eventAction = UIThread.draggingSlot.eventAction;
								needsUpdating = true;
								UIThread.draggingSlot = null;
							}
						}
					}
				} catch (ClassCastException e) {

				}
			}

		});
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
		count = 0;
		for (int b = 0; b < 4; b++) {
			UIBagSlot slot = new UIBagSlot();
			for (int i = 0; i < slot.count; i++) {
				UIItemSlot itemSlot = new UIItemSlot("" + (count < 5 ? "TEST_ITEM" : ""), 1, b, i);
				addEventsToItemSlot(itemSlot);
				slot.itemSlots.add(itemSlot);
				count++;
			}
			bagSlots.put("" + b, slot);
		}
		itemCount.setWidth(7);
		itemCount.setHeight((int) Math.ceil((double) count / (double) 7));
		panel.setSize(new Size(((32 + spacing.getWidth()) * 7) + 10,
				((32 + spacing.getHeight()) * (int) Math.ceil((double) count / (double) 7)) + 10));

	}

	public void setItemSlot(UIItemSlot slot) {
		if (slot != null) {
			UIBagSlot bagSlot = bagSlots.get(slot.bagIndex + "");
			if (bagSlot != null) {
				UIItemSlot itemSlot = bagSlot.itemSlots.get(slot.slotIndex);
				if (itemSlot != null) {
					bagSlot.itemSlots.set(slot.slotIndex, slot);
					needsUpdating = true;
					UIThread.draggingSlot = null;
				}
			}
		}
	}

	static Vector2f hoveredItemPosition;

	public void update() {

		if (this.isVisible) {
			super.update();
			panel.update();
			if (panel.hover) {
				for (UIBagSlot bagSlot : bagSlots.values()) {
					if (bagSlot != null) {
						bagSlot.update();
					}
				}
			}
		}
	}

	private void build() {
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		int maxWidth = panel.getSize().getWidth() / (32 + 2);
		int x = 0, y = 0, c = 0;
		for (UIBagSlot bagSlot : bagSlots.values()) {
			if (bagSlot != null) {
				for (UIItemSlot itemSlot : bagSlot.itemSlots) {
					if (itemSlot != null) {
						itemSlot.setPosition(new Vector2f((x * (32 + spacing.getWidth()) + 5) + this.getPosition().x,
								(y * (32 + spacing.getHeight())) + 5 + this.getPosition().y));
						GL11.glColor3f(1, 1, 1);
						Renderer.renderModel("ITEM", itemSlot.item, itemSlot.getPosition().x, itemSlot.getPosition().y);
						y = (x >= maxWidth - 1 ? y + 1 : y);
						x = (x >= maxWidth - 1 ? 0 : x + 1);
						c++;
					}
				}
			}
		}
		int maxCount = (itemCount.getWidth() * itemCount.getHeight());
		int remainingCount = maxCount - c;
		for (int i = 0; i < remainingCount; i++) {
			Renderer.renderModel("ITEM", "ITEM_BLOCK", (x * (32 + spacing.getWidth()) + 5) + this.getPosition().x,
					(y * (32 + spacing.getHeight())) + 5 + this.getPosition().y);
			y = (x >= maxWidth - 1 ? y + 1 : y);
			x = (x >= maxWidth - 1 ? 0 : x + 1);
		}

		GL11.glEnd();
		GL11.glEndList();
	}

	public void render() {
		if (this.isVisible) {
			panel.render();
			if (displayListID == -1 || needsUpdating) {
				System.out.println("Building...");
				build();
				needsUpdating = false;
			}

			if (hoveredItemPosition != null) {
				Renderer.renderQuad(hoveredItemPosition.x, hoveredItemPosition.y, 32, 32, new Color(0, 0, 0, 0.5f));
			}
			Renderer.bindTexture();
			if (displayListID != -1) {
				GL11.glCallList(displayListID);
			}
		}
	}

	public void cleanup() {

	}

	public void resetDrag() {
		this.setItemSlot(UIThread.draggingSlot);
	}
}
