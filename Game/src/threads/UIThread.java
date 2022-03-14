package threads;

import java.awt.Polygon;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Index;
import classes.MouseIndex;
import classes.Size;
import classes.UIButton;
import classes.UIControl;
import classes.UIMenu;
import classes.UIMenuItem;
import classes.World;
import data.EngineData;
import data.Settings;
import data.EngineData;
import utils.FPS;
import utils.Input;
import utils.Loader;
import classes.Object;
import utils.Renderer;
import utils.View;

public class UIThread extends BaseThread {

	@Override
	public void setup() {
		super.setup();

		UIMenu menu = new UIMenu("test");
		menu.setPosition(new Vector2f(100, 100));
		menu.setSize(new Size(0, 0));

		UIMenuItem testItem = new UIMenuItem();
		testItem.setName("Test");
		testItem.setText("Test");
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
		menu.add(testItem);
		UIMenuItem testItem1 = new UIMenuItem();
		testItem1.setName("Hello");
		testItem1.setText("Hello");
		testItem1.onEvent(new Action() {
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
		menu.add(testItem1);
		menu.toggle();
		EngineData.controls.put(menu.getName(), menu);
		/*
		 * 
		 */

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
		pollHover();
		for (UIControl control : EngineData.controls.values()) {
			control.update();
		}
		if (Input.isKeyPressed(Keyboard.KEY_F1)) {
			EngineData.showTelematry = !EngineData.showTelematry;
		}
		if (Input.isKeyPressed(Keyboard.KEY_F2)) {
			Loader.load();
			World.rebuild();
			Settings.dataLoaded = true;
		}
	}

	@Override
	public void render() {
		super.render();

		for (UIControl control : EngineData.controls.values()) {
			if (control.isVisible) {
				Renderer.renderControl(control);
			}
		}
		if (EngineData.showTelematry) {
			Renderer.renderQuad(0, 0, 200, 100, new Color(0, 0, 0, 0.5f));
			Renderer.renderText(0, 0, "FPS:" + FPS.currentfps, 12, Color.white);
			Renderer.renderText(0, 12, "Data Loaded:" + Settings.dataLoaded, 12, Color.white);
			Renderer.renderText(0, 24, "Chunks:" + EngineData.renderedChunks.size() + "/" + EngineData.chunks.size(),
					12, Color.white);

			objectsHovered = World.getHoveredObjects();

			if (objectsHovered.size() > 0) {
				Renderer.renderText(200, 0, "Hover Index:", 12, Color.white);
				int y = 12;
				for (int i = objectsHovered.size() - 1; i >= 0; i--) {
					Object obj = objectsHovered.get(i);
					if (obj != null) {
						Renderer.renderText(200, y, obj.toString(), 12, Color.white);
						y += 12;
					}
				}
			}
		}
	}

	LinkedList<Object> objectsHovered;

	public void renderOnMap() {
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
					GL11.glBegin(GL11.GL_TRIANGLES);
					Renderer.renderModel(obj.getModel(), obj.getMaterial(), isoX, isoY);
					GL11.glEnd();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					i++;
				}
			}
		}

	}

	/*
	 * public static void renderBounds(Polygon polygon, Color newColor) {
	 * 
	 * if (polygon != null) { unbindTexture();
	 * GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	 * GL11.glColor4f(newColor.r, newColor.g, newColor.b, newColor.a);
	 * 
	 * GL11.glBegin(GL11.GL_POLYGON); for (int p = 0; p < polygon.npoints; p++) {
	 * GL11.glVertex2f(polygon.xpoints[p], polygon.ypoints[p]); } GL11.glEnd();
	 * GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL); bindTexture(); } }
	 */
	@Override
	public void clean() {

		super.clean();
	}

	public static void showMenu(String string, Vector2f position) {
		UIControl temp = EngineData.controls.get(string);
		if (temp != null) {
			temp.setPosition(new Vector2f(position.x, position.y - 10));
			temp.toggle();
		}
	}

	private void pollHover() {
		int cartX = (int) Input.getMousePosition().getX() - View.x;
		int cartY = (int) Input.getMousePosition().getY() - View.y;
		int isoX = cartX / 2 + (cartY);
		int isoY = cartY - cartX / 2;
		int indexX = (int) Math.floor((float) isoX / (float) 32);
		int indexY = (int) Math.floor((float) isoY / (float) 32);

		int chunkX = (int) Math.floor(indexX / 16);
		int chunkY = (int) Math.floor(indexY / 16);
		if (indexX < 0) {
			chunkX--;
		}
		if (indexY < 0) {
			chunkY--;
		}

		// EngineData.hover = new MouseIndex(indexX, indexY, chunkX, chunkY);
	}
}
