package ui;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import classes.Action;
import classes.Size;
import data.EngineData;
import threads.BaseThread;
import threads.UIThread;
import utils.Input;
import utils.Renderer;
import utils.Window;

public class MainMenu extends UIControl {
	UIMenu mainMenu;

	public void setup() {
		mainMenu = new UIMenu("mainMenu");
		mainMenu.setPosition(new Vector2f(0, 100));
		mainMenu.setSize(new Size(100, 0));
		mainMenu.setBackgroundColor(new Color(0, 0, 0, 0.5f));

		UIMenuItem resumeItem = new UIMenuItem();
		resumeItem.setName("Resume");
		resumeItem.setText("Resume");
		resumeItem.setFontSize(32);
		resumeItem.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					EngineData.showMainMenu = false;
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
		mainMenu.add(resumeItem);
		UIMenuItem saveItem = new UIMenuItem();
		saveItem.setName("Save");
		saveItem.setText("Save");
		saveItem.setFontSize(32);
		saveItem.onEvent(new Action() {
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
		mainMenu.add(saveItem);
		UIMenuItem loadItem = new UIMenuItem();
		loadItem.setName("Load");
		loadItem.setText("Load");
		loadItem.setFontSize(32);
		loadItem.onEvent(new Action() {
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
		mainMenu.add(loadItem);
		UIMenuItem settingsItem = new UIMenuItem();
		settingsItem.setName("Settings");
		settingsItem.setText("Settings");
		settingsItem.setFontSize(32);
		settingsItem.onEvent(new Action() {
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
		mainMenu.add(settingsItem);
		UIMenuItem exitItem = new UIMenuItem();
		exitItem.setName("Exit");
		exitItem.setText("Exit");
		exitItem.setFontSize(32);
		exitItem.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					System.out.println("Mouse Click Left" + self.getName());
					BaseThread.close();
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
		mainMenu.add(exitItem);
	}

	@Override
	public void update() {
		mainMenu.update();
		if (Input.isKeyPressed(Keyboard.KEY_F3)) {
			Window.takeScreenShot();
		}
		if (Input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			EngineData.showMainMenu = !EngineData.showMainMenu;
		}
		this.isVisible = EngineData.showMainMenu;

	}

	@Override
	public void render() {
		if (this.isVisible) {
			GL11.glBegin(GL11.GL_POLYGON);
			Renderer.renderQuad(0, 0, Window.getWidth(), Window.getHeight(), new Color(0, 0, 0, 1f));
			GL11.glEnd();

			mainMenu.render();

		}
	}
}
