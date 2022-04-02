package threads;

import java.awt.Point;
import java.util.LinkedList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import classes.World;
import data.EngineData;
import ui.MainMenu;
import ui.UIButton;
import ui.UIControl;
import ui.UIMenu;
import ui.UIMenuItem;
import utils.APathFinder;
import utils.FPS;
import utils.Input;
import utils.Loader;
import classes.Object;
import classes.Path;
import utils.Renderer;
import utils.SettingsHandler;
import utils.Window;

public class UIThread extends BaseThread {
	MainMenu mainMenu;

	@Override
	public void setup() {
		super.setup();
		mainMenu = new MainMenu();
		mainMenu.setup();

		// EngineData.controls.put("mainMenuControl", mainMenu);

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

		// EngineData.controls.put(test.getName(), test);
	}

	@Override
	public void update() {
		super.update();

		mainMenu.update();

		if (Input.isKeyPressed(Keyboard.KEY_F1)) {
			EngineData.showTelematry = !EngineData.showTelematry;
		}
		if (Input.isKeyPressed(Keyboard.KEY_F2)) {
			SettingsHandler.load();
			Loader.load();
			World.rebuild();
			EngineData.dataLoaded = true;
		}
		if (Input.isMousePressed(0)) {
			if (objectsHovered.size() > 0) {
				Object obj = objectsHovered.getLast();
				if (obj != null) {
					System.out.println("finding path");
					path = new Path(new Point(100, 100), new Point(0,0));
					BackEndThread.findPath(path);

					// System.out.println("Path=>"+path);
				}

			}
		}
		if (Input.isMousePressed(1)) {
			showMenu("test", Input.getMousePosition());
		}

		path = BackEndThread.getPath(path);
		if (path != null) {
			if (path.path != null) {
				if (path.path.size() > 0) {
					// System.out.println("Path=>" + path.path);
				}
			}
		}
	}

	Path path;

	@Override
	public void render() {
		super.render();
		mainMenu.render();

		objectsHovered = World.getHoveredObjects();
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
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY, "Data Loaded:" + EngineData.dataLoaded, 12,
					Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY,
					"Chunks:" + EngineData.renderedChunks.size() + "/" + EngineData.chunks.size(), 12, Color.white);
			tempY += 12;
			Renderer.renderText(debugPosition.x, debugPosition.y + tempY, "Blocked Input:" + EngineData.blockInput, 12,
					Color.white);

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
		EngineData.pauseGame = EngineData.showMainMenu;

	}

	static LinkedList<Object> objectsHovered;

	public void renderOnMap() {
		if (EngineData.showTelematry) {
			if (objectsHovered != null) {
				if (objectsHovered.size() > 0) {
					int i = 0;
					for (Object obj : objectsHovered) {
						int carX = obj.getIndex().getX() * 32;
						int carY = obj.getIndex().getY() * 32;
						int isoX = carX - carY;
						int isoY = (carY + carX) / 2;
						if (i == objectsHovered.size() - 1) {
							GL11.glColor4f(1, 0, 0, 0.5f);
						} else {
							GL11.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
						}
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glBegin(GL11.GL_POLYGON);
						Renderer.renderModelBounds(obj.getModel(), obj.getMaterial(), isoX, isoY);
						GL11.glEnd();
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						i++;
					}
				}
			}
		}
	}

	@Override
	public void clean() {

		super.clean();
	}

	public static void showMenu(String string, Vector2f position) {

		UIControl temp = EngineData.controls.get(string);
		if (temp != null) {
			UIMenu tempMenu = (UIMenu) temp;
			if (tempMenu != null) {
				tempMenu.clear();
				for (Object obj : objectsHovered) {
					UIMenuItem testItem = new UIMenuItem();
					testItem.setName("Inspect:" + obj.getModel());
					testItem.setText("Inspect:" + obj.getModel());
					testItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								System.out.println("Mouse Click Left" + self.getName());
							}
							if (mouseButton == 1) {
								System.out.println("Mouse Click Right");
							}
							if (mouseButton == 2) {
								System.out.println("Mouse Click Middle");
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
					tempMenu.add(testItem);
					testItem = new UIMenuItem();
					testItem.setName("Chop:" + obj.getModel());
					testItem.setText("Chop:" + obj.getModel());
					testItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								System.out.println("Mouse Click Left" + self.getName());
							}
							if (mouseButton == 1) {
								System.out.println("Mouse Click Right");
							}
							if (mouseButton == 2) {
								System.out.println("Mouse Click Middle");
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
					tempMenu.add(testItem);
				}
			}

			temp.setPosition(new Vector2f(position.x, position.y - 10));
			temp.toggle();
			EngineData.pauseGame = true;
		}
	}

}
