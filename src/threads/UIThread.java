package threads;

import java.awt.Point;
import java.text.ParseException;
import java.util.LinkedList;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Index;
import classes.Size;
import classes.Task;
import classes.TaskManager;
import classes.TaskType;
import classes.View;
import classes.World;
import data.AssetData;
import data.EngineData;
import ui.MainMenu;
import ui.UIButton;
import ui.UIContextMenu;
import ui.UIControl;
import ui.UIEquipment;
import ui.UIEquipmentSlot;
import ui.UIHUDMenu;
import ui.UIInventory;
import ui.UIItemSlot;
import ui.UIMenu;
import ui.UIMenuItem;
import ui.UIPanel;
import utils.APathFinder;
import utils.FPS;
import utils.Input;
import utils.Loader;
import classes.Object;
import classes.Path;
import utils.Renderer;
import utils.SettingsHandler;
import utils.Ticker;
import utils.Window;

public class UIThread {
	UIInventory inventory;
	UIEquipment equipment;
	TaskManager taskMgr;
	MainMenu mainMenu;
	UIContextMenu contextMenu;
	UIHUDMenu hudMenu;
	public static boolean uiHovered = false;
	public

	static UIItemSlot draggingSlot = null;

	public void getOpenedOrder() {
		int oi = -1, oe = -1;

		if (equipment.isVisible && inventory.isVisible) {
			oe = 0;
			oi = 1;
		}

		if (equipment.isVisible && !inventory.isVisible) {
			oe = 0;
			oi = -1;
		}
		if (!equipment.isVisible && inventory.isVisible) {
			oe = -1;
			oi = 0;
		}
		inventory.openOrder = oi;
		equipment.openOrder = oe;
	}

