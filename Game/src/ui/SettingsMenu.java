package ui;

import java.awt.Point;
import java.util.Comparator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import classes.World;
import data.EngineData;
import threads.UIThread;
import utils.FPS;
import utils.Loader;
import utils.Renderer;
import utils.Window;

public class SettingsMenu extends UIControl {

	boolean graphicsChanged = false;
	UIButton applyBtn;

	UIButton controlsBtn;
	UIButton graphicsBtn;
	UIButton audioBtn;

	UISlider masterVolumeSlider;
	UISlider musicVolumeSlider;
	UISlider soundEffectsVolumeSlider;

	UIToggle fullscreenToggle;
	UIDropDown resolutionDropdown;

	UIToggle vsyncToggle;

	int panelActive = 0;

	public void setup() {
		this.isVisible = false;
		setPosition(new Vector2f(200, 100));
		setSize(new Size(454, 400));

		applyBtn = new UIButton();
		applyBtn.setText("Apply", 20, Color.white);
		applyBtn.setBackgroundColor(new Color(0, 0, 0.5f, 0.9f));
		applyBtn.setPosition(
				new Vector2f(this.getPosition().getX(), this.getPosition().getY() + this.getSize().getHeight()));
		applyBtn.setSize(new Size(150, 32));
		applyBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					graphicsChanged = false;
					UIThread.reloadGame();
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
				}
			}
		});
		fullscreenToggle = new UIToggle();
		fullscreenToggle.setName("Fullscreen");
		fullscreenToggle.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 20));
		fullscreenToggle.setSize(new Size(16, 16));
		fullscreenToggle.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIToggle toggle = (UIToggle) self;
				if (toggle != null) {
					toggle.setToggle(!toggle.getToggle());

					EngineData.isFullscreen = toggle.getToggle();
					// UIThread.reloadGame();
					graphicsChanged = true;
				}
			}
		});

		resolutionDropdown = new UIDropDown();
		resolutionDropdown.setName("Resolution");
		resolutionDropdown.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 50));
		resolutionDropdown.setSize(new Size(120, 16));
		resolutionDropdown.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					UIDropDown dropdown = (UIDropDown) self;
					if (dropdown != null) {
						dropdown.setShown(true);
					}
				}
			}

			@Override
			public void onSelect(UIControl self, int index) {
				UIDropDown dropdown = (UIDropDown) self;
				if (dropdown != null) {
					String temp = dropdown.getValue(index);
					if (temp.toLowerCase().equals("auto")) {
						EngineData.isResizable = true;
					} else {
						EngineData.isResizable = false;
					}
					String[] data = temp.split(" x ");
					if (data.length >= 2) {
						EngineData.width = Integer.parseInt(data[0]);
						EngineData.height = Integer.parseInt(data[1]);
					}
					dropdown.setValue(temp);
					dropdown.setShown(false);
					graphicsChanged = true;
				}
			}
		});
		resolutionDropdown.addValue("Auto");
		LinkedList<Point> dms = Window.getDisplayModes();
		dms.sort(new Comparator<Point>() {
			public int compare(Point x1, Point x2) {
				if (x1.getX() < x2.getX() || x1.getY() < x2.getY()) {
					return 1;
				}
				return -1;
			}
		});

		for (Point dm : dms) {
			resolutionDropdown.addValue((int) dm.getX() + " x " + (int) dm.getY());
		}

		vsyncToggle = new UIToggle();
		vsyncToggle.setName("Vsync");
		vsyncToggle.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 80));
		vsyncToggle.setSize(new Size(16, 16));
		vsyncToggle.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIToggle toggle = (UIToggle) self;
				if (toggle != null) {
					toggle.setToggle(!toggle.getToggle());
					EngineData.isVsync = toggle.getToggle();
					graphicsChanged = true;
				}
			}
		});

		masterVolumeSlider = new UISlider();
		masterVolumeSlider.setName("Master Volume");
		masterVolumeSlider.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 20));
		masterVolumeSlider.setSize(new Size(150, 16));
		masterVolumeSlider.onEvent(new Action() {

			@Override
			public void onMouseDrag(UIControl self, Point mouseOffset) {
				UISlider slider = (UISlider) self;
				if (slider != null) {
					float newValue = (float) mouseOffset.getX() / ((float) self.getSize().getWidth() - 8);
					slider.setValue((int) (newValue * slider.getMaxValue()));
				}
			}
		});

		musicVolumeSlider = new UISlider();
		musicVolumeSlider.setName("Music Volume");
		musicVolumeSlider.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 50));
		musicVolumeSlider.setSize(new Size(150, 16));
		musicVolumeSlider.onEvent(new Action() {
			@Override
			public void onMouseDrag(UIControl self, Point mouseOffset) {
				UISlider slider = (UISlider) self;
				if (slider != null) {
					float newValue = (float) mouseOffset.getX() / ((float) self.getSize().getWidth() - 8);
					slider.setValue((int) (newValue * slider.getMaxValue()));
				}
			}
		});

		soundEffectsVolumeSlider = new UISlider();
		soundEffectsVolumeSlider.setName("Sound Effects");
		soundEffectsVolumeSlider
				.setPosition(new Vector2f(this.getPosition().getX() + 170, this.getPosition().getY() + 80));
		soundEffectsVolumeSlider.setSize(new Size(150, 16));
		soundEffectsVolumeSlider.onEvent(new Action() {
			@Override
			public void onMouseDrag(UIControl self, Point mouseOffset) {
				UISlider slider = (UISlider) self;
				if (slider != null) {
					float newValue = (float) mouseOffset.getX() / ((float) self.getSize().getWidth() - 8);
					slider.setValue((int) (newValue * slider.getMaxValue()));
				}
			}
		});

		controlsBtn = new UIButton();
		controlsBtn.setText("Controls", 20, Color.white);
		controlsBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		controlsBtn.setPosition(new Vector2f(this.getPosition().getX(), this.getPosition().getY() - 32));
		controlsBtn.setSize(new Size(150, 32));
		controlsBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 0;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) audioBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) graphicsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 0) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 0) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
		graphicsBtn = new UIButton();
		graphicsBtn.setText("Graphics", 20, Color.white);
		graphicsBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		graphicsBtn.setPosition(new Vector2f(this.getPosition().getX() + 152, this.getPosition().getY() - 32));
		graphicsBtn.setSize(new Size(150, 32));
		graphicsBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 1;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) audioBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) controlsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 1) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 1) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
		audioBtn = new UIButton();
		audioBtn.setText("Audio", 20, Color.white);
		audioBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		audioBtn.setPosition(new Vector2f(this.getPosition().getX() + 304, this.getPosition().getY() - 32));
		audioBtn.setSize(new Size(150, 32));
		audioBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 2;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) controlsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) graphicsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 2) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 2) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
	}

	@Override
	public void update() {
		if (this.isVisible) {
			controlsBtn.update();
			graphicsBtn.update();
			audioBtn.update();
			if (graphicsChanged) {
				applyBtn.update();
			}
			switch (this.panelActive) {
			case 0:
				/*
				 * path finding max distance to check different panel keyboard shortcuts Fx
				 * buttons, such as debug, logs, reload?
				 * 
				 * 
				 */
				break;
			case 1:
				/*
				 * fullscreen toggle resolution dropdown texture quality? vsync custom non vsync
				 * options: target fps menu fps inactive fps
				 * 
				 */
				fullscreenToggle.update();
				resolutionDropdown.update();
				vsyncToggle.update();
				break;
			case 2:
				/*
				 * volume slider for master, sound effects, music.
				 * 
				 */
				masterVolumeSlider.update();
				musicVolumeSlider.update();
				soundEffectsVolumeSlider.update();
				break;
			}
		}
	}

	@Override
	public void render() {
		if (this.isVisible) {
			GL11.glColor3f(1, 1, 1);
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY(), this.getSize().getWidth(),
					this.getSize().getHeight(), new Color(0.15f, 0.15f, 0.15f, 1f));

			Renderer.renderButton(controlsBtn);
			Renderer.renderButton(graphicsBtn);
			Renderer.renderButton(audioBtn);
			if (graphicsChanged) {
				Renderer.renderButton(applyBtn);
			}

			switch (this.panelActive) {
			case 0:
				/*
				 * path finding max distance to check different panel keyboard shortcuts Fx
				 * buttons, such as debug, logs, reload?
				 * 
				 * 
				 */
				int y = 0;
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Controls Panel", 12,
						Color.white);
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Path Finding",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 120, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "Level 0", 16,
						Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y,
						"Show Telemetry", 20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 16, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				break;
			case 1:
				/*
				 * fullscreen toggle resolution dropdown texture quality? vsync custom non vsync
				 * options: target fps menu fps inactive fps
				 * 
				 */

				Renderer.renderToggle(fullscreenToggle);
				Renderer.renderToggle(vsyncToggle);

				Renderer.renderDropDown(resolutionDropdown);

				y = 0;
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Graphics Panel", 12,
						Color.white);
				/*
				 * Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY()
				 * + 10 + y, "Fullscreen", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 16, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				 * y += 200; Renderer.renderText(this.getPosition().getX() + 10,
				 * this.getPosition().getY() + 10 + y, "Resolution", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 120, 16, new Color(0.8f, 0.8f, 0.8f,
				 * 1f)); Renderer.renderText(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 14 + y, "Auto", 16, Color.black); y += 30;
				 * Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY()
				 * + 10 + y, "Quality", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 120, 16, new Color(0.8f, 0.8f, 0.8f,
				 * 1f)); Renderer.renderText(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 14 + y, "Auto", 16, Color.black); y += 30;
				 * Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY()
				 * + 10 + y, "Vsync", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 16, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				 * y += 30; Renderer.renderText(this.getPosition().getX() + 10,
				 * this.getPosition().getY() + 10 + y, "- Target FPS", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 50, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				 * Renderer.renderText(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 14 + y, "120", 16, Color.black); y += 30;
				 * Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY()
				 * + 10 + y, "- Menu FPS", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 50, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				 * Renderer.renderText(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 14 + y, "60", 16, Color.black); y += 30;
				 * Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY()
				 * + 10 + y, "- Inactive FPS", 20, Color.white);
				 * Renderer.renderQuad(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 18 + y, 50, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				 * Renderer.renderText(this.getPosition().getX() + 200,
				 * this.getPosition().getY() + 14 + y, "30", 16, Color.black);
				 */
				break;
			case 2:
				/*
				 * volume slider for master, sound effects, music.
				 * 
				 */
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Audio Panel", 12,
						Color.white);
				Renderer.renderSlider(masterVolumeSlider);
				Renderer.renderSlider(musicVolumeSlider);
				Renderer.renderSlider(soundEffectsVolumeSlider);
				break;
			}

		}
	}
}
