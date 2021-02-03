package core;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	private boolean waitingForKey = false;
	private boolean waitingForInput = false;
	private String configName = "";
	public static Point menuPosition;

	MenuItem backBtn;
	MenuItem controlsBtn;
	MenuItem graphicsBtn;
	MenuItem audioBtn;
	LinkedList<MenuItem> controlInputs = new LinkedList<MenuItem>();
	LinkedList<MenuSlider> audioSliders = new LinkedList<MenuSlider>();
	MenuDropDown fullscreenDD;
	MenuDropDown resolutionDD;
	MenuDropDown vsyncDD;
	MenuInput vsync;
	MenuDropDown qualityDD;
	MenuInput lostFocusFPS;
	MenuInput menuFPS;
	int inputID = -1;
	String inputValue = "";

	public void menuVisible(boolean visible) {
		showMenu = visible;
		GameData.mainMenuShown = visible;
	}

	public void init() {
		menuPosition = new Point((Window.getWidth() / 2) - 200, (Window.getHeight() / 2) - 200);

		menu = new ListView();

		MenuItem resume = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				menuVisible(false);

			}
		});
		resume.setName("Resume");
		menu.addItem(resume);

		MenuItem settings = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				System.out.println(item.getName());
				subMenu = 0;
			}
		});
		settings.setName("Settings");
		menu.addItem(settings);

		MenuItem help = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				System.out.println(item.getName());
				subMenu = 1;
			}
		});
		help.setName("Help");
		menu.addItem(help);

		MenuItem save = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				System.out.println("Save");
			}
		});
		save.setName("Save");
		menu.addItem(save);
		MenuItem exit = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				menuVisible(false);
			}
		});
		exit.setName("Exit");
		menu.addItem(exit);

		backBtn = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				subMenu = -1;
			}
		});
		backBtn.bounds = new Rectangle(0, 0, 60, 24);
		backBtn.setName("Exit");

		controlsBtn = new MenuItem(new AFunction<MenuItem>() {
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
		controlsBtn.bounds = new Rectangle(65, 0, 100, 24);
		controlsBtn.setName("Controls");

		graphicsBtn = new MenuItem(new AFunction<MenuItem>() {
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
		graphicsBtn.bounds = new Rectangle(175, 0, 100, 24);
		graphicsBtn.setName("Graphics");

		audioBtn = new MenuItem(new AFunction<MenuItem>() {
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
		audioBtn.bounds = new Rectangle(285, 0, 100, 24);
		audioBtn.setName("Audio");

		Set<String> keys = GameData.config.stringPropertyNames();
		List<String> keyList = new ArrayList<String>(keys);
		Collections.sort(keyList);

		int y = 0;
		int x = 0;
		for (String key : keyList) {
			if (key.startsWith("control.")) {
				MenuItem testBtn = new MenuItem(new AFunction<MenuItem>() {
					public void onClick(MenuItem item) {
						waitingForKey = true;
						configName = item.getName();
					}
				});
				testBtn.bounds = new Rectangle(90 + (x * 200), 28 + (y * 24), 100, 24);
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
		slider.bounds = new Rectangle(150, 30, 235, 16);
		audioSliders.add(slider);

		slider = new MenuSlider();
		slider.name = "Sound Effects";
		slider.bounds = new Rectangle(150, 48, 235, 16);
		audioSliders.add(slider);

		slider = new MenuSlider();
		slider.name = "Music";
		slider.bounds = new Rectangle(150, 66, 235, 16);
		audioSliders.add(slider);

		fullscreenDD = new MenuDropDown();
		fullscreenDD.bounds = new Rectangle(150, 30, 60, 16);
		MenuItem yes = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				GameData.config.setProperty("window.fullscreen", "true");
				fullscreenDD.showDropDown = false;
				System.out.println("yes fullscreen");
			}
		});
		yes.setName("Yes");

		fullscreenDD.addItem(yes);
		MenuItem no = new MenuItem(new AFunction<MenuItem>() {
			public void onClick(MenuItem item) {
				GameData.config.setProperty("window.fullscreen", "false");
				fullscreenDD.showDropDown = false;
				System.out.println("no fullscreen");
			}
		});
		no.setName("no");
		fullscreenDD.addItem(no);

		resolutionDD = new MenuDropDown();
		resolutionDD.bounds = new Rectangle(150, 48, 100, 16);

		ArrayList<Point> resolutions = Window.getResolutions();
		System.out.println("res: " + resolutions.size());
		for (Point res : resolutions) {
			MenuItem resItem = new MenuItem(new AFunction<MenuItem>() {
				public void onClick(MenuItem item) {
					String[] data = item.getName().split("x");
					int width = Integer.parseInt(data[0]);
					int height = Integer.parseInt(data[1]);
					GameData.config.setProperty("window.width", width + "");
					GameData.config.setProperty("window.height", height + "");
					Window.updateWindow();
					resolutionDD.showDropDown = false;
					System.out.println("select Res: " + width + "x" + height);
				}
			});

			resItem.setName(res.x + "x" + res.y);
			resolutionDD.addItem(resItem);
		}
		vsync = new MenuInput(new AFunction<MenuInput>() {
			public void onClick(MenuInput item) {
				inputID = 0;
				waitingForInput = true;
				System.out.println("test");
			}
		});
		vsync.bounds = new Rectangle(0, 0, 60, 16);
		vsync.value = GameData.config.getProperty("window.vsync");
		lostFocusFPS = new MenuInput(new AFunction<MenuInput>() {
			public void onClick(MenuInput item) {
				inputID = 1;
				waitingForInput = true;
			}
		});
		lostFocusFPS.bounds = new Rectangle(0, 0, 60, 16);
		lostFocusFPS.value = GameData.config.getProperty("window.lose_focus");
		menuFPS = new MenuInput(new AFunction<MenuInput>() {
			public void onClick(MenuInput item) {
				inputID = 2;
				waitingForInput = true;

			}
		});
		menuFPS.bounds = new Rectangle(0, 0, 60, 16);
		menuFPS.value = GameData.config.getProperty("window.menu_vsync");

		updateBounds();
	}

	public void updateBounds() {
		backBtn.bounds.x = menuPosition.x;
		backBtn.bounds.y = menuPosition.y;

		controlsBtn.bounds.x = menuPosition.x + 65;
		controlsBtn.bounds.y = menuPosition.y;

		graphicsBtn.bounds.x = menuPosition.x + 175;
		graphicsBtn.bounds.y = menuPosition.y;

		audioBtn.bounds.x = menuPosition.x + 285;
		audioBtn.bounds.y = menuPosition.y;

		slider.bounds.x = menuPosition.x + 150;
		slider.bounds.y = menuPosition.y + 30;

		fullscreenDD.bounds.x = menuPosition.x + 150;
		fullscreenDD.bounds.y = menuPosition.y + 30;

		resolutionDD.bounds.x = menuPosition.x + 150;
		resolutionDD.bounds.y = menuPosition.y + 48;

		vsync.bounds.x = menuPosition.x + 150;
		vsync.bounds.y = menuPosition.y + 66;

		lostFocusFPS.bounds.x = menuPosition.x + 150;
		lostFocusFPS.bounds.y = menuPosition.y + 120;

		menuFPS.bounds.x = menuPosition.x + 150;
		menuFPS.bounds.y = menuPosition.y + 138;
	}

	boolean firstInput = true;

	MenuSlider slider;

	public void update() {
		if (waitingForKey) {
			if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {
				waitingForKey = false;
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
					waitingForKey = false;
					firstInput = true;
				}
			}

		} else if (waitingForInput) {
			// System.out.println("test:" + inputID);
			if (Input.isKeyDown(Keyboard.KEY_ESCAPE) || Input.isKeyDown(Keyboard.KEY_RETURN)) {
				if (inputID == 0) {
					vsync.value = inputValue;
				}
				if (inputID == 1) {
					lostFocusFPS.value = inputValue;
				}
				if (inputID == 2) {
					menuFPS.value = inputValue;
				}
				inputValue = "";
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

					inputValue = "";
					firstInput = false;
				}
				int key = Input.getInput();
				// System.out.println("value: " + inputValue);
				if (key >= 0) {
					String keyName = Keyboard.getKeyName(key);
					// System.out.println("Key: " + keyName);
					if (keyName.length() == 1) {
						inputValue += keyName;
					} else if (keyName.equals("BACK") && inputValue.length() > 0) {
						inputValue = inputValue.substring(0, inputValue.length() - 1);

					}
					if (inputID == 0) {
						vsync.value = inputValue + "_";
					}
					if (inputID == 1) {
						lostFocusFPS.value = inputValue + "_";
					}
					if (inputID == 2) {
						menuFPS.value = inputValue + "_";
					}
				}

			}

		} else {
			if (showMenu) {
				boolean isActive = true;
				if (subMenu != -1) {

					menuPosition.x = (Window.getWidth() / 2) - 200;
					menuPosition.y = (Window.getHeight() / 2) - 200;
					updateBounds();
					backBtn.update();
					switch (subMenu) {
					case 0:
						controlsBtn.update();
						graphicsBtn.update();
						audioBtn.update();
						switch (settingsTab) {
						case 0:
							int x = 0;
							int y = 0;
							for (MenuItem btn : controlInputs) {
								btn.bounds.x = menuPosition.x + 90 + (x * 200);
								btn.bounds.y = menuPosition.y + 28 + (y * 24);
								btn.update();
								if (x >= 1) {
									x = 0;
									y++;
								} else {
									x++;
								}
							}
							break;
						case 1:
							fullscreenDD.update();
							resolutionDD.update();
							vsync.update();
							lostFocusFPS.update();
							menuFPS.update();

							break;
						case 2:
							int y2 = 0;
							for (MenuSlider slider : audioSliders) {
								slider.bounds.x = menuPosition.x + 150;
								slider.bounds.y = menuPosition.y + 30 + (y2 * 18);
								slider.update();
								y2++;
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
				Renderer.drawQuad(menuPosition.x, menuPosition.y, 400, 400, new Color(0, 0, 0, 1f));
				Renderer.endDraw();
				Color backBackgroundColor = new Color(1, 1, 1, 0.5f);
				if (backBtn.hovered) {
					backBackgroundColor = new Color(1, 0, 0, 0.5f);
				}
				Renderer.beginDraw(GL11.GL_QUADS);
				Renderer.drawQuad(backBtn.bounds.x, backBtn.bounds.y, backBtn.bounds.width, backBtn.bounds.height,
						backBackgroundColor);
				Renderer.endDraw();
				Renderer.drawText(menuPosition.x, menuPosition.y, "Back", 24, Color.white);
				switch (subMenu) {
				case 0:
					Renderer.drawText(menuPosition.x + 70, menuPosition.y, "Controls", 24, Color.white);
					Renderer.drawText(menuPosition.x + 180, menuPosition.y, "Graphics", 24, Color.white);
					Renderer.drawText(menuPosition.x + 300, menuPosition.y, "Audio", 24, Color.white);

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
						 * Renderer.drawText(menuPosition.x + 5, menuPosition.y + 28, "Forward", 18,
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
							if (waitingForKey && this.configName.equals(btn.getName())) {
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

						break;
					case 1:
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 28, "Fullscreen", 18, Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 46, "Resolution	", 18, Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 64, "VSync", 18, Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 82, "Target FPS", 18, Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 100, "Quailty", 18, Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 118, "Lost Focus VSync", 18,
								Color.white);
						Renderer.drawText(menuPosition.x + 5, menuPosition.y + 136, "Menu FPS", 18, Color.white);

						Renderer.drawText(vsync.bounds.x + 2, vsync.bounds.y - 2, vsync.value, 18, Color.white);

						Renderer.drawText(lostFocusFPS.bounds.x + 2, lostFocusFPS.bounds.y - 2, lostFocusFPS.value, 18,
								Color.white);

						Renderer.drawText(menuFPS.bounds.x + 2, menuFPS.bounds.y - 2, menuFPS.value, 18, Color.white);

						Renderer.drawText(menuPosition.x + 155, menuPosition.y + 28,
								GameData.config.getProperty("window.fullscreen"), 18, Color.white);

						Renderer.drawText(menuPosition.x + 155, menuPosition.y + 46,
								GameData.config.getProperty("window.width") + "x"
										+ GameData.config.getProperty("window.height"),
								18, Color.white);

						Renderer.beginDraw(GL11.GL_QUADS);
						if (fullscreenDD.showDropDown) {
							Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 30, 60,
									16 + ((fullscreenDD.getItems().size() - 1) * 18),
									new Color(0.25f, 0.25f, 0.25f, 1f));
						}
						Renderer.endDraw();
						if (fullscreenDD.showDropDown) {
							int y = 0;
							for (MenuItem item : fullscreenDD.getItems()) {
								// if (item.bounds == null) {
								item.bounds = new Rectangle(fullscreenDD.bounds.x, fullscreenDD.bounds.y + (y * 18),
										fullscreenDD.bounds.width, fullscreenDD.bounds.height);
								// }
								Renderer.drawText(item.bounds.x + 5, item.bounds.y, item.getName(), 18, Color.white);
								y++;
							}
						}

						if (resolutionDD.showDropDown) {
							Renderer.drawQuad(resolutionDD.bounds.x, resolutionDD.bounds.y, 100,
									16 + ((resolutionDD.getItems().size() - 1) * 18),
									new Color(0.25f, 0.25f, 0.25f, 1f));
						}
						Renderer.endDraw();
						if (resolutionDD.showDropDown) {
							int y = 0;
							for (MenuItem item : resolutionDD.getItems()) {
								// if (item.bounds == null) {
								item.bounds = new Rectangle(resolutionDD.bounds.x, resolutionDD.bounds.y + (y * 18),
										resolutionDD.bounds.width, resolutionDD.bounds.height);
								// }
								Renderer.drawText(item.bounds.x + 5, item.bounds.y, item.getName(), 18, Color.white);
								y++;
							}
						}
						Renderer.beginDraw(GL11.GL_QUADS);
						if (fullscreenDD.showDropDown) {
							for (MenuItem item : fullscreenDD.getItems()) {
								if (item.hovered) {
									Renderer.drawQuad(item.bounds.x, item.bounds.y, fullscreenDD.bounds.width,
											fullscreenDD.bounds.height, new Color(1, 0, 0, 0.5f));
								}
							}
						}
						if (resolutionDD.showDropDown) {
							for (MenuItem item : resolutionDD.getItems()) {
								if (item.hovered) {
									System.out.println("width: " + item.bounds.x);
									Renderer.drawQuad(item.bounds.x, item.bounds.y, resolutionDD.bounds.width,
											resolutionDD.bounds.height, new Color(1, 0, 0, 0.5f));
								}
							}
						}
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 30, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 48, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 66, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 84, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 102, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 120, 60, 16, new Color(1, 1, 1, 0.5f));
						Renderer.drawQuad(menuPosition.x + 150, menuPosition.y + 138, 60, 16, new Color(1, 1, 1, 0.5f));

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