	public void setup() {
		hudMenu = new UIHUDMenu("HUDMenu");
		UIMenuItem inventoryBtn = new UIMenuItem();
		inventoryBtn.setIcon("INVENTORY_ICON");
		inventoryBtn.setSize(new Size(32, 32));
		inventoryBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				System.out.println("test");
				if (mouseButton == 0) {
					inventory.isVisible = !inventory.isVisible;
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				uiHovered = true;
				UIMenuItem temp = (UIMenuItem) self;
				if (temp != null) {
					temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIMenuItem temp = (UIMenuItem) self;
				if (temp != null) {
					temp.setBackgroundColor(null);
				}
			}
		});
		hudMenu.add(inventoryBtn);
		UIMenuItem equipmentBtn = new UIMenuItem();
		equipmentBtn.setIcon("EQUIPMENT_ICON");
		equipmentBtn.setSize(new Size(32, 32));
		equipmentBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				System.out.println("test");
				if (mouseButton == 0) {
					equipment.isVisible = !equipment.isVisible;
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				uiHovered = true;
				UIMenuItem temp = (UIMenuItem) self;
				if (temp != null) {
					temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIMenuItem temp = (UIMenuItem) self;
				if (temp != null) {
					temp.setBackgroundColor(null);
				}
			}
		});
		hudMenu.add(equipmentBtn);

		inventory = new UIInventory();
		inventory.setPosition(new Vector2f(0, 32));
		inventory.setup();

		equipment = new UIEquipment();
		equipment.setPosition(new Vector2f(0, 32));
		equipment.setup();

		taskMgr = new TaskManager();

		contextMenu = new UIContextMenu("contextMenu");
		contextMenu.onEvent(new Action() {
			@Override
			public void onMouseExit(UIControl self) {
				self.isVisible = false;
			}

			@Override
			public void onMouseMove(UIControl self, Vector2f mousePosition) {
				if (self.getName().equals("contextMenu") && !self.isHovered()) {
					Vector2f offset = new Vector2f(mousePosition.x, mousePosition.y);
					if (offset.x < 0 && offset.y < 0) {
						self.isVisible = false;
						System.out.println("Offset:" + offset);
					}
				}
			}
		});

		mainMenu = new MainMenu();
		mainMenu.setup();

		UIButton test = new UIButton();
		test.setName("move");
		test.setText("Move");
		test.setPosition(new Vector2f(100, 100));
		test.setSize(new Size(32, 32));
		test.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					System.out.println("Mouse Click Left");
				}
				if (mouseButton == 1) {
					System.out.println("Mouse Click Right");
				}
				if (mouseButton == 2) {
					System.out.println("Mouse Click Middle");
				}
			}
		});
	}

	private Point getClosestIndex(Index index) {
		Point newPoint = new Point(index.getX(), index.getY());
		double lowestDist = 1000;

		Point tempPoint = new Point(index.getX() - 1, index.getY());
		double dist = getDistance(tempPoint);
		if (lowestDist >= dist) {
			newPoint = tempPoint;
			lowestDist = dist;
		}
		tempPoint = new Point(index.getX() + 1, index.getY());
		dist = getDistance(tempPoint);
		if (lowestDist >= dist) {
			newPoint = tempPoint;
			lowestDist = dist;
		}
		tempPoint = new Point(index.getX(), index.getY() - 1);
		dist = getDistance(tempPoint);
		if (lowestDist >= dist) {
			newPoint = tempPoint;
			lowestDist = dist;
		}
		tempPoint = new Point(index.getX(), index.getY() + 1);
		dist = getDistance(tempPoint);
		if (lowestDist >= dist) {
			newPoint = tempPoint;
			lowestDist = dist;
		}

		return newPoint;
	}

	public void buildContext() {
		if (objectsHovered.size() > 0) {
			contextMenu.removeNonDefault();
			for (Object obj : objectsHovered) {
				UIMenuItem tempItem = new UIMenuItem();
				tempItem.setName(obj.getIndex() + "");
				tempItem.setFontSize(12);
				switch (obj.getModel().toLowerCase()) {
				case "tree":
					tempItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								String[] data = self.getName().split(",");
								try {
									Index tempIndex = new Index(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
									Object obj = World.getObjectAtIndex(tempIndex);
									if (obj != null) {
										int dist = (int) getDistance(
												new Point(obj.getIndex().getX(), obj.getIndex().getY()));
										if (dist < range) {
											Point objTemp = getClosestIndex(obj.getIndex());
											System.out.println("nearest:" + obj.getIndex() + "=" + objTemp);
											Task task = new Task(TaskType.MOVE,
													new Path(new Point(World.character.getIndex().getX(),
															World.character.getIndex().getY()), objTemp));
											TaskManager.addTask(task);

											task = new Task(TaskType.CHOP,
													new Path(
															new Point(World.character.getIndex().getX(),
																	World.character.getIndex().getY()),
															new Point(obj.getIndex().getX(), obj.getIndex().getY())));
											TaskManager.addTask(task);
										} else {
											System.out.println("You cannot move that far.");
										}
									}
								} catch (NumberFormatException e) {
									System.out.println("index:" + e.getMessage());

								}
							}
						}

						@Override
						public void onMouseHover(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
							}
						}

						@Override
						public void onMouseExit(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(null);
							}
						}
					});
					tempItem.setText("Chop Tree");
					contextMenu.add(tempItem);
					break;
				case "tile":
					tempItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								Object obj = UIThread.objectsHovered.getLast();
								if (obj != null) {
									int dist = (int) getDistance(
											new Point(obj.getIndex().getX(), obj.getIndex().getY()));
									if (dist < range) {
										Point objTemp = getClosestIndex(obj.getIndex());
										System.out.println("nearest:" + obj.getIndex() + "=" + objTemp);
										Task task = new Task(TaskType.MOVE,
												new Path(new Point(World.character.getIndex().getX(),
														World.character.getIndex().getY()), objTemp));
										TaskManager.addTask(task);
									} else {
										System.out.println("You cannot move that far.");
									}
								}
							}
						}

						@Override
						public void onMouseHover(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
							}
						}

						@Override
						public void onMouseExit(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(null);
							}
						}
					});
					tempItem.setText("Move Here");
					contextMenu.add(tempItem);
					break;
				default:
				}
			}
		}
	}

	public void update() {
		uiHovered = false;
		hudMenu.update();
		inventory.update();
		equipment.update();
		if (!EngineData.pauseGame) {
			taskMgr.update();
		}
		mainMenu.update();
		contextMenu.update();
		getOpenedOrder();
		objectsHovered = World.getHoveredObjects();
		if (uiHovered) {
			objectsHovered.clear();
		}
		if (Input.isKeyPressed(Keyboard.KEY_F1)) {
			EngineData.showTelematry = !EngineData.showTelematry;
		}
		if (Input.isKeyPressed(Keyboard.KEY_F2)) {
			SettingsHandler.load();
			Loader.load();
			World.rebuild();
			EngineData.dataLoaded = true;
		}
		if (Input.isMousePressed(0) && !contextMenu.isVisible) {
			if (objectsHovered.size() > 0) {
				Object obj = objectsHovered.getLast();
				if (obj != null) {
					if (obj.getModel().equals("TILE")) {
						int dist = (int) getDistance(new Point(obj.getIndex().getX(), obj.getIndex().getY()));
						System.out.println("Dist:" + dist);
						if (dist < range) {

							Task task = new Task(TaskType.MOVE,
									new Path(
											new Point(World.character.getIndex().getX(),
													World.character.getIndex().getY()),
											new Point(obj.getIndex().getX(), obj.getIndex().getY())));
							TaskManager.addTask(task);
						} else {
							System.out.println("You cannot move that far.");
						}
					} else if (obj.getModel().equals("TREE")) {
						Point objTemp = getClosestIndex(obj.getIndex());
						Task task = new Task(TaskType.MOVE,
								new Path(
										new Point(World.character.getIndex().getX(), World.character.getIndex().getY()),
										objTemp));
						TaskManager.addTask(task);

						task = new Task(TaskType.CHOP,
								new Path(
										new Point(World.character.getIndex().getX(), World.character.getIndex().getY()),
										new Point(obj.getIndex().getX(), obj.getIndex().getY())));
						TaskManager.addTask(task);

					} else if (obj.getModel().equals("CUBE") && obj.getMaterial().contains("ORE")) {
						Point objTemp = getClosestIndex(obj.getIndex());
						Task task = new Task(TaskType.MOVE,
								new Path(
										new Point(World.character.getIndex().getX(), World.character.getIndex().getY()),
										objTemp));
						TaskManager.addTask(task);

						task = new Task(TaskType.MINE,
								new Path(
										new Point(World.character.getIndex().getX(), World.character.getIndex().getY()),
										new Point(obj.getIndex().getX(), obj.getIndex().getY())));
						TaskManager.addTask(task);

					}
				}
			}
		}
		if (Input.isMousePressed(1) && !contextMenu.isVisible && true == false) {
			buildContext();
			contextMenu.objectsHovered = objectsHovered;
			contextMenu.isVisible = true;
			contextMenu.setPosition(Input.getMousePosition());
		}

		EngineData.pauseGame = EngineData.showMainMenu;

		if (!Input.isMouseDown(0)) {
			if (UIThread.draggingSlot instanceof UIEquipmentSlot) {

			}
			if (UIThread.draggingSlot instanceof UIItemSlot) {
				inventory.resetDrag();
			}
		}
	}

	boolean test = false;
	int range = 1000;

	public void render() {
		if (uiHovered) {
			objectsHovered.clear();
		}

		hudMenu.render();
		int eWidth = equipment.panel.getSize().getWidth() * (equipment.openOrder + 1);
		equipment.render();
		int iWidth = inventory.panel.getSize().getWidth() * inventory.openOrder;
		inventory.setPosition(new Vector2f(eWidth, inventory.getSize().getHeight()));
		inventory.render();

		mainMenu.render();

		contextMenu.render();

		if (EngineData.showTelematry) {
			Vector2f debugPosition = new Vector2f(Window.getWidth() - 200, 0);
			Renderer.renderQuad(debugPosition.x, debugPosition.y, 200, 100, new Color(0, 0, 0, 0.5f));
			int tempY = 0;
			Renderer.renderText(debugPosition.x, debugPosition.y, "FPS:" + FPS.currentfps + "(" + FPS.getDelta() + ")",
					12, Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Resolution:" + Display.getWidth() + "x" + Display.getHeight() + "@" + EngineData.frequency, 12,
					Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Chunk Count:" + World.chunksRendered + "(" + EngineData.chunks.size() + ")", 12, Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"View Index:" + View.getIndex().x + "," + View.getIndex().y, 12, Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Blocked Input:" + (EngineData.blockInput ? true : (UIThread.uiHovered ? true : false)), 12,
					Color.white);
			tempY += 12;

			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Update Time:" + EngineData.timings.get("update") + "us", 12, Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Render Time:" + EngineData.timings.get("render") + "us", 12, Color.white);

			if (objectsHovered.size() > 0) {
				Renderer.renderText(debugPosition.x - 200, debugPosition.y, "Hover Index:", 12, Color.white);
				int y = 12;
				for (int i = objectsHovered.size() - 1; i >= 0; i--) {
					Object obj = objectsHovered.get(i);
					if (obj != null) {
						Renderer.renderText(debugPosition.x - 200, y, obj.toString(), 12, Color.white);
						y += 12;
					}
				}
			}
		}
		GL11.glBegin(GL11.GL_POLYGON);
		Renderer.renderModel("ITEM", "CURSOR", Input.getMousePosition().getX(), Input.getMousePosition().getY());
		GL11.glEnd();

		if (objectsHovered.size() > 0 && !EngineData.pauseGame) {
			Object obj = objectsHovered.getLast();
			if (obj != null) {
				if (obj.getModel().toLowerCase().equals("tree")) {
					GL11.glBegin(GL11.GL_POLYGON);
					Renderer.renderModel("ITEM", "CURSOR_CHOP", Input.getMousePosition().getX(),
							Input.getMousePosition().getY());
					GL11.glEnd();
				}

				if (obj.getModel().toLowerCase().equals("cube") && obj.getMaterial().toLowerCase().contains("ore")) {
					GL11.glBegin(GL11.GL_POLYGON);
					Renderer.renderModel("ITEM", "CURSOR_MINE", Input.getMousePosition().getX(),
							Input.getMousePosition().getY());
					GL11.glEnd();
				}
				if (obj.getModel().toLowerCase().equals("tile")
						&& obj.getMaterial().toLowerCase().contains("tiled_dirt")) {
					GL11.glBegin(GL11.GL_POLYGON);
					Renderer.renderModel("ITEM", "CURSOR_HOE", Input.getMousePosition().getX(),
							Input.getMousePosition().getY());
					GL11.glEnd();
				}
			}
		}

		if (UIThread.draggingSlot != null) {
			GL11.glBegin(GL11.GL_TRIANGLES);
			Renderer.renderModel("ITEM", UIThread.draggingSlot.item, Input.getMousePosition().x,
					Input.getMousePosition().y);
			GL11.glEnd();
		}
	}

	public static LinkedList<Object> objectsHovered = new LinkedList<Object>();

	public void renderOnMap() {
		if (EngineData.showTelematry) {
			if (uiHovered) {
				objectsHovered.clear();
			}
			if (objectsHovered != null) {
				if (objectsHovered.size() > 0) {
					int i = 0;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					for (Object obj : objectsHovered) {
						int carX = obj.getIndex().getX() * 32;
						int carY = obj.getIndex().getY() * 32;
						int isoX = carX - carY;
						int isoY = (carY + carX) / 2;
						if (i == objectsHovered.size() - 1) {
							int dist = (int) getDistance(new Point(obj.getIndex().getX(), obj.getIndex().getY()));
							if (dist < range) {
								GL11.glColor4f(0, 0, 1, 0.5f);
							} else {
								GL11.glColor4f(1, 0, 0, 0.5f);
							}
						} else {
							GL11.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
						}
						GL11.glBegin(GL11.GL_POLYGON);
						Renderer.renderModelBounds(obj.getModel(), obj.getMaterial(), isoX, isoY);
						GL11.glEnd();
						i++;
					}
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
			}
		}
	}

	public double getDistance(Point index) {
		return Math.sqrt((index.y - World.character.getIndex().getX()) * (index.y - World.character.getIndex().getY())
				+ (index.x - World.character.getIndex().getX()) * (index.x - World.character.getIndex().getY()));
	}

	public void clean() {
	}
}
