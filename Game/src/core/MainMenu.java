package core;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import classes.*;
import utils.Renderer;

public class MainMenu {
	public boolean showMenu = false;
	ListView menu;
	int subMenu = -1;
	int settingsTab = 0;
	private boolean waitingForInput = false;
	private String configName = "";

	MenuItem backBtn;
	MenuItem controlsBtn;
	MenuItem graphicsBtn;
	MenuItem audioBtn;
	LinkedList<MenuItem> controlInputs = new LinkedList<MenuItem>();

	LinkedList<MenuSlider> audioSliders = new LinkedList<MenuSlider>();
	MenuItem forwardBtn;

	public void menuVisible(boolean visible) {
		showMenu = visible;
		GameData.mainMenuShown = visible;
	}

	public void init() {
		menu = new ListView();

		MenuItem resume = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				menuVisible(false);

			}
		});
		resume.setName("Resume");
		menu.addItem(resume);

		MenuItem settings = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				System.out.println(item.getName());
				subMenu = 0;
			}
		});
		settings.setName("Settings");
		menu.addItem(settings);

		MenuItem help = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				System.out.println(item.getName());
				subMenu = 1;
			}
		});
		help.setName("Help");
		menu.addItem(help);

		MenuItem save = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				System.out.println("Save");
			}
		});
		save.setName("Save");
		menu.addItem(save);
		MenuItem exit = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				menuVisible(false);
			}
		});
		exit.setName("Exit");
		menu.addItem(exit);

		backBtn = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				subMenu = -1;
			}
		});
		backBtn.bounds = new Rectangle((Window.getWidth() / 2) - 200, (Window.getHeight() / 2) - 200, 60, 24);
		backBtn.setName("Exit");

		controlsBtn = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				settingsTab = 0;
				item.color = new Color(0.5f, 1, 0.5f, 0.5f);
			}

			public void onMouseHover(MenuItem item) {
				item.color = new Color(1f, 0.5f, 0.5f, 0.5f);

			}

			public void onMouseOut(MenuItem item) {
				if (settingsTab == 0) {
					item.color = new Color(0.5f, 1, 0.5f, 0.5f);
				} else {
					item.color = new Color(0.5f, 1, 0.5f, 0f);
				}
			}
		});
		controlsBtn.bounds = new Rectangle(backBtn.bounds.x + 65, backBtn.bounds.y, 100, 24);
		controlsBtn.setName("Controls");

		graphicsBtn = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				settingsTab = 1;
				item.color = new Color(0.5f, 1, 0.5f, 0.5f);
			}

			public void onMouseHover(MenuItem item) {
				item.color = new Color(1f, 0.5f, 0.5f, 0.5f);

			}

			public void onMouseOut(MenuItem item) {
				if (settingsTab == 1) {
					item.color = new Color(0.5f, 1, 0.5f, 0.5f);
				} else {
					item.color = new Color(0.5f, 1, 0.5f, 0f);
				}
			}
		});
		graphicsBtn.bounds = new Rectangle(backBtn.bounds.x + 175, backBtn.bounds.y, 100, 24);
		graphicsBtn.setName("Graphics");

		audioBtn = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				settingsTab = 2;
				item.color = new Color(0.5f, 1, 0.5f, 0.5f);
			}

			public void onMouseHover(MenuItem item) {
				item.color = new Color(1f, 0.5f, 0.5f, 0.5f);
			}

			public void onMouseOut(MenuItem item) {
				if (settingsTab == 2) {
					item.color = new Color(0.5f, 1, 0.5f, 0.5f);
				} else {
					item.color = new Color(0.5f, 1, 0.5f, 0f);
				}
			}
		});
		audioBtn.bounds = new Rectangle(backBtn.bounds.x + 285, backBtn.bounds.y, 100, 24);
		audioBtn.setName("Audio");

		forwardBtn = new MenuItem(new AFunction() {
			public void onClick(MenuItem item) {
				waitingForInput = true;
				configName = item.getName();
			}
		});
		forwardBtn.bounds = new Rectangle(backBtn.bounds.x + 150, backBtn.bounds.y + 30, 60, 24);
		forwardBtn.setName("control.forward");
		Set<String> keys = GameData.config.stringPropertyNames();
		List<String> keyList = new ArrayList<String>(keys);
		Collections.sort(keyList);

		int y = 0;
		int x = 0;
		for (String key : keyList) {
			if (key.startsWith("control.")) {
				MenuItem testBtn = new MenuItem(new AFunction() {
					public void onClick(MenuItem item) {
						waitingForInput = true;
						configName = item.getName();
					}
				});
				testBtn.bounds = new Rectangle(backBtn.bounds.x + 90 + (x * 200), backBtn.bounds.y + 28 + (y * 24), 100,
						24);
				testBtn.setName(key);

				controlInputs.add(testBtn);
				if (x >= 1) {
					x = 0;
					y++;
				} else {
					x++;
				}
			}
		}

		slider = new MenuSlider();
		slider.name = "Master";
		slider.bounds = new Rectangle(backBtn.bounds.x + 150, backBtn.bounds.y + 30, 235, 16);
		audioSliders.add(slider);

		slider = new MenuSlider();
		slider.name = "Sound Effects";
		slider.bounds = new Rectangle(backBtn.bounds.x + 150, backBtn.bounds.y + 48, 235, 16);
		audioSliders.add(slider);

		slider = new MenuSlider();
		slider.name = "Music";
		slider.bounds = new Rectangle(backBtn.bounds.x + 150, backBtn.bounds.y + 66, 235, 16);
		audioSliders.add(slider);
	}

	boolean firstInput = true;

	MenuSlider slider;

	public void update() {
		if (waitingForInput) {
			if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {
				waitingForInput = false;
				firstInput = true;
			} else {
				if (firstInput) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					firstInput = false;
				}
				int key = Input.getInput();
				System.out.println("Mouse: " + key + "/" + ((-key) - 1));
				if (key != -99) {
					String keyName = "";
					if (key < 0) {
						System.out.println("Mouse: " + ((-key) - 1));
						keyName = Mouse.getButtonName((-key) - 1);
					} else {
						keyName = Keyboard.getKeyName(key);
					}
					GameData.config.setProperty(configName, keyName);
					waitingForInput = false;
					firstInput = true;
				}
			}

		} else {
			if (showMenu) {
				boolean isActive = true;
				if (subMenu != -1) {
					backBtn.bounds.x = (Window.getWidth() / 2) - 200;
					backBtn.bounds.y = (Window.getHeight() / 2) - 200;
					backBtn.update();
					switch (subMenu) {
					case 0:
						controlsBtn.update();
						graphicsBtn.update();
						audioBtn.update();
						switch (settingsTab) {
						case 0:
							forwardBtn.update();
							for (MenuItem btn : controlInputs) {
								btn.update();
							}
							break;
						case 1:

							break;
						case 2:
							for (MenuSlider slider : audioSliders) {
								slider.update();
							}
							break;
						default:
							break;
						}
						break;
					case 1:

						break;

					default:
						break;
					}
					isActive = false;
				}
				menu.update(isActive);
			}
		}
	}

	public void render() {
		if (showMenu) {
			Renderer.beginDraw(GL11.GL_QUADS);
			Renderer.drawQuad(0, 0, Window.getWidth(), Window.getHeight(), new Color(0, 0, 0, 0.75f));
			Renderer.endDraw();
			menu.render();

			if (subMenu != -1) {
				Renderer.beginDraw(GL11.GL_QUADS);
				Renderer.drawQuad(backBtn.bounds.x, backBtn.bounds.y, 400, 400, new Color(0, 0, 0, 1f));
				Renderer.endDraw();

				Color backBackgroundColor = new Color(1, 1, 1, 0.5f);
				if (backBtn.hovered) {
					backBackgroundColor = new Color(1, 0, 0, 0.5f);
				}
				Renderer.beginDraw(GL11.GL_QUADS);
				Renderer.drawQuad(backBtn.bounds.x, backBtn.bounds.y, backBtn.bounds.width, backBtn.bounds.height,
						backBackgroundColor);
				Renderer.endDraw();
				Renderer.drawText(backBtn.bounds.x, backBtn.bounds.y, "Back", 24, Color.white);
				switch (subMenu) {
				case 0:
					Renderer.drawText(backBtn.bounds.x + 70, backBtn.bounds.y, "Controls", 24, Color.white);
					Renderer.drawText(backBtn.bounds.x + 180, backBtn.bounds.y, "Graphics", 24, Color.white);
					Renderer.drawText(backBtn.bounds.x + 300, backBtn.bounds.y, "Audio", 24, Color.white);

					Renderer.beginDraw(GL11.GL_QUADS);
					Renderer.drawQuad(controlsBtn.bounds.x, controlsBtn.bounds.y, controlsBtn.bounds.width,
							controlsBtn.bounds.height, controlsBtn.color);
					Renderer.endDraw();

					Renderer.beginDraw(GL11.GL_QUADS);
					Renderer.drawQuad(graphicsBtn.bounds.x, graphicsBtn.bounds.y, graphicsBtn.bounds.width,
							graphicsBtn.bounds.height, graphicsBtn.color);
					Renderer.endDraw();

					Renderer.beginDraw(GL11.GL_QUADS);
					Renderer.drawQuad(audioBtn.bounds.x, audioBtn.bounds.y, audioBtn.bounds.width,
							audioBtn.bounds.height, audioBtn.color);
					Renderer.endDraw();

					switch (settingsTab) {
					case 0:

						/*
						 * Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 28, "Forward", 18,
						 * Color.white); Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 46,
						 * "Backward", 18, Color.white); Renderer.drawText(backBtn.bounds.x + 5,
						 * backBtn.bounds.y + 64, "Straft Left", 18, Color.white);
						 * Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 82,
						 * "Straft Right", 18, Color.white); Renderer.drawText(backBtn.bounds.x + 5,
						 * backBtn.bounds.y + 100, "Jump", 18, Color.white);
						 * Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 118, "Sneak", 18,
						 * Color.white);
						 * 
						 * Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 164, "Action", 18,
						 * Color.white); Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 182,
						 * "Secondary Action", 18, Color.white);
						 * 
						 * Renderer.drawText(backBtn.bounds.x + 220, backBtn.bounds.y + 28, "Inventory",
						 * 18, Color.white); Renderer.drawText(backBtn.bounds.x + 220, backBtn.bounds.y
						 * + 46, "Character", 18, Color.white); Renderer.drawText(backBtn.bounds.x +
						 * 220, backBtn.bounds.y + 64, "Skills", 18, Color.white);
						 * Renderer.drawText(backBtn.bounds.x + 220, backBtn.bounds.y + 82, "Spellbook",
						 * 18, Color.white); Renderer.drawText(backBtn.bounds.x + 220, backBtn.bounds.y
						 * + 100, "Quests", 18, Color.white); Renderer.drawText(backBtn.bounds.x + 220,
						 * backBtn.bounds.y + 118, "Journal", 18, Color.white);
						 * 
						 */
						/*
						 * Renderer.drawText(backBtn.bounds.x + 155, backBtn.bounds.y + 28,
						 * GameData.config.getProperty("control.forward"), 18, Color.white);
						 */

						for (MenuItem btn : controlInputs) {

							Renderer.drawText(btn.bounds.x - 85, btn.bounds.y, btn.getName().replaceAll("control.", ""),
									18, Color.white);

							String key = GameData.config.getProperty(btn.getName());
							if (waitingForInput && this.configName.equals(btn.getName())) {
								key = "_";
							}

							Renderer.drawText(btn.bounds.x, btn.bounds.y, key, 18, Color.white);
						}
						Renderer.beginDraw(GL11.GL_QUADS);

						for (MenuItem btn : controlInputs) {
							Renderer.drawQuad(btn.bounds.x - 5, btn.bounds.y, btn.bounds.width, btn.bounds.height,
									new Color(1, 1, 1, 0.5f));
						}
						Renderer.endDraw();
						/*
						 * Renderer.beginDraw(GL11.GL_QUADS); Renderer.drawQuad(backBtn.bounds.x + 150,
						 * backBtn.bounds.y + 30, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 48, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 150,
						 * backBtn.bounds.y + 66, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 84, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 150,
						 * backBtn.bounds.y + 102, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 120, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 150,
						 * backBtn.bounds.y + 166, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 184, 60, 16, new
						 * Color(1, 1, 1, 0.5f));
						 * 
						 * Renderer.drawQuad(backBtn.bounds.x + 335, backBtn.bounds.y + 30, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 335,
						 * backBtn.bounds.y + 48, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 335, backBtn.bounds.y + 66, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 335,
						 * backBtn.bounds.y + 84, 60, 16, new Color(1, 1, 1, 0.5f));
						 * Renderer.drawQuad(backBtn.bounds.x + 335, backBtn.bounds.y + 102, 60, 16, new
						 * Color(1, 1, 1, 0.5f)); Renderer.drawQuad(backBtn.bounds.x + 335,
						 * backBtn.bounds.y + 120, 60, 16, new Color(1, 1, 1, 0.5f));
						 * 
						 * Renderer.endDraw();
						 */
						break;
					case 1:
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 28, "Fullscreen", 18, Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 46, "Resolution	", 18, Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 64, "VSync", 18, Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 82, "Target FPS", 18, Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 100, "Quailty", 18, Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 118, "Lost Focus VSync", 18,
								Color.white);
						Renderer.drawText(backBtn.bounds.x + 5, backBtn.bounds.y + 136, "Lost Focus FPS", 18,
								Color.white);
						Renderer.beginDraw(GL11.GL_QUADS);
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 30, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 48, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 66, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 84, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 102, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 120, 60, 16,
								new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(backBtn.bounds.x + 150, backBtn.bounds.y + 138, 60, 16,
								new Color(1, 1, 1, 0.5f));

						Renderer.endDraw();
						break;
					case 2:

						for (MenuSlider slider : audioSliders) {
							Renderer.drawText(slider.bounds.x - 145, slider.bounds.y - 4, slider.name, 18, Color.white);
						}

						Renderer.beginDraw(GL11.GL_QUADS);
						for (MenuSlider slider : audioSliders) {
							Renderer.drawQuad(slider.bounds.x, slider.bounds.y, slider.bounds.width,
									slider.bounds.height, new Color(0.5f, 0.5f, 0.5f, 0.5f));

							Renderer.drawQuad(slider.bounds.x, slider.bounds.y,
									(int) (((float) slider.value / (float) slider.maxValue)
											* (float) slider.bounds.width),
									slider.bounds.height, new Color(1, 1, 1, 0.5f));
						}
						Renderer.endDraw();
						break;
					default:
						break;
					}

					break;
				case 1:

					break;
				default:
					break;
				}
			}
		}
	}

	public void destroy() {

	}

	public void show() {
		showMenu = !showMenu;
		subMenu = -1;
		settingsTab = 0;
		GameData.mainMenuShown = showMenu;
	}
}
